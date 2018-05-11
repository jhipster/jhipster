package io.github.jhipster.service.util;

import io.github.jhipster.service.filter.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Allow to build or erase parts of criteria received in REST API, often we want to make sure that for getAll or getOne we filter on specific data that user can only get and not others
 * <p>The logic follow: equals &gt; in &gt; contains &gt; specified = gt = gte = lt = lte. It is similar to order of {@linkplain io.github.jhipster.service.QueryService}</p>
 * <p>If further checks are required like when building equals criteria that "IN" must be empty or only contain the equal value passed, you can extends this class</p>
 * <p>Created on 2018/2/6.</p>
 *
 * @author Yoann CAPLAIN
 */
@SuppressWarnings({"unused"})
public class CriteriaUtil {

    private static final Logger log = LoggerFactory.getLogger(CriteriaUtil.class);

    private CriteriaUtil() {
    }

    protected static <T extends Filter<?>> T createCriteria(final Class<T> criteriaClass) {
        try {
            return criteriaClass.newInstance();
        } catch (InstantiationException e) {
            log.error("Make sure that filters passed can have a default empty constructor", e);
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            log.error("Cannot instantiate", e);
            throw new IllegalStateException(e);
        }
    }

    /**
     * @param criteriaClass  Class to instantiate if criteria is null
     * @param criteriaPassed Criteria given by user, can be null
     * @param value          Value criteria should be equal to
     * @param replaceValue   True if value of criteria equals should be replaced or throw if different
     * @param <T>            Filter generic type
     * @param <U>            Value generic type
     * @return Filter with equals value set
     * @throws IllegalArgumentException if criteria equals value is not null and is different from value passed and {@code replaceValue} is false
     */
    protected static <T extends Filter<U>, U> T buildEqualsCriteria(final Class<T> criteriaClass, @Nullable final T criteriaPassed, @NotNull final U value, final boolean replaceValue) {
        if (value == null)
            throw new IllegalArgumentException("Value param cannot be null");

        final T criteria = criteriaPassed == null ? createCriteria(criteriaClass) : criteriaPassed;
        if (!replaceValue && criteria.getEquals() != null && !criteria.getEquals().equals(value))
            throw new IllegalArgumentException("Equals filter value is not allowed");
        criteria.setEquals(value);
        return criteria;
    }

    /**
     * Build a filter with the Equals value with the one passed in param, leave other attribute untouched
     *
     * @param value Value to set
     * @return Filter with equals value set
     */
    public static LongFilter buildEqualsCriteria(@NotNull final Long value) {
        return buildEqualsCriteria(new LongFilter(), value);
    }

    /**
     * Replace the Equals value with the one passed in param, leave other attribute untouched
     *
     * @param criteriaPassed Criteria to build the Equals filtering (can be null)
     * @param value          Value to set
     * @return Filter with equals value set
     */
    public static LongFilter buildEqualsCriteria(@Nullable final LongFilter criteriaPassed, @NotNull final Long value) {
        return buildEqualsCriteria(LongFilter.class, criteriaPassed, value, true);
    }

    /**
     * Build a filter with the Equals value with the one passed in param, leave other attribute untouched
     * <p>Throws if equals value is set and different from the one passed</p>
     *
     * @param criteriaPassed Criteria to build the Equals filtering (can be null)
     * @param value          Value to set
     * @return Filter with equals value set
     */
    public static LongFilter buildEqualsCriteriaOrThrow(@Nullable final LongFilter criteriaPassed, @NotNull final Long value) {
        return buildEqualsCriteria(LongFilter.class, criteriaPassed, value, false);
    }

    public static IntegerFilter buildEqualsCriteria(@NotNull final Integer value) {
        return buildEqualsCriteria(new IntegerFilter(), value);
    }

    public static IntegerFilter buildEqualsCriteria(@Nullable final IntegerFilter criteriaPassed, @NotNull final Integer value) {
        return buildEqualsCriteria(IntegerFilter.class, criteriaPassed, value, true);
    }

    public static IntegerFilter buildEqualsCriteriaOrThrow(@Nullable final IntegerFilter criteriaPassed, @NotNull final Integer value) {
        return buildEqualsCriteria(IntegerFilter.class, criteriaPassed, value, false);
    }

    public static BooleanFilter buildEqualsCriteria(@NotNull final Boolean value) {
        return buildEqualsCriteria(new BooleanFilter(), value);
    }

    public static BooleanFilter buildEqualsCriteria(@Nullable final BooleanFilter criteriaPassed, @NotNull final Boolean value) {
        return buildEqualsCriteria(BooleanFilter.class, criteriaPassed, value, true);
    }

    public static BooleanFilter buildEqualsCriteriaOrThrow(@Nullable final BooleanFilter criteriaPassed, @NotNull final Boolean value) {
        return buildEqualsCriteria(BooleanFilter.class, criteriaPassed, value, false);
    }

    public static StringFilter buildEqualsCriteria(@NotNull final String value) {
        return buildEqualsCriteria(new StringFilter(), value);
    }

    public static StringFilter buildEqualsCriteria(@Nullable final StringFilter criteriaPassed, @NotNull final String
        value) {
        return buildEqualsCriteria(StringFilter.class, criteriaPassed, value, true);
    }

    public static StringFilter buildEqualsCriteriaOrThrow(@Nullable final StringFilter criteriaPassed, @NotNull final String
        value) {
        return buildEqualsCriteria(StringFilter.class, criteriaPassed, value, false);
    }

    public static ShortFilter buildEqualsCriteria(@NotNull final Short value) {
        return buildEqualsCriteria(new ShortFilter(), value);
    }

    public static ShortFilter buildEqualsCriteria(@Nullable final ShortFilter criteriaPassed, @NotNull final Short value) {
        return buildEqualsCriteria(ShortFilter.class, criteriaPassed, value, true);
    }

    public static ShortFilter buildEqualsCriteriaOrThrow(@Nullable final ShortFilter criteriaPassed, @NotNull final Short value) {
        return buildEqualsCriteria(ShortFilter.class, criteriaPassed, value, false);
    }

    public static DoubleFilter buildEqualsCriteria(@NotNull final Double value) {
        return buildEqualsCriteria(new DoubleFilter(), value);
    }

    public static DoubleFilter buildEqualsCriteria(@Nullable final DoubleFilter criteriaPassed, @NotNull final Double value) {
        return buildEqualsCriteria(DoubleFilter.class, criteriaPassed, value, true);
    }

    public static DoubleFilter buildEqualsCriteriaOrThrow(@Nullable final DoubleFilter criteriaPassed, @NotNull final Double value) {
        return buildEqualsCriteria(DoubleFilter.class, criteriaPassed, value, false);
    }

    public static FloatFilter buildEqualsCriteria(@NotNull final Float value) {
        return buildEqualsCriteria(new FloatFilter(), value);
    }

    public static FloatFilter buildEqualsCriteria(@Nullable final FloatFilter criteriaPassed, @NotNull final Float value) {
        return buildEqualsCriteria(FloatFilter.class, criteriaPassed, value, true);
    }

    public static FloatFilter buildEqualsCriteriaOrThrow(@Nullable final FloatFilter criteriaPassed, @NotNull final Float value) {
        return buildEqualsCriteria(FloatFilter.class, criteriaPassed, value, false);
    }

    public static BigDecimalFilter buildEqualsCriteria(@NotNull final BigDecimal value) {
        return buildEqualsCriteria(new BigDecimalFilter(), value);
    }

    public static BigDecimalFilter buildEqualsCriteria(@Nullable final BigDecimalFilter criteriaPassed, @NotNull final BigDecimal value) {
        return buildEqualsCriteria(BigDecimalFilter.class, criteriaPassed, value, true);
    }

    public static BigDecimalFilter buildEqualsCriteriaOrThrow(@Nullable final BigDecimalFilter criteriaPassed, @NotNull final BigDecimal value) {
        return buildEqualsCriteria(BigDecimalFilter.class, criteriaPassed, value, false);
    }

    public static InstantFilter buildEqualsCriteria(@NotNull final Instant value) {
        return buildEqualsCriteria(new InstantFilter(), value);
    }

    public static InstantFilter buildEqualsCriteria(@Nullable final InstantFilter criteriaPassed, @NotNull final Instant value) {
        return buildEqualsCriteria(InstantFilter.class, criteriaPassed, value, true);
    }

    public static InstantFilter buildEqualsCriteriaOrThrow(@Nullable final InstantFilter criteriaPassed, @NotNull final Instant value) {
        return buildEqualsCriteria(InstantFilter.class, criteriaPassed, value, false);
    }

    public static LocalDateFilter buildEqualsCriteria(@NotNull final LocalDate value) {
        return buildEqualsCriteria(new LocalDateFilter(), value);
    }

    public static LocalDateFilter buildEqualsCriteria(@Nullable final LocalDateFilter criteriaPassed, @NotNull final LocalDate value) {
        return buildEqualsCriteria(LocalDateFilter.class, criteriaPassed, value, true);
    }

    public static LocalDateFilter buildEqualsCriteriaOrThrow(@Nullable final LocalDateFilter criteriaPassed, @NotNull final LocalDate value) {
        return buildEqualsCriteria(LocalDateFilter.class, criteriaPassed, value, false);
    }

    /**
     * Replace the IN values with the one passed in list param, leave other attribute untouched
     *
     * @param criteriaClass Class to instantiate and return type
     * @param values        List of elements to put in filter IN (can not be empty)
     * @param <T>           Filter generic type
     * @param <U>           Value generic type
     * @return Filter with in value set
     */
    public static <T extends Filter<U>, U> T buildInCriteria(@NotNull final Class<T> criteriaClass, @NotNull final List<U> values) {
        return buildInCriteria(criteriaClass, null, values);
    }

    /**
     * Build a filter with the IN values with the one passed in list param, leave other attribute untouched
     *
     * @param values List of elements to put in filter IN (can not be empty)
     * @return A {@link LongFilter} with a non-empty IN list or throws
     * @throws IllegalArgumentException if list is empty
     * @throws IllegalStateException    when result criteria IN is empty
     */
    public static LongFilter buildInCriteria(@NotNull final List<Long> values) {
        return buildInCriteria(new LongFilter(), values);
    }

    /**
     * @param values List of elements to put in filter IN (can not be empty)
     * @return A Filter with a non-empty IN list or throws
     * @see #buildInCriteria(List)
     */
    public static ShortFilter buildInCriteriaShort(@NotNull final List<Short> values) {
        return buildInCriteria(new ShortFilter(), values);
    }

    /**
     * @param values List of elements to put in filter IN (can not be empty)
     * @return A Filter with a non-empty IN list or throws
     * @see #buildInCriteria(List)
     */
    public static IntegerFilter buildInCriteriaInteger(@NotNull final List<Integer> values) {
        return buildInCriteria(new IntegerFilter(), values);
    }

    /**
     * @param values List of elements to put in filter IN (can not be empty)
     * @return A Filter with a non-empty IN list or throws
     * @see #buildInCriteria(List)
     */
    public static StringFilter buildInCriteriaString(@NotNull final List<String> values) {
        return buildInCriteria(new StringFilter(), values);
    }

    /**
     * Replace the IN values with the one passed in list param, leave other attribute untouched
     *
     * @param criteriaPassed Criteria to build the IN filtering (can be null)
     * @param values         List of elements to put in filter IN (can not be empty)
     * @return A {@link LongFilter} with a non-empty IN list or throws
     * @throws IllegalArgumentException if list is empty
     * @throws IllegalStateException    when result criteria IN is empty
     */
    public static LongFilter buildInCriteria(@Nullable final LongFilter criteriaPassed, @NotNull final List<Long> values) {
        return buildInCriteria(LongFilter.class, criteriaPassed, values);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null)
     * @param values         List of elements to put in filter IN (can not be empty)
     * @return A Filter with a non-empty IN list or throws
     * @see #buildInCriteria(LongFilter, List)
     */
    public static ShortFilter buildInCriteria(@Nullable final ShortFilter criteriaPassed, @NotNull final List<Short> values) {
        return buildInCriteria(ShortFilter.class, criteriaPassed, values);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null)
     * @param values         List of elements to put in filter IN (can not be empty)
     * @return A Filter with a non-empty IN list or throws
     * @see #buildInCriteria(LongFilter, List)
     */
    public static IntegerFilter buildInCriteria(@Nullable final IntegerFilter criteriaPassed, @NotNull final List<Integer> values) {
        return buildInCriteria(IntegerFilter.class, criteriaPassed, values);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null)
     * @param values         List of elements to put in filter IN (can not be empty)
     * @return A Filter with a non-empty IN list or throws
     * @see #buildInCriteria(LongFilter, List)
     */
    public static StringFilter buildInCriteria(@Nullable final StringFilter criteriaPassed, @NotNull final List<String> values) {
        return buildInCriteria(StringFilter.class, criteriaPassed, values);
    }

    /**
     * Replace the IN values with the one passed in list param, leave other attribute untouched
     *
     * @param criteriaClass  Class to instantiate if criteria is null
     * @param criteriaPassed Criteria to build the IN filtering (can be null)
     * @param values         List of elements to put in filter IN (can not be empty)
     * @param <T>            Filter generic type
     * @param <U>            Value generic type
     * @return Filter with in value set
     */
    public static <T extends Filter<U>, U> T buildInCriteria(final Class<T> criteriaClass, @Nullable final T criteriaPassed, @NotNull final List<U> values) {
        if (values.isEmpty())
            throw new IllegalArgumentException("List param cannot be empty");

        final T criteria = criteriaPassed == null ? createCriteria(criteriaClass) : criteriaPassed;
        if (criteria.getIn() == null)
            criteria.setIn(new ArrayList<>(values.size()));

        criteria.getIn().clear();
        criteria.getIn().addAll(values);

        checkInNotEmpty(criteria);

        return criteria;
    }

    /**
     * If equals criteria is defined then it takes precedence on IN filter.
     * <p>If equals and in filters are null then in filter is filled with list passed</p>
     * <p>If equals or in filters values are not contained in list passed, throws</p>
     * <p>If list contains <b>only one element</b>, it will <b>build the equal criteria</b> instead. Unless 'in' is already specified with different values</p>
     *
     * @param criteriaPassed Criteria to build the IN filtering (can be null) and check if equals is different than
     *                       all contained in the list
     * @param values         List of in
     * @return A {@link LongFilter} with equal or a non-empty IN list filter or throws
     * @throws IllegalArgumentException if list is empty
     * @throws IllegalArgumentException If equals or some in filters values are not contained in list passed
     * @throws IllegalStateException    when result criteria equals and IN are empty
     */
    public static LongFilter buildInCriteriaOrThrow(@Nullable final LongFilter criteriaPassed, @NotNull final List<Long> values) {
        return buildInCriteriaOrThrow(LongFilter.class, criteriaPassed, values, true);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null) and check if equals is different than
     *                       all contained in the list
     * @param values         List of in
     * @return A Filter with equal or a non-empty IN list filter or throws
     * @see #buildInCriteria(LongFilter, List)
     */
    public static ShortFilter buildInCriteriaOrThrow(@Nullable final ShortFilter criteriaPassed, @NotNull final List<Short> values) {
        return buildInCriteriaOrThrow(ShortFilter.class, criteriaPassed, values, true);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null) and check if equals is different than
     *                       all contained in the list
     * @param values         List of in
     * @return A Filter with equal or a non-empty IN list filter or throws
     * @see #buildInCriteria(LongFilter, List)
     */
    public static IntegerFilter buildInCriteriaOrThrow(@Nullable final IntegerFilter criteriaPassed, @NotNull final List<Integer> values) {
        return buildInCriteriaOrThrow(IntegerFilter.class, criteriaPassed, values, true);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null) and check if equals is different than
     *                       all contained in the list
     * @param values         List of in
     * @return A Filter with equal or a non-empty IN list filter or throws
     * @see #buildInCriteria(LongFilter, List)
     */
    public static StringFilter buildInCriteriaOrThrow(@Nullable final StringFilter criteriaPassed, @NotNull final List<String> values) {
        return buildInCriteriaOrThrow(StringFilter.class, criteriaPassed, values, true);
    }


    /**
     * If equals criteria is defined then it takes precedence on IN filter. If IN is defined and all values are allowed then it returns without replacing these values by the ones in the list passed.
     * <p>If equals and in filters are null then in filter is filled with list passed</p>
     * <p>If equals or in filters values are not contained in list passed, throws</p>
     * <p>If list contains <b>only one element</b>, it will <b>build the equal criteria</b> instead. Unless 'in' is already specified with different values</p>
     *
     * @param criteriaClass  Class to instantiate if criteria is null
     * @param criteriaPassed Criteria to build the IN filtering (can be null) and check if equals is different than
     *                       all contained in the list
     * @param values         List of elements to put in filter IN (can not be empty)
     * @param <T>            Filter generic type
     * @param <U>            Value generic type
     * @return A {@link LongFilter} with equal or a non-empty IN list filter or throws
     * @throws IllegalArgumentException if list is empty
     * @throws IllegalArgumentException If equals or some in filters values are not contained in list passed
     * @throws IllegalStateException    when result criteria equals and IN are empty
     */
    public static <T extends Filter<U>, U> T buildInCriteriaOrThrowNoReplace(final Class<T> criteriaClass, @Nullable final T criteriaPassed, @NotNull final List<U> values) {
        return buildInCriteriaOrThrow(criteriaClass, criteriaPassed, values, false);
    }

    /**
     * If equals criteria is defined then it takes precedence on IN filter. If IN is defined and all values are allowed then it returns without replacing these values by the ones in the list passed.
     * <p>If equals and in filters are null then in filter is filled with list passed</p>
     * <p>If equals or in filters values are not contained in list passed, throws</p>
     * <p>If list contains <b>only one element</b>, it will <b>build the equal criteria</b> instead. Unless 'in' is already specified with different values</p>
     *
     * @param criteriaPassed Criteria to build the IN filtering (can be null) and check if equals is different than
     *                       all contained in the list
     * @param values         List of elements to put in filter IN (can not be empty)
     * @return A {@link LongFilter} with equal or a non-empty IN list filter or throws
     * @throws IllegalArgumentException if list is empty
     * @throws IllegalArgumentException If equals or some in filters values are not contained in list passed
     * @throws IllegalStateException    when result criteria equals and IN are empty
     */
    public static LongFilter buildInCriteriaOrThrowNoReplace(@Nullable final LongFilter criteriaPassed, @NotNull final List<Long> values) {
        return buildInCriteriaOrThrow(LongFilter.class, criteriaPassed, values, false);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null) and check if equals is different than
     *                       all contained in the list
     * @param values         List of elements to put in filter IN (can not be empty)
     * @return A Filter with equal or a non-empty IN list filter or throws
     * @see #buildInCriteriaOrThrowNoReplace(LongFilter, List)
     */
    public static ShortFilter buildInCriteriaOrThrowNoReplace(@Nullable final ShortFilter criteriaPassed, @NotNull final List<Short> values) {
        return buildInCriteriaOrThrow(ShortFilter.class, criteriaPassed, values, false);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null) and check if equals is different than
     *                       all contained in the list
     * @param values         List of elements to put in filter IN (can not be empty)
     * @return A Filter with equal or a non-empty IN list filter or throws
     * @see #buildInCriteriaOrThrowNoReplace(LongFilter, List)
     */
    public static IntegerFilter buildInCriteriaOrThrowNoReplace(@Nullable final IntegerFilter criteriaPassed, @NotNull final List<Integer> values) {
        return buildInCriteriaOrThrow(IntegerFilter.class, criteriaPassed, values, false);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null) and check if equals is different than
     *                       all contained in the list
     * @param values         List of elements to put in filter IN (can not be empty)
     * @return A Filter with equal or a non-empty IN list filter or throws
     * @see #buildInCriteriaOrThrowNoReplace(LongFilter, List)
     */
    public static StringFilter buildInCriteriaOrThrowNoReplace(@Nullable final StringFilter criteriaPassed, @NotNull final List<String> values) {
        return buildInCriteriaOrThrow(StringFilter.class, criteriaPassed, values, false);
    }

    /**
     * If equals criteria is defined then it takes precedence on IN filter.
     * <p>If equals and in filters are null then in filter is filled with list passed</p>
     * <p>If equals or in filters values are not contained in list passed, throws</p>
     * <p>If list contains <b>only one element</b>, it will <b>build the equal criteria</b> instead. Unless 'in' is already specified with different values</p>
     *
     * @param criteriaClass  Class to instantiate if criteria is null
     * @param criteriaPassed Criteria to build the IN filtering (can be null) and check if equals is different than
     *                       all contained in the list
     * @param values         List of in
     * @param replaceIn      If true then any value in "in" will be replaced with values from the list. Otherwise if "in" has values it does not overrides those (still need to be included in list passed)
     * @param <T>            Filter generic type
     * @param <U>            Value generic type
     * @return A Filter with equal or a non-empty IN list filter or throws
     * @throws IllegalArgumentException if list is empty
     * @throws IllegalArgumentException If equals or some in filters values are not contained in list passed
     * @throws IllegalStateException    when result criteria equals and IN are empty
     */
    protected static <T extends Filter<U>, U> T buildInCriteriaOrThrow(final Class<T> criteriaClass, @Nullable final T criteriaPassed, @NotNull final List<U> values, final boolean replaceIn) {
        if (values.isEmpty())
            throw new IllegalArgumentException("List param cannot be empty");

        final T criteria = criteriaPassed == null ? createCriteria(criteriaClass) : criteriaPassed;
        if (criteria.getEquals() != null) {
            if (!values.contains(criteria.getEquals()))
                throw new IllegalArgumentException("Equals filter specified is not allowed");
            else
                return criteria;
        }
        if (criteria.getIn() != null && !values.containsAll(criteria.getIn())) {
            throw new IllegalArgumentException("Some in filter specified are not allowed");
        }
        if (values.size() == 1 && (criteria.getIn() == null || criteria.getIn().isEmpty()
            || (criteria.getIn().size() == 1 && criteria.getIn().get(0).equals(values.get(0)))
        )) {
            criteria.setEquals(values.get(0));
            return criteria;
        }
        if (!replaceIn && criteria.getIn() != null && !criteria.getIn().isEmpty()) {
            return criteria;
        }
        return buildInCriteria(criteriaClass, criteria, values);
    }


    protected static <T extends Filter<?>> void checkInNotEmpty(final T criteriaPassed) {
        if (criteriaPassed.getIn() == null || criteriaPassed.getIn().isEmpty()) {
            // If in is empty then the query will be "... IN ()" so it will result in an error. Kept that verification here just in case of multi-thread and list is modified between check of first line (which should never be the case...)
            throw new IllegalStateException("A IN criteria cannot result in an empty list");
        }
    }
}
