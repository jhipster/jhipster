/*
 * Copyright 2016-2019 the original author or authors.
 *
 * This file is part of the JHipster project, see https://www.jhipster.tech/
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.jhipster.service;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.RangeFilter;
import io.github.jhipster.service.filter.StringFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

/**
 * Base service for constructing and executing complex queries.
 *
 * @param <ENTITY> the type of the entity which is queried.
 */
@Transactional(readOnly = true)
public abstract class QueryService<ENTITY> {

    /**
     * Helper function to return a specification for filtering on a single field, where equality, and null/non-null
     * conditions are supported.
     *
     * @param filter the individual attribute filter coming from the frontend.
     * @param field  the JPA static metamodel representing the field.
     * @param <X>    The type of the attribute which is filtered.
     * @return a Specification
     */
    protected <X> Specification<ENTITY> buildSpecification(Filter<X> filter, SingularAttribute<? super ENTITY, X>
        field) {
        return buildSpecification(filter, root -> root.get(field));
    }

    /**
     * Helper function to return a specification for filtering on a single field, where equality, and null/non-null
     * conditions are supported.
     *
     * @param filter            the individual attribute filter coming from the frontend.
     * @param metaclassFunction the function, which navigates from the current entity to a column, for which the filter applies.
     * @param <X>               The type of the attribute which is filtered.
     * @return a Specification
     */
    protected <X> Specification<ENTITY> buildSpecification(Filter<X> filter, Function<Root<ENTITY>, Expression<X>> metaclassFunction) {
        if (filter.getEquals() != null) {
            return equalsSpecification(metaclassFunction, filter.getEquals());
        } else if (filter.getIn() != null) {
            return valueIn(metaclassFunction, filter.getIn());
        } else if (filter.getSpecified() != null) {
            return byFieldSpecified(metaclassFunction, filter.getSpecified());
        }
        return null;
    }

    /**
     * Helper function to return a specification for filtering on a {@link String} field, where equality, containment,
     * and null/non-null conditions are supported.
     *
     * @param filter the individual attribute filter coming from the frontend.
     * @param field  the JPA static metamodel representing the field.
     * @return a Specification
     */
    protected Specification<ENTITY> buildStringSpecification(StringFilter filter, SingularAttribute<? super ENTITY,
        String> field) {
        return buildSpecification(filter, root -> root.get(field));
    }

    /**
     * Helper function to return a specification for filtering on a {@link String} field, where equality, containment,
     * and null/non-null conditions are supported.
     *
     * @param filter            the individual attribute filter coming from the frontend.
     * @param metaclassFunction lambda, which based on a Root&lt;ENTITY&gt; returns Expression - basicaly picks a column
     * @return a Specification
     */
    protected Specification<ENTITY> buildSpecification(StringFilter filter, Function<Root<ENTITY>, Expression<String>> metaclassFunction) {
        if (filter.getEquals() != null) {
            return equalsSpecification(metaclassFunction, filter.getEquals());
        } else if (filter.getIn() != null) {
            return valueIn(metaclassFunction, filter.getIn());
        } else if (filter.getContains() != null) {
            return likeUpperSpecification(metaclassFunction, filter.getContains());
        } else if (filter.getSpecified() != null) {
            return byFieldSpecified(metaclassFunction, filter.getSpecified());
        }
        return null;
    }

    /**
     * Helper function to return a specification for filtering on a single {@link Comparable}, where equality, less
     * than, greater than and less-than-or-equal-to and greater-than-or-equal-to and null/non-null conditions are
     * supported.
     *
     * @param filter the individual attribute filter coming from the frontend.
     * @param field  the JPA static metamodel representing the field.
     * @param <X>    The type of the attribute which is filtered.
     * @return a Specification
     */
    protected <X extends Comparable<? super X>> Specification<ENTITY> buildRangeSpecification(RangeFilter<X> filter,
                                                                                              SingularAttribute<? super ENTITY, X> field) {
        return buildSpecification(filter, root -> root.get(field));
    }

    /**
     * Helper function to return a specification for filtering on a single {@link Comparable}, where equality, less
     * than, greater than and less-than-or-equal-to and greater-than-or-equal-to and null/non-null conditions are
     * supported.
     *
     * @param filter            the individual attribute filter coming from the frontend.
     * @param metaclassFunction lambda, which based on a Root&lt;ENTITY&gt; returns Expression - basicaly picks a column
     * @param <X>               The type of the attribute which is filtered.
     * @return a Specification
     */
    protected <X extends Comparable<? super X>> Specification<ENTITY> buildSpecification(RangeFilter<X> filter,
                                                                                         Function<Root<ENTITY>, Expression<X>> metaclassFunction) {
        if (filter.getEquals() != null) {
            return equalsSpecification(metaclassFunction, filter.getEquals());
        } else if (filter.getIn() != null) {
            return valueIn(metaclassFunction, filter.getIn());
        }

        Specification<ENTITY> result = Specification.where(null);
        if (filter.getSpecified() != null) {
            result = result.and(byFieldSpecified(metaclassFunction, filter.getSpecified()));
        }
        if (filter.getGreaterThan() != null) {
            result = result.and(greaterThan(metaclassFunction, filter.getGreaterThan()));
        }
        if (filter.getGreaterOrEqualThan() != null) {
            result = result.and(greaterThanOrEqualTo(metaclassFunction, filter.getGreaterOrEqualThan()));
        }
        if (filter.getLessThan() != null) {
            result = result.and(lessThan(metaclassFunction, filter.getLessThan()));
        }
        if (filter.getLessOrEqualThan() != null) {
            result = result.and(lessThanOrEqualTo(metaclassFunction, filter.getLessOrEqualThan()));
        }
        return result;
    }

    /**
     * Helper function to return a specification for filtering on one-to-one or many-to-one reference. Usage:
     * <pre>
     *   Specification&lt;Employee&gt; specByProjectId = buildReferringEntitySpecification(criteria.getProjectId(),
     * Employee_.project, Project_.id);
     *   Specification&lt;Employee&gt; specByProjectName = buildReferringEntitySpecification(criteria.getProjectName(),
     * Employee_.project, Project_.name);
     * </pre>
     *
     * @param filter     the filter object which contains a value, which needs to match or a flag if nullness is
     *                   checked.
     * @param reference  the attribute of the static metamodel for the referring entity.
     * @param valueField the attribute of the static metamodel of the referred entity, where the equality should be
     *                   checked.
     * @param <OTHER>    The type of the referenced entity.
     * @param <X>        The type of the attribute which is filtered.
     * @return a Specification
     */
    protected <OTHER, X> Specification<ENTITY> buildReferringEntitySpecification(Filter<X> filter,
                                                                                 SingularAttribute<? super ENTITY, OTHER> reference,
                                                                                 SingularAttribute<? super OTHER, X> valueField) {
        return buildSpecification(filter, root -> root.get(reference).get(valueField));
    }

    /**
     * Helper function to return a specification for filtering on one-to-one or many-to-one reference. Where equality, less
     * than, greater than and less-than-or-equal-to and greater-than-or-equal-to and null/non-null conditions are
     * supported. Usage:
     * <pre>
     *   Specification&lt;Employee&gt; specByProjectId = buildReferringEntitySpecification(criteria.getProjectId(),
     * Employee_.project, Project_.id);
     *   Specification&lt;Employee&gt; specByProjectName = buildReferringEntitySpecification(criteria.getProjectName(),
     * Employee_.project, Project_.name);
     * </pre>
     *
     * @param filter     the filter object which contains a value, which needs to match or a flag if nullness is
     *                   checked.
     * @param reference  the attribute of the static metamodel for the referring entity.
     * @param valueField the attribute of the static metamodel of the referred entity, where the equality should be
     *                   checked.
     * @param <OTHER>    The type of the referenced entity.
     * @param <X>        The type of the attribute which is filtered.
     * @return a Specification
     * @deprecated just call buildSpecification(filter, root -&gt; root.get(reference).get(valueField))
     */
    @Deprecated
    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> buildReferringEntitySpecification(final RangeFilter<X> filter,
                                                                                                               final SingularAttribute<? super ENTITY, OTHER> reference,
                                                                                                               final SingularAttribute<OTHER, X> valueField) {
        return buildSpecification(filter, root -> root.get(reference).get(valueField));
    }

    /**
     * Helper function to return a specification for filtering on one-to-many or many-to-many reference. Usage:
     * <pre>
     *   Specification&lt;Employee&gt; specByEmployeeId = buildReferringEntitySpecification(criteria.getEmployeId(),
     * Project_.employees, Employee_.id);
     *   Specification&lt;Employee&gt; specByEmployeeName = buildReferringEntitySpecification(criteria.getEmployeName(),
     * Project_.project, Project_.name);
     * </pre>
     *
     * @param filter     the filter object which contains a value, which needs to match or a flag if emptiness is
     *                   checked.
     * @param reference  the attribute of the static metamodel for the referring entity.
     * @param valueField the attribute of the static metamodel of the referred entity, where the equality should be
     *                   checked.
     * @param <OTHER>    The type of the referenced entity.
     * @param <X>        The type of the attribute which is filtered.
     * @return a Specification
     */
    protected <OTHER, X> Specification<ENTITY> buildReferringEntitySpecification(Filter<X> filter,
                                                                                 SetAttribute<ENTITY, OTHER> reference,
                                                                                 SingularAttribute<OTHER, X> valueField) {
        return buildReferringEntitySpecification(filter, root -> root.join(reference), entity -> entity.get(valueField));
    }

    /**
     * Helper function to return a specification for filtering on one-to-many or many-to-many reference. Usage:
     * <pre>
     *   Specification&lt;Employee&gt; specByEmployeeId = buildReferringEntitySpecification(
     *          criteria.getEmployeId(),
     *          root -&gt; root.get(Project_.company).join(Company_.employees),
     *          entity -&gt; entity.get(Employee_.id));
     *   Specification&lt;Employee&gt; specByProjectName = buildReferringEntitySpecification(
     *          criteria.getProjectName(),
     *          root -&gt; root.get(Project_.project)
     *          entity -&gt; entity.get(Project_.name));
     * </pre>
     *
     * @param filter           the filter object which contains a value, which needs to match or a flag if emptiness is
     *                         checked.
     * @param functionToEntity the function, which joins he current entity to the entity set, on which the filtering is applied.
     * @param entityToColumn   the function, which of the static metamodel of the referred entity, where the equality should be
     *                         checked.
     * @param <OTHER>          The type of the referenced entity.
     * @param <X>              The type of the attribute which is filtered.
     * @return a Specification
     */
    protected <OTHER, MISC, X> Specification<ENTITY> buildReferringEntitySpecification(Filter<X> filter,
                                                                                       Function<Root<ENTITY>, SetJoin<MISC, OTHER>> functionToEntity,
                                                                                       Function<SetJoin<MISC, OTHER>, Expression<X>> entityToColumn) {
        if (filter.getEquals() != null) {
            return equalsSpecification(functionToEntity.andThen(entityToColumn), filter.getEquals());
        } else if (filter.getSpecified() != null) {
            // Interestingly, 'functionToEntity' doesn't work, we need the longer lambda formula 
            return byFieldSpecified(root -> functionToEntity.apply(root), filter.getSpecified());
        }
        return null;
    }

    /**
     * Helper function to return a specification for filtering on one-to-many or many-to-many reference. Where equality, less
     * than, greater than and less-than-or-equal-to and greater-than-or-equal-to and null/non-null conditions are
     * supported. Usage:
     * <pre>
     *   Specification&lt;Employee&gt; specByEmployeeId = buildReferringEntitySpecification(criteria.getEmployeId(),
     * Project_.employees, Employee_.id);
     *   Specification&lt;Employee&gt; specByEmployeeName = buildReferringEntitySpecification(criteria.getEmployeName(),
     * Project_.project, Project_.name);
     * </pre>
     *
     * @param filter     the filter object which contains a value, which needs to match or a flag if emptiness is
     *                   checked.
     * @param reference  the attribute of the static metamodel for the referring entity.
     * @param valueField the attribute of the static metamodel of the referred entity, where the equality should be
     *                   checked.
     * @param <OTHER>    The type of the referenced entity.
     * @param <X>        The type of the attribute which is filtered.
     * @return a Specification
     */
    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> buildReferringEntitySpecification(final RangeFilter<X> filter,
                                                                                                               final SetAttribute<ENTITY, OTHER> reference,
                                                                                                               final SingularAttribute<OTHER, X> valueField) {
        return buildReferringEntitySpecification(filter, root -> root.join(reference), entity -> entity.get(valueField));
    }

    /**
     * Helper function to return a specification for filtering on one-to-many or many-to-many reference. Where equality, less
     * than, greater than and less-than-or-equal-to and greater-than-or-equal-to and null/non-null conditions are
     * supported. Usage:
     * <pre><code>
     *   Specification&lt;Employee&gt; specByEmployeeId = buildReferringEntitySpecification(
     *          criteria.getEmployeId(),
     *          root -&gt; root.get(Project_.company).join(Company_.employees),
     *          entity -&gt; entity.get(Employee_.id));
     *   Specification&lt;Employee&gt; specByProjectName = buildReferringEntitySpecification(
     *          criteria.getProjectName(),
     *          root -&gt; root.get(Project_.project)
     *          entity -&gt; entity.get(Project_.name));
     * </code>
     * </pre>
     *
     * @param filter           the filter object which contains a value, which needs to match or a flag if emptiness is
     *                         checked.
     * @param functionToEntity the function, which joins he current entity to the entity set, on which the filtering is applied.
     * @param entityToColumn   the function, which of the static metamodel of the referred entity, where the equality should be
     *                         checked.
     * @param <OTHER>          The type of the referenced entity.
     * @param <MISC>           The type of the entity which is the last before the OTHER in the chain.
     * @param <X>              The type of the attribute which is filtered.
     * @return a Specification
     */
    protected <OTHER, MISC, X extends Comparable<? super X>> Specification<ENTITY> buildReferringEntitySpecification(final RangeFilter<X> filter,
                                                                                                                     Function<Root<ENTITY>, SetJoin<MISC, OTHER>> functionToEntity,
                                                                                                                     Function<SetJoin<MISC, OTHER>, Expression<X>> entityToColumn) {

        Function<Root<ENTITY>, Expression<X>> fused = functionToEntity.andThen(entityToColumn);
        if (filter.getEquals() != null) {
            return equalsSpecification(fused, filter.getEquals());
        } else if (filter.getIn() != null) {
            return valueIn(fused, filter.getIn());
        }
        Specification<ENTITY> result = Specification.where(null);
        if (filter.getSpecified() != null) {
            // Interestingly, 'functionToEntity' doesn't work, we need the longer lambda formula 
            result = result.and(byFieldSpecified(root -> functionToEntity.apply(root), filter.getSpecified()));
        }
        if (filter.getGreaterThan() != null) {
            result = result.and(greaterThan(fused, filter.getGreaterThan()));
        }
        if (filter.getGreaterOrEqualThan() != null) {
            result = result.and(greaterThanOrEqualTo(fused, filter.getGreaterOrEqualThan()));
        }
        if (filter.getLessThan() != null) {
            result = result.and(lessThan(fused, filter.getLessThan()));
        }
        if (filter.getLessOrEqualThan() != null) {
            result = result.and(lessThanOrEqualTo(fused, filter.getLessOrEqualThan()));
        }
        return result;
    }

    /**
     * Generic method, which based on a Root&lt;ENTITY&gt; returns an Expression which type is the same as the given 'value' type.
     *
     * @param metaclassFunction function which returns the column which is used for filtering.
     * @param value             the actual value to filter for.
     * @return a Specification.
     */
    protected <X> Specification<ENTITY> equalsSpecification(Function<Root<ENTITY>, Expression<X>> metaclassFunction, final X value) {
        return (root, query, builder) -> builder.equal(metaclassFunction.apply(root), value);
    }

    /**
     * @deprecated Just call the equalsSpecification(root -&gt; root.get(field), value) directly.
     */
    protected <X> Specification<ENTITY> equalsSpecification(SingularAttribute<? super ENTITY, X> field, final X value) {
        return equalsSpecification(root -> root.get(field), value);
    }

    /**
     * @deprecated Just call the equalsSpecification(root -&gt; root.get(reference).get(valueField), value) directly.
     */
    @Deprecated
    protected <OTHER, X> Specification<ENTITY> equalsSpecification(
        SingularAttribute<? super ENTITY, OTHER> reference,
        SingularAttribute<? super OTHER, X> valueField,
        X value) {
        return equalsSpecification(root -> root.get(reference).get(valueField), value);
    }

    /**
     * @deprecated Just call the equalsSpecification(root -&gt; root.join(reference).get(valueField), value) directly.
     */
    @Deprecated
    protected <OTHER, X> Specification<ENTITY> equalsSetSpecification(SetAttribute<? super ENTITY, OTHER> reference,
                                                                      SingularAttribute<OTHER, X> idField, X value) {
        return equalsSpecification(root -> root.join(reference).get(idField), value);
    }

    protected Specification<ENTITY> likeUpperSpecification(Function<Root<ENTITY>, Expression<String>> metaclassFunction,
                                                           final String value) {
        return (root, query, builder) -> builder.like(builder.upper(metaclassFunction.apply(root)), wrapLikeQuery(value));
    }

    /**
     * @deprecated Just call the likeUpperSpecification(root -&gt; root.get(field), value) directly.
     */
    protected Specification<ENTITY> likeUpperSpecification(SingularAttribute<? super ENTITY, String> field,
                                                           final String value) {
        return likeUpperSpecification(root -> root.get(field), value);
    }

    protected <X> Specification<ENTITY> byFieldSpecified(Function<Root<ENTITY>, Expression<X>> metaclassFunction,
                                                         final boolean specified) {
        return specified ?
            (root, query, builder) -> builder.isNotNull(metaclassFunction.apply(root)) :
            (root, query, builder) -> builder.isNull(metaclassFunction.apply(root));
    }

    /**
     * @deprecated Just call the byFieldSpecified(root -&gt; root.get(field), value) directly.
     */
    @Deprecated
    protected <X> Specification<ENTITY> byFieldSpecified(SingularAttribute<? super ENTITY, X> field,
                                                         final boolean specified) {
        return byFieldSpecified(root -> root.get(field), specified);
    }

    protected <X> Specification<ENTITY> byFieldEmptiness(Function<Root<ENTITY>, Expression<Set<X>>> metaclassFunction,
                                                         final boolean specified) {
        return specified ?
            (root, query, builder) -> builder.isNotEmpty(metaclassFunction.apply(root)) :
            (root, query, builder) -> builder.isEmpty(metaclassFunction.apply(root));
    }

    /**
     * @deprecated Just call the byFieldEmptiness(root -&gt; root.get(field), value) directly.
     */
    @Deprecated
    protected <X> Specification<ENTITY> byFieldSpecified(SetAttribute<ENTITY, X> field, final boolean specified) {
        return byFieldEmptiness(root -> root.get(field), specified);
    }

    protected <X> Specification<ENTITY> valueIn(Function<Root<ENTITY>, Expression<X>> metaclassFunction,
                                                final Collection<X> values) {
        return (root, query, builder) -> {
            In<X> in = builder.in(metaclassFunction.apply(root));
            for (X value : values) {
                in = in.value(value);
            }
            return in;
        };
    }

    /**
     * @deprecated Just call the valueIn(root -&gt; root.get(field), value) directly.
     */
    @Deprecated
    protected <X> Specification<ENTITY> valueIn(SingularAttribute<? super ENTITY, X> field,
                                                final Collection<X> values) {
        return valueIn(root -> root.get(field), values);
    }

    /**
     * @deprecated Just call the valueIn(root -&gt; root.get(reference).get(valueField), value) directly.
     */
    @Deprecated
    protected <OTHER, X> Specification<ENTITY> valueIn(SingularAttribute<? super ENTITY, OTHER> reference,
                                                       SingularAttribute<? super OTHER, X> valueField, final Collection<X> values) {
        return valueIn(root -> root.get(reference).get(valueField), values);
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> greaterThanOrEqualTo(Function<Root<ENTITY>, Expression<X>> metaclassFunction,
                                                                                           final X value) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(metaclassFunction.apply(root), value);
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> greaterThan(Function<Root<ENTITY>, Expression<X>> metaclassFunction,
                                                                                  final X value) {
        return (root, query, builder) -> builder.greaterThan(metaclassFunction.apply(root), value);
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> lessThanOrEqualTo(Function<Root<ENTITY>, Expression<X>> metaclassFunction,
                                                                                        final X value) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(metaclassFunction.apply(root), value);
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> lessThan(Function<Root<ENTITY>, Expression<X>> metaclassFunction,
                                                                               final X value) {
        return (root, query, builder) -> builder.lessThan(metaclassFunction.apply(root), value);
    }

    /**
     * @deprecated Just call the greaterThanOrEqualTo(root -&gt; root.get(field), value) directly.
     */
    @Deprecated
    protected <X extends Comparable<? super X>> Specification<ENTITY> greaterThanOrEqualTo(SingularAttribute<? super
        ENTITY, X> field, final X value) {
        return greaterThanOrEqualTo(root -> root.get(field), value);
    }

    /**
     * @deprecated Just call the greaterThan(root -&gt; root.get(field), value) directly.
     */
    @Deprecated
    protected <X extends Comparable<? super X>> Specification<ENTITY> greaterThan(SingularAttribute<? super ENTITY,
        X> field, final X value) {
        return greaterThan(root -> root.get(field), value);
    }

    /**
     * @deprecated Just call the lessThanOrEqualTo(root -&gt; root.get(field), value) directly.
     */
    @Deprecated
    protected <X extends Comparable<? super X>> Specification<ENTITY> lessThanOrEqualTo(SingularAttribute<? super
        ENTITY, X> field, final X value) {
        return lessThanOrEqualTo(root -> root.get(field), value);
    }

    /**
     * @deprecated Just call the lessThan(root -&gt; root.get(field), value) directly.
     */
    @Deprecated
    protected <X extends Comparable<? super X>> Specification<ENTITY> lessThan(SingularAttribute<? super ENTITY, X>
                                                                                   field, final X value) {
        return lessThan(root -> root.get(field), value);
    }

    protected String wrapLikeQuery(String txt) {
        return "%" + txt.toUpperCase() + '%';
    }

    /**
     * @deprecated Just call the valueIn(root -&gt; root.get(reference).get(valueField), value) directly.
     */
    @Deprecated
    protected <OTHER, X> Specification<ENTITY> valueIn(final SetAttribute<? super ENTITY, OTHER> reference,
                                                       final SingularAttribute<OTHER, X> valueField, final Collection<X> values) {
        return valueIn(root -> root.join(reference).get(valueField), values);
    }

    /**
     * @deprecated Just call the greaterThan(root -&gt; root.get(reference).get(valueField), value) directly.
     */
    @Deprecated
    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> greaterThan(final SingularAttribute<? super ENTITY, OTHER> reference, final SingularAttribute<OTHER, X> valueField, final X value) {
        return greaterThan(root -> root.get(reference).get(valueField), value);
    }

    /**
     * @deprecated Just call the greaterThan(root -&gt; root.join(reference).get(valueField), value) directly.
     */
    @Deprecated
    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> greaterThan(final SetAttribute<? super ENTITY, OTHER> reference, final SingularAttribute<OTHER, X> valueField, final X value) {
        return greaterThan(root -> root.join(reference).get(valueField), value);
    }

    /**
     * @deprecated Just call the greaterThanOrEqualTo(root -&gt; root.get(reference).get(valueField), value) directly.
     */
    @Deprecated
    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> greaterThanOrEqualTo(final SingularAttribute<? super ENTITY, OTHER> reference, final SingularAttribute<OTHER, X> valueField, final X value) {
        return greaterThanOrEqualTo(root -> root.get(reference).get(valueField), value);
    }

    /**
     * @deprecated Just call the greaterThanOrEqualTo(root -&gt; root.join(reference).get(valueField), value) directly.
     */
    @Deprecated
    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> greaterThanOrEqualTo(final SetAttribute<? super ENTITY, OTHER> reference, final SingularAttribute<OTHER, X> valueField, final X value) {
        return greaterThanOrEqualTo(root -> root.join(reference).get(valueField), value);
    }

    /**
     * @deprecated Just call the lessThan(root -&lt; root.get(reference).get(valueField), value) directly.
     */
    @Deprecated
    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> lessThan(final SingularAttribute<? super ENTITY, OTHER> reference, final SingularAttribute<OTHER, X> valueField, final X value) {
        return lessThan(root -> root.get(reference).get(valueField), value);
    }

    /**
     * @deprecated Just call the lessThan(root -&gt; root.join(reference).get(valueField), value) directly.
     */
    @Deprecated
    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> lessThan(final SetAttribute<? super ENTITY, OTHER> reference, final SingularAttribute<OTHER, X> valueField, final X value) {
        return lessThan(root -> root.join(reference).get(valueField), value);
    }

    /**
     * @deprecated Just call the lessThanOrEqualTo(root -&gt; root.get(reference).get(valueField), value) directly.
     */
    @Deprecated
    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> lessThanOrEqualTo(final SingularAttribute<? super ENTITY, OTHER> reference, final SingularAttribute<OTHER, X> valueField, final X value) {
        return lessThanOrEqualTo(root -> root.get(reference).get(valueField), value);
    }

    /**
     * @deprecated Just call the lessThanOrEqualTo(root -&gt; root.join(reference).get(valueField), value) directly.
     */
    @Deprecated
    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> lessThanOrEqualTo(final SetAttribute<? super ENTITY, OTHER> reference, final SingularAttribute<OTHER, X> valueField, final X value) {
        return lessThanOrEqualTo(root -> root.join(reference).get(valueField), value);
    }
}
