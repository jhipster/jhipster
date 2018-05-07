package io.github.jhipster.service.util;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.RangeFilter;
import io.github.jhipster.service.filter.StringFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Collection;

/**
 * Util class to allow to extend {@linkplain io.github.jhipster.service.QueryService} with more filtering possibilities after application is generated.
 * <p>For a new filter, steps should be followed :</p>
 * <ul>
 * <li>add filter in the criteria generated</li>
 * <li>modify associated XXXXQueryService that use the criteria class to handle this new filter</li>
 * <li>call appropriate method of this util class depending on if the value to be filtered is a foreign key or not from the last relation of the filter chain</li>
 * </ul>
 * <p>Created on 2018/5/5.</p>
 *
 * @author Yoann CAPLAIN
 */
@SuppressWarnings({"unused"})
public class QueryUtil {

    private QueryUtil() {
    }

    /*
     * Commented code below is just for demonstration purpose
     */
    /*
    // For a relation: A -> B -> C where U is the fk of B (so value inside table B) we will get one inner join and a where b.c_id = ... (good, we skip a join)
    //For a relation: A -> B -> C where U is a column of C (so value not inside table B) we will get one inner join, a cross join and a where c.column_name = ... <- not very good but that's where method joinAndFinalGet can fix that
    public static <U, ENTITY1, ENTITY2, ENTITY3> Specification<ENTITY1> joinAndGet(final U value, final SingularAttribute<ENTITY1, ENTITY2> e1, final SingularAttribute<ENTITY2, ENTITY3> e2, final SingularAttribute<ENTITY3, U> e3) {
        return (root, query, builder) -> builder.equal(root.join(e1).get(e2).get(e3), value);
    }

    // For a relation: A -> B -> C where U is the fk of B (so value inside table B) we will get two inner join and a where c.column_name = ... (good as well, we just get 2 inner join)
    // For a relation: A -> B -> C where U is a column of C (so value not inside table B) we will get two inner join and a where c.column_name = ... (similar to fk)
    public static <U, ENTITY1, ENTITY2, ENTITY3> Specification<ENTITY1> joinAndFinalGet(final U value, final SingularAttribute<ENTITY1, ENTITY2> e1, final SingularAttribute<ENTITY2, ENTITY3> e2, final SingularAttribute<ENTITY3, U> e3) {
        return (root, query, builder) -> builder.equal(root.join(e1).join(e2).get(e3), value);
    }
    //*/

    public static <U, ENTITY1, ENTITY2, ENTITY3> Specification<ENTITY1> buildEqualsJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, U> e3) {
        return (root, query, builder) -> builder.equal(root.join(e1).join(e2).get(e3), value);
    }

    public static <U, ENTITY1, ENTITY2, ENTITY3, ENTITY4> Specification<ENTITY1> buildEqualsJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, U> e4) {
        return (root, query, builder) -> builder.equal(root.join(e1).join(e2).join(e3).get(e4), value);
    }

    public static <U, ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5> Specification<ENTITY1> buildEqualsJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, U> e5) {
        return (root, query, builder) -> builder.equal(root.join(e1).join(e2).join(e3).join(e4).get(e5), value);
    }

    public static <U, ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5, ENTITY6> Specification<ENTITY1> buildEqualsJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, ENTITY6> e5, final SingularAttribute<? super ENTITY6, U> e6) {
        return (root, query, builder) -> builder.equal(root.join(e1).join(e2).join(e3).join(e4).join(e5).get(e6), value);
    }

    public static <U, ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5, ENTITY6, ENTITY7> Specification<ENTITY1> buildEqualsJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, ENTITY6> e5, final SingularAttribute<? super ENTITY6, ENTITY7> e6, final SingularAttribute<? super ENTITY7, U> e7) {
        return (root, query, builder) -> builder.equal(root.join(e1).join(e2).join(e3).join(e4).join(e5).join(e6).get(e7), value);
    }


    public static <U, ENTITY1, ENTITY2, ENTITY3> Specification<ENTITY1> buildInJoin(final Collection<U> values, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, U> e3) {
        return (root, query, builder) -> {
            CriteriaBuilder.In<U> in = builder.in(root.join(e1).join(e2).get(e3));
            for (U value : values) {
                in = in.value(value);
            }
            return in;
        };
    }

    public static <U, ENTITY1, ENTITY2, ENTITY3, ENTITY4> Specification<ENTITY1> buildInJoin(final Collection<U> values, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, U> e4) {
        return (root, query, builder) -> {
            CriteriaBuilder.In<U> in = builder.in(root.join(e1).join(e2).join(e3).get(e4));
            for (U value : values) {
                in = in.value(value);
            }
            return in;
        };
    }

    public static <U, ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5> Specification<ENTITY1> buildInJoin(final Collection<U> values, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<ENTITY5, U> e5) {
        return (root, query, builder) -> {
            CriteriaBuilder.In<U> in = builder.in(root.join(e1).join(e2).join(e3).join(e4).get(e5));
            for (U value : values) {
                in = in.value(value);
            }
            return in;
        };
    }

    public static <U, ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5, ENTITY6> Specification<ENTITY1> buildInJoin(final Collection<U> values, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, ENTITY6> e5, final SingularAttribute<? super ENTITY6, U> e6) {
        return (root, query, builder) -> {
            CriteriaBuilder.In<U> in = builder.in(root.join(e1).join(e2).join(e3).join(e4).join(e5).get(e6));
            for (U value : values) {
                in = in.value(value);
            }
            return in;
        };
    }

    public static <U, ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5, ENTITY6, ENTITY7> Specification<ENTITY1> buildInJoin(final Collection<U> values, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, ENTITY6> e5, final SingularAttribute<? super ENTITY6, ENTITY7> e6, final SingularAttribute<? super ENTITY7, U> e7) {
        return (root, query, builder) -> {
            CriteriaBuilder.In<U> in = builder.in(root.join(e1).join(e2).join(e3).join(e4).join(e5).join(e6).get(e7));
            for (U value : values) {
                in = in.value(value);
            }
            return in;
        };
    }


    public static <U, ENTITY1, ENTITY2, ENTITY3> Specification<ENTITY1> buildByFieldSpecifiedJoin(final boolean specified, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, U> e3) {
        return specified ?
            (root, query, builder) -> builder.isNotNull(root.join(e1).join(e2).get(e3)) :
            (root, query, builder) -> builder.isNull(root.join(e1).join(e2).get(e3));
    }

    public static <U, ENTITY1, ENTITY2, ENTITY3, ENTITY4> Specification<ENTITY1> buildByFieldSpecifiedJoin(final boolean specified, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, U> e4) {
        return specified ?
            (root, query, builder) -> builder.isNotNull(root.join(e1).join(e2).join(e3).get(e4)) :
            (root, query, builder) -> builder.isNull(root.join(e1).join(e2).join(e3).get(e4));
    }

    public static <U, ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5> Specification<ENTITY1> buildByFieldSpecifiedJoin(final boolean specified, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, U> e5) {
        return specified ?
            (root, query, builder) -> builder.isNotNull(root.join(e1).join(e2).join(e3).join(e4).get(e5)) :
            (root, query, builder) -> builder.isNull(root.join(e1).join(e2).join(e3).join(e4).get(e5));
    }

    public static <U, ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5, ENTITY6> Specification<ENTITY1> buildByFieldSpecifiedJoin(final boolean specified, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, ENTITY6> e5, final SingularAttribute<? super ENTITY6, U> e6) {
        return specified ?
            (root, query, builder) -> builder.isNotNull(root.join(e1).join(e2).join(e3).join(e4).join(e5).get(e6)) :
            (root, query, builder) -> builder.isNull(root.join(e1).join(e2).join(e3).join(e4).join(e5).get(e6));
    }

    public static <U, ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5, ENTITY6, ENTITY7> Specification<ENTITY1> buildByFieldSpecifiedJoin(final boolean specified, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, ENTITY6> e5, final SingularAttribute<? super ENTITY6, ENTITY7> e6, final SingularAttribute<? super ENTITY7, U> e7) {
        return specified ?
            (root, query, builder) -> builder.isNotNull(root.join(e1).join(e2).join(e3).join(e4).join(e5).join(e6).get(e7)) :
            (root, query, builder) -> builder.isNull(root.join(e1).join(e2).join(e3).join(e4).join(e5).join(e6).get(e7));
    }


    public static <ENTITY1, ENTITY2, ENTITY3> Specification<ENTITY1> buildLikeUpperJoin(final String value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, String> e3) {
        return (root, query, builder) -> builder.like(builder.upper(root.join(e1).join(e2).get(e3)), wrapLikeQuery(value));
    }

    public static <ENTITY1, ENTITY2, ENTITY3, ENTITY4> Specification<ENTITY1> buildLikeUpperJoin(final String value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, String> e4) {
        return (root, query, builder) -> builder.like(builder.upper(root.join(e1).join(e2).join(e3).get(e4)), wrapLikeQuery(value));
    }

    public static <ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5> Specification<ENTITY1> buildLikeUpperJoin(final String value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, String> e5) {
        return (root, query, builder) -> builder.like(builder.upper(root.join(e1).join(e2).join(e3).join(e4).get(e5)), wrapLikeQuery(value));
    }

    public static <ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5, ENTITY6> Specification<ENTITY1> buildLikeUpperJoin(final String value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, ENTITY6> e5, final SingularAttribute<? super ENTITY6, String> e6) {
        return (root, query, builder) -> builder.like(builder.upper(root.join(e1).join(e2).join(e3).join(e4).join(e5).get(e6)), wrapLikeQuery(value));
    }

    public static <ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5, ENTITY6, ENTITY7> Specification<ENTITY1> buildLikeUpperJoin(final String value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, ENTITY6> e5, final SingularAttribute<? super ENTITY6, ENTITY7> e6, final SingularAttribute<? super ENTITY7, String> e7) {
        return (root, query, builder) -> builder.like(builder.upper(root.join(e1).join(e2).join(e3).join(e4).join(e5).join(e6).get(e7)), wrapLikeQuery(value));
    }


    public static <U extends Comparable<? super U>, ENTITY1, ENTITY2, ENTITY3> Specification<ENTITY1> buildGreaterThanJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, U> e3) {
        return (root, query, builder) -> builder.greaterThan(root.join(e1).join(e2).get(e3), value);
    }

    public static <U extends Comparable<? super U>, ENTITY1, ENTITY2, ENTITY3, ENTITY4> Specification<ENTITY1> buildGreaterThanJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, U> e4) {
        return (root, query, builder) -> builder.greaterThan(root.join(e1).join(e2).join(e3).get(e4), value);
    }

    public static <U extends Comparable<? super U>, ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5> Specification<ENTITY1> buildGreaterThanJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, U> e5) {
        return (root, query, builder) -> builder.greaterThan(root.join(e1).join(e2).join(e3).join(e4).get(e5), value);
    }

    public static <U extends Comparable<? super U>, ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5, ENTITY6> Specification<ENTITY1> buildGreaterThanJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, ENTITY6> e5, final SingularAttribute<? super ENTITY6, U> e6) {
        return (root, query, builder) -> builder.greaterThan(root.join(e1).join(e2).join(e3).join(e4).join(e5).get(e6), value);
    }

    public static <U extends Comparable<? super U>, ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5, ENTITY6, ENTITY7> Specification<ENTITY1> buildGreaterThanJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, ENTITY6> e5, final SingularAttribute<? super ENTITY6, ENTITY7> e6, final SingularAttribute<? super ENTITY7, U> e7) {
        return (root, query, builder) -> builder.greaterThan(root.join(e1).join(e2).join(e3).join(e4).join(e5).join(e6).get(e7), value);
    }


    public static <U extends Comparable<? super U>, ENTITY1, ENTITY2, ENTITY3> Specification<ENTITY1> buildGreaterThanOrEqualToJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, U> e3) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.join(e1).join(e2).get(e3), value);
    }

    public static <U extends Comparable<? super U>, ENTITY1, ENTITY2, ENTITY3, ENTITY4> Specification<ENTITY1> buildGreaterThanOrEqualToJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, U> e4) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.join(e1).join(e2).join(e3).get(e4), value);
    }

    public static <U extends Comparable<? super U>, ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5> Specification<ENTITY1> buildGreaterThanOrEqualToJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, U> e5) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.join(e1).join(e2).join(e3).join(e4).get(e5), value);
    }

    public static <U extends Comparable<? super U>, ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5, ENTITY6> Specification<ENTITY1> buildGreaterThanOrEqualToJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, ENTITY6> e5, final SingularAttribute<? super ENTITY6, U> e6) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.join(e1).join(e2).join(e3).join(e4).join(e5).get(e6), value);
    }

    public static <U extends Comparable<? super U>, ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5, ENTITY6, ENTITY7> Specification<ENTITY1> buildGreaterThanOrEqualToJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, ENTITY6> e5, final SingularAttribute<? super ENTITY6, ENTITY7> e6, final SingularAttribute<? super ENTITY7, U> e7) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.join(e1).join(e2).join(e3).join(e4).join(e5).join(e6).get(e7), value);
    }


    public static <U extends Comparable<? super U>, ENTITY1, ENTITY2, ENTITY3> Specification<ENTITY1> buildLessThanJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, U> e3) {
        return (root, query, builder) -> builder.greaterThan(root.join(e1).join(e2).get(e3), value);
    }

    public static <U extends Comparable<? super U>, ENTITY1, ENTITY2, ENTITY3, ENTITY4> Specification<ENTITY1> buildLessThanJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, U> e4) {
        return (root, query, builder) -> builder.greaterThan(root.join(e1).join(e2).join(e3).get(e4), value);
    }

    public static <U extends Comparable<? super U>, ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5> Specification<ENTITY1> buildLessThanJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, U> e5) {
        return (root, query, builder) -> builder.greaterThan(root.join(e1).join(e2).join(e3).join(e4).get(e5), value);
    }

    public static <U extends Comparable<? super U>, ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5, ENTITY6> Specification<ENTITY1> buildLessThanJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, ENTITY6> e5, final SingularAttribute<? super ENTITY6, U> e6) {
        return (root, query, builder) -> builder.greaterThan(root.join(e1).join(e2).join(e3).join(e4).join(e5).get(e6), value);
    }

    public static <U extends Comparable<? super U>, ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5, ENTITY6, ENTITY7> Specification<ENTITY1> buildLessThanJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, ENTITY6> e5, final SingularAttribute<? super ENTITY6, ENTITY7> e6, final SingularAttribute<? super ENTITY7, U> e7) {
        return (root, query, builder) -> builder.greaterThan(root.join(e1).join(e2).join(e3).join(e4).join(e5).join(e6).get(e7), value);
    }


    public static <U extends Comparable<? super U>, ENTITY1, ENTITY2, ENTITY3> Specification<ENTITY1> buildLessThanOrEqualToJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, U> e3) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.join(e1).join(e2).get(e3), value);
    }

    public static <U extends Comparable<? super U>, ENTITY1, ENTITY2, ENTITY3, ENTITY4> Specification<ENTITY1> buildLessThanOrEqualToJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, U> e4) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.join(e1).join(e2).join(e3).get(e4), value);
    }

    public static <U extends Comparable<? super U>, ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5> Specification<ENTITY1> buildLessThanOrEqualToJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, U> e5) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.join(e1).join(e2).join(e3).join(e4).get(e5), value);
    }

    public static <U extends Comparable<? super U>, ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5, ENTITY6> Specification<ENTITY1> buildLessThanOrEqualToJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, ENTITY6> e5, final SingularAttribute<? super ENTITY6, U> e6) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.join(e1).join(e2).join(e3).join(e4).join(e5).get(e6), value);
    }

    public static <U extends Comparable<? super U>, ENTITY1, ENTITY2, ENTITY3, ENTITY4, ENTITY5, ENTITY6, ENTITY7> Specification<ENTITY1> buildLessThanOrEqualToJoin(final U value, final SingularAttribute<? super ENTITY1, ENTITY2> e1, final SingularAttribute<? super ENTITY2, ENTITY3> e2, final SingularAttribute<? super ENTITY3, ENTITY4> e3, final SingularAttribute<? super ENTITY4, ENTITY5> e4, final SingularAttribute<? super ENTITY5, ENTITY6> e5, final SingularAttribute<? super ENTITY6, ENTITY7> e6, final SingularAttribute<? super ENTITY7, U> e7) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.join(e1).join(e2).join(e3).join(e4).join(e5).join(e6).get(e7), value);
    }


    public static <ENTITY, ENTITY2, ENTITY3, X> Specification<ENTITY> buildReferringEntitySpecification(
        Filter<X> filter,
        SingularAttribute<? super ENTITY, ENTITY2> reference,
        SingularAttribute<? super ENTITY2, ENTITY3> reference2,
        SingularAttribute<ENTITY3, X> valueField) {
        if (filter.getEquals() != null) {
            return buildEqualsJoin(filter.getEquals(), reference, reference2, valueField);
        } else if (filter.getIn() != null) {
            return buildInJoin(filter.getIn(), reference, reference2, valueField);
        } else if (filter.getSpecified() != null) {
            return buildByFieldSpecifiedJoin(filter.getSpecified(), reference, reference2, valueField);
        }
        return null;
    }

    public static <ENTITY, ENTITY2, ENTITY3, ENTITY4, X> Specification<ENTITY> buildReferringEntitySpecification(
        Filter<X> filter,
        SingularAttribute<? super ENTITY, ENTITY2> reference,
        SingularAttribute<? super ENTITY2, ENTITY3> reference2,
        SingularAttribute<? super ENTITY3, ENTITY4> reference3,
        SingularAttribute<ENTITY4, X> valueField) {
        if (filter.getEquals() != null) {
            return buildEqualsJoin(filter.getEquals(), reference, reference2, reference3, valueField);
        } else if (filter.getIn() != null) {
            return buildInJoin(filter.getIn(), reference, reference2, reference3, valueField);
        } else if (filter.getSpecified() != null) {
            return buildByFieldSpecifiedJoin(filter.getSpecified(), reference, reference2, reference3, valueField);
        }
        return null;
    }

    public static <ENTITY, ENTITY2, ENTITY3, ENTITY4, ENTITY5, X> Specification<ENTITY> buildReferringEntitySpecification(
        Filter<X> filter,
        SingularAttribute<? super ENTITY, ENTITY2> reference,
        SingularAttribute<? super ENTITY2, ENTITY3> reference2,
        SingularAttribute<? super ENTITY3, ENTITY4> reference3,
        SingularAttribute<? super ENTITY4, ENTITY5> reference4,
        SingularAttribute<ENTITY5, X> valueField) {
        if (filter.getEquals() != null) {
            return buildEqualsJoin(filter.getEquals(), reference, reference2, reference3, reference4, valueField);
        } else if (filter.getIn() != null) {
            return buildInJoin(filter.getIn(), reference, reference2, reference3, reference4, valueField);
        } else if (filter.getSpecified() != null) {
            return buildByFieldSpecifiedJoin(filter.getSpecified(), reference, reference2, reference3, reference4, valueField);
        }
        return null;
    }

    public static <ENTITY, ENTITY2, ENTITY3, ENTITY4, ENTITY5, ENTITY6, X> Specification<ENTITY> buildReferringEntitySpecification(
        Filter<X> filter,
        SingularAttribute<? super ENTITY, ENTITY2> reference,
        SingularAttribute<? super ENTITY2, ENTITY3> reference2,
        SingularAttribute<? super ENTITY3, ENTITY4> reference3,
        SingularAttribute<? super ENTITY4, ENTITY5> reference4,
        SingularAttribute<? super ENTITY5, ENTITY6> reference5,
        SingularAttribute<ENTITY6, X> valueField) {
        if (filter.getEquals() != null) {
            return buildEqualsJoin(filter.getEquals(), reference, reference2, reference3, reference4, reference5, valueField);
        } else if (filter.getIn() != null) {
            return buildInJoin(filter.getIn(), reference, reference2, reference3, reference4, reference5, valueField);
        } else if (filter.getSpecified() != null) {
            return buildByFieldSpecifiedJoin(filter.getSpecified(), reference, reference2, reference3, reference4, reference5, valueField);
        }
        return null;
    }

    public static <ENTITY, ENTITY2, ENTITY3, ENTITY4, ENTITY5, ENTITY6, ENTITY7, X> Specification<ENTITY> buildReferringEntitySpecification(
        Filter<X> filter,
        SingularAttribute<? super ENTITY, ENTITY2> reference,
        SingularAttribute<? super ENTITY2, ENTITY3> reference2,
        SingularAttribute<? super ENTITY3, ENTITY4> reference3,
        SingularAttribute<? super ENTITY4, ENTITY5> reference4,
        SingularAttribute<? super ENTITY5, ENTITY6> reference5,
        SingularAttribute<? super ENTITY6, ENTITY7> reference6,
        SingularAttribute<ENTITY7, X> valueField) {
        if (filter.getEquals() != null) {
            return buildEqualsJoin(filter.getEquals(), reference, reference2, reference3, reference4, reference5, reference6, valueField);
        } else if (filter.getIn() != null) {
            return buildInJoin(filter.getIn(), reference, reference2, reference3, reference4, reference5, reference6, valueField);
        } else if (filter.getSpecified() != null) {
            return buildByFieldSpecifiedJoin(filter.getSpecified(), reference, reference2, reference3, reference4, reference5, reference6, valueField);
        }
        return null;
    }

    public static <ENTITY, ENTITY2, ENTITY3, X extends Comparable<? super X>> Specification<ENTITY> buildReferringEntitySpecification(
        final RangeFilter<X> filter,
        final SingularAttribute<? super ENTITY, ENTITY2> reference,
        final SingularAttribute<? super ENTITY2, ENTITY3> reference2,
        final SingularAttribute<ENTITY3, X> valueField) {
        if (filter.getEquals() != null) {
            return buildEqualsJoin(filter.getEquals(), reference, reference2, valueField);
        } else if (filter.getIn() != null) {
            return buildInJoin(filter.getIn(), reference, reference2, valueField);
        }
        Specification<ENTITY> result = Specification.where(null);
        if (filter.getSpecified() != null) {
            return buildByFieldSpecifiedJoin(filter.getSpecified(), reference, reference2, valueField);
        }
        if (filter.getGreaterThan() != null) {
            result = result.and(buildGreaterThanJoin(filter.getGreaterThan(), reference, reference2, valueField));
        }
        if (filter.getGreaterOrEqualThan() != null) {
            result = result.and(buildGreaterThanOrEqualToJoin(filter.getGreaterOrEqualThan(), reference, reference2, valueField));
        }
        if (filter.getLessThan() != null) {
            result = result.and(buildLessThanJoin(filter.getLessThan(), reference, reference2, valueField));
        }
        if (filter.getLessOrEqualThan() != null) {
            result = result.and(buildLessThanOrEqualToJoin(filter.getLessOrEqualThan(), reference, reference2, valueField));
        }
        return result;
    }

    public static <ENTITY, ENTITY2, ENTITY3, ENTITY4, X extends Comparable<? super X>> Specification<ENTITY> buildReferringEntitySpecification(
        final RangeFilter<X> filter,
        final SingularAttribute<? super ENTITY, ENTITY2> reference,
        final SingularAttribute<? super ENTITY2, ENTITY3> reference2,
        final SingularAttribute<? super ENTITY3, ENTITY4> reference3,
        final SingularAttribute<ENTITY4, X> valueField) {
        if (filter.getEquals() != null) {
            return buildEqualsJoin(filter.getEquals(), reference, reference2, reference3, valueField);
        } else if (filter.getIn() != null) {
            return buildInJoin(filter.getIn(), reference, reference2, reference3, valueField);
        }
        Specification<ENTITY> result = Specification.where(null);
        if (filter.getSpecified() != null) {
            return buildByFieldSpecifiedJoin(filter.getSpecified(), reference, reference2, reference3, valueField);
        }
        if (filter.getGreaterThan() != null) {
            result = result.and(buildGreaterThanJoin(filter.getGreaterThan(), reference, reference2, reference3, valueField));
        }
        if (filter.getGreaterOrEqualThan() != null) {
            result = result.and(buildGreaterThanOrEqualToJoin(filter.getGreaterOrEqualThan(), reference, reference2, reference3, valueField));
        }
        if (filter.getLessThan() != null) {
            result = result.and(buildLessThanJoin(filter.getLessThan(), reference, reference2, reference3, valueField));
        }
        if (filter.getLessOrEqualThan() != null) {
            result = result.and(buildLessThanOrEqualToJoin(filter.getLessOrEqualThan(), reference, reference2, reference3, valueField));
        }
        return result;
    }

    public static <ENTITY, ENTITY2, ENTITY3, ENTITY4, ENTITY5, X extends Comparable<? super X>> Specification<ENTITY> buildReferringEntitySpecification(
        final RangeFilter<X> filter,
        final SingularAttribute<? super ENTITY, ENTITY2> reference,
        final SingularAttribute<? super ENTITY2, ENTITY3> reference2,
        final SingularAttribute<? super ENTITY3, ENTITY4> reference3,
        final SingularAttribute<? super ENTITY4, ENTITY5> reference4,
        final SingularAttribute<ENTITY5, X> valueField) {
        if (filter.getEquals() != null) {
            return buildEqualsJoin(filter.getEquals(), reference, reference2, reference3, reference4, valueField);
        } else if (filter.getIn() != null) {
            return buildInJoin(filter.getIn(), reference, reference2, reference3, reference4, valueField);
        }
        Specification<ENTITY> result = Specification.where(null);
        if (filter.getSpecified() != null) {
            return buildByFieldSpecifiedJoin(filter.getSpecified(), reference, reference2, reference3, reference4, valueField);
        }
        if (filter.getGreaterThan() != null) {
            result = result.and(buildGreaterThanJoin(filter.getGreaterThan(), reference, reference2, reference3, reference4, valueField));
        }
        if (filter.getGreaterOrEqualThan() != null) {
            result = result.and(buildGreaterThanOrEqualToJoin(filter.getGreaterOrEqualThan(), reference, reference2, reference3, reference4, valueField));
        }
        if (filter.getLessThan() != null) {
            result = result.and(buildLessThanJoin(filter.getLessThan(), reference, reference2, reference3, reference4, valueField));
        }
        if (filter.getLessOrEqualThan() != null) {
            result = result.and(buildLessThanOrEqualToJoin(filter.getLessOrEqualThan(), reference, reference2, reference3, reference4, valueField));
        }
        return result;
    }

    public static <ENTITY, ENTITY2, ENTITY3, ENTITY4, ENTITY5, ENTITY6, X extends Comparable<? super X>> Specification<ENTITY> buildReferringEntitySpecification(
        final RangeFilter<X> filter,
        final SingularAttribute<? super ENTITY, ENTITY2> reference,
        final SingularAttribute<? super ENTITY2, ENTITY3> reference2,
        final SingularAttribute<? super ENTITY3, ENTITY4> reference3,
        final SingularAttribute<? super ENTITY4, ENTITY5> reference4,
        final SingularAttribute<? super ENTITY5, ENTITY6> reference5,
        final SingularAttribute<ENTITY6, X> valueField) {
        if (filter.getEquals() != null) {
            return buildEqualsJoin(filter.getEquals(), reference, reference2, reference3, reference4, reference5, valueField);
        } else if (filter.getIn() != null) {
            return buildInJoin(filter.getIn(), reference, reference2, reference3, reference4, reference5, valueField);
        }
        Specification<ENTITY> result = Specification.where(null);
        if (filter.getSpecified() != null) {
            return buildByFieldSpecifiedJoin(filter.getSpecified(), reference, reference2, reference3, reference4, reference5, valueField);
        }
        if (filter.getGreaterThan() != null) {
            result = result.and(buildGreaterThanJoin(filter.getGreaterThan(), reference, reference2, reference3, reference4, reference5, valueField));
        }
        if (filter.getGreaterOrEqualThan() != null) {
            result = result.and(buildGreaterThanOrEqualToJoin(filter.getGreaterOrEqualThan(), reference, reference2, reference3, reference4, reference5, valueField));
        }
        if (filter.getLessThan() != null) {
            result = result.and(buildLessThanJoin(filter.getLessThan(), reference, reference2, reference3, reference4, reference5, valueField));
        }
        if (filter.getLessOrEqualThan() != null) {
            result = result.and(buildLessThanOrEqualToJoin(filter.getLessOrEqualThan(), reference, reference2, reference3, reference4, reference5, valueField));
        }
        return result;
    }

    public static <ENTITY, ENTITY2, ENTITY3, ENTITY4, ENTITY5, ENTITY6, ENTITY7, X extends Comparable<? super X>> Specification<ENTITY> buildReferringEntitySpecification(
        final RangeFilter<X> filter,
        final SingularAttribute<? super ENTITY, ENTITY2> reference,
        final SingularAttribute<? super ENTITY2, ENTITY3> reference2,
        final SingularAttribute<? super ENTITY3, ENTITY4> reference3,
        final SingularAttribute<? super ENTITY4, ENTITY5> reference4,
        final SingularAttribute<? super ENTITY5, ENTITY6> reference5,
        final SingularAttribute<? super ENTITY6, ENTITY7> reference6,
        final SingularAttribute<ENTITY7, X> valueField) {
        if (filter.getEquals() != null) {
            return buildEqualsJoin(filter.getEquals(), reference, reference2, reference3, reference4, reference5, reference6, valueField);
        } else if (filter.getIn() != null) {
            return buildInJoin(filter.getIn(), reference, reference2, reference3, reference4, reference5, reference6, valueField);
        }
        Specification<ENTITY> result = Specification.where(null);
        if (filter.getSpecified() != null) {
            return buildByFieldSpecifiedJoin(filter.getSpecified(), reference, reference2, reference3, reference4, reference5, reference6, valueField);
        }
        if (filter.getGreaterThan() != null) {
            result = result.and(buildGreaterThanJoin(filter.getGreaterThan(), reference, reference2, reference3, reference4, reference5, reference6, valueField));
        }
        if (filter.getGreaterOrEqualThan() != null) {
            result = result.and(buildGreaterThanOrEqualToJoin(filter.getGreaterOrEqualThan(), reference, reference2, reference3, reference4, reference5, reference6, valueField));
        }
        if (filter.getLessThan() != null) {
            result = result.and(buildLessThanJoin(filter.getLessThan(), reference, reference2, reference3, reference4, reference5, reference6, valueField));
        }
        if (filter.getLessOrEqualThan() != null) {
            result = result.and(buildLessThanOrEqualToJoin(filter.getLessOrEqualThan(), reference, reference2, reference3, reference4, reference5, reference6, valueField));
        }
        return result;
    }

    public static <ENTITY, ENTITY2, ENTITY3> Specification<ENTITY> buildReferringEntitySpecification(
        StringFilter filter,
        SingularAttribute<? super ENTITY, ENTITY2> reference,
        SingularAttribute<? super ENTITY2, ENTITY3> reference2,
        SingularAttribute<ENTITY3, String> valueField) {
        if (filter.getEquals() != null) {
            return buildEqualsJoin(filter.getEquals(), reference, reference2, valueField);
        } else if (filter.getIn() != null) {
            return buildInJoin(filter.getIn(), reference, reference2, valueField);
        } else if (filter.getContains() != null) {
            return buildLikeUpperJoin(filter.getContains(), reference, reference2, valueField);
        } else if (filter.getSpecified() != null) {
            return buildByFieldSpecifiedJoin(filter.getSpecified(), reference, reference2, valueField);
        }
        return null;
    }

    public static <ENTITY, ENTITY2, ENTITY3, ENTITY4> Specification<ENTITY> buildReferringEntitySpecification(
        StringFilter filter,
        SingularAttribute<? super ENTITY, ENTITY2> reference,
        SingularAttribute<? super ENTITY2, ENTITY3> reference2,
        SingularAttribute<? super ENTITY3, ENTITY4> reference3,
        SingularAttribute<ENTITY4, String> valueField) {
        if (filter.getEquals() != null) {
            return buildEqualsJoin(filter.getEquals(), reference, reference2, reference3, valueField);
        } else if (filter.getIn() != null) {
            return buildInJoin(filter.getIn(), reference, reference2, reference3, valueField);
        } else if (filter.getContains() != null) {
            return buildLikeUpperJoin(filter.getContains(), reference, reference2, reference3, valueField);
        } else if (filter.getSpecified() != null) {
            return buildByFieldSpecifiedJoin(filter.getSpecified(), reference, reference2, reference3, valueField);
        }
        return null;
    }

    public static <ENTITY, ENTITY2, ENTITY3, ENTITY4, ENTITY5> Specification<ENTITY> buildReferringEntitySpecification(
        StringFilter filter,
        SingularAttribute<? super ENTITY, ENTITY2> reference,
        SingularAttribute<? super ENTITY2, ENTITY3> reference2,
        SingularAttribute<? super ENTITY3, ENTITY4> reference3,
        SingularAttribute<? super ENTITY4, ENTITY5> reference4,
        SingularAttribute<ENTITY5, String> valueField) {
        if (filter.getEquals() != null) {
            return buildEqualsJoin(filter.getEquals(), reference, reference2, reference3, reference4, valueField);
        } else if (filter.getIn() != null) {
            return buildInJoin(filter.getIn(), reference, reference2, reference3, reference4, valueField);
        } else if (filter.getContains() != null) {
            return buildLikeUpperJoin(filter.getContains(), reference, reference2, reference3, reference4, valueField);
        } else if (filter.getSpecified() != null) {
            return buildByFieldSpecifiedJoin(filter.getSpecified(), reference, reference2, reference3, reference4, valueField);
        }
        return null;
    }

    public static <ENTITY, ENTITY2, ENTITY3, ENTITY4, ENTITY5, ENTITY6> Specification<ENTITY> buildReferringEntitySpecification(
        StringFilter filter,
        SingularAttribute<? super ENTITY, ENTITY2> reference,
        SingularAttribute<? super ENTITY2, ENTITY3> reference2,
        SingularAttribute<? super ENTITY3, ENTITY4> reference3,
        SingularAttribute<? super ENTITY4, ENTITY5> reference4,
        SingularAttribute<? super ENTITY5, ENTITY6> reference5,
        SingularAttribute<ENTITY6, String> valueField) {
        if (filter.getEquals() != null) {
            return buildEqualsJoin(filter.getEquals(), reference, reference2, reference3, reference4, reference5, valueField);
        } else if (filter.getIn() != null) {
            return buildInJoin(filter.getIn(), reference, reference2, reference3, reference4, reference5, valueField);
        } else if (filter.getContains() != null) {
            return buildLikeUpperJoin(filter.getContains(), reference, reference2, reference3, reference4, reference5, valueField);
        } else if (filter.getSpecified() != null) {
            return buildByFieldSpecifiedJoin(filter.getSpecified(), reference, reference2, reference3, reference4, reference5, valueField);
        }
        return null;
    }

    public static <ENTITY, ENTITY2, ENTITY3, ENTITY4, ENTITY5, ENTITY6, ENTITY7> Specification<ENTITY> buildReferringEntitySpecification(
        StringFilter filter,
        SingularAttribute<? super ENTITY, ENTITY2> reference,
        SingularAttribute<? super ENTITY2, ENTITY3> reference2,
        SingularAttribute<? super ENTITY3, ENTITY4> reference3,
        SingularAttribute<? super ENTITY4, ENTITY5> reference4,
        SingularAttribute<? super ENTITY5, ENTITY6> reference5,
        SingularAttribute<? super ENTITY6, ENTITY7> reference6,
        SingularAttribute<ENTITY7, String> valueField) {
        if (filter.getEquals() != null) {
            return buildEqualsJoin(filter.getEquals(), reference, reference2, reference3, reference4, reference5, reference6, valueField);
        } else if (filter.getIn() != null) {
            return buildInJoin(filter.getIn(), reference, reference2, reference3, reference4, reference5, reference6, valueField);
        } else if (filter.getContains() != null) {
            return buildLikeUpperJoin(filter.getContains(), reference, reference2, reference3, reference4, reference5, reference6, valueField);
        } else if (filter.getSpecified() != null) {
            return buildByFieldSpecifiedJoin(filter.getSpecified(), reference, reference2, reference3, reference4, reference5, reference6, valueField);
        }
        return null;
    }

    protected static String wrapLikeQuery(String txt) {
        return "%" + txt.toUpperCase() + '%';
    }
}
