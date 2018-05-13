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
 * Allow to build or erase parts of criteria received in REST API, often we want to make sure that for getAll or getOne we filter on specific data that user can only get and not others.
 * <p>The logic follow: equals &gt; in &gt; contains &gt; specified = gt = gte = lt = lte. It is similar to order of {@linkplain io.github.jhipster.service.QueryService}</p>
 * <p>If further checks are required like when building equals criteria that "IN" must be empty or only contain the equal value passed, you can extends this class.</p>
 * <p>Some methods may be "useless" but it is provided as is, user can decide to use or not.</p>
 * <p>Created on 2018/2/6.</p>
 *
 * @author Yoann CAPLAIN
 */
@SuppressWarnings({"unused"})
public class CriteriaUtil {

    private static final Logger log = LoggerFactory.getLogger(CriteriaUtil.class);

    protected CriteriaUtil() {
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

    // We could use this method instead of creating so many overloaded methods with different type but it would require the user to cast to proper filter type for some of the methods
    /*
    @SuppressWarnings("unchecked")
    protected static <T extends Filter<U>, U> T createCriteria(final U value) {
        if (value == null)
            throw new IllegalArgumentException("Value param cannot be null");
        if (value.getClass().equals(Long.class))
            return (T) new LongFilter();
        if (value.getClass().equals(Short.class))
            return (T) new ShortFilter();
        if (value.getClass().equals(Integer.class))
            return (T) new IntegerFilter();
        if (value.getClass().equals(String.class))
            return (T) new StringFilter();
        if (value.getClass().equals(Double.class))
            return (T) new DoubleFilter();
        if (value.getClass().equals(Float.class))
            return (T) new FloatFilter();
        if (value.getClass().equals(BigDecimal.class))
            return (T) new BigDecimalFilter();
        if (value.getClass().equals(LocalDate.class))
            return (T) new LocalDateFilter();
        if (value.getClass().equals(Instant.class))
            return (T) new InstantFilter();
        if (value.getClass().equals(Boolean.class))
            return (T) new BooleanFilter();

        throw new IllegalStateException("Unknown value type");
    }
    @SuppressWarnings("unchecked")
    protected static <T extends RangeFilter<U>, U extends Comparable<? super U>> T createCriteria(final U value) {
        // could repeat same here
    }
    //*/

    /**
     * @param criteriaPassed Criteria to check (can be null)
     * @param <T>            Filter generic type
     * @return True if some filter values overlap each others and will be ignored when building jpa specification
     */
    public static <T extends Filter<?>> boolean isCriteriaOverlap(@Nullable final T criteriaPassed) {
        if (criteriaPassed == null)
            return false;
        if (isEqualsDefined(criteriaPassed))
            return isInDefined(criteriaPassed) || isSpecifiedDefined(criteriaPassed);
        if (isInDefined(criteriaPassed))
            return isSpecifiedDefined(criteriaPassed);
        return false;
    }

    /**
     * @param criteriaPassed Criteria to check (can be null)
     * @return True if some filter values overlap each others and will be ignored when building jpa specification
     */
    public static boolean isCriteriaOverlap(@Nullable final StringFilter criteriaPassed) {
        if (criteriaPassed == null)
            return false;
        if (isEqualsDefined(criteriaPassed))
            return isInDefined(criteriaPassed) || isSpecifiedDefined(criteriaPassed) || isContainsDefined(criteriaPassed);
        if (isInDefined(criteriaPassed))
            return isSpecifiedDefined(criteriaPassed) || isContainsDefined(criteriaPassed);
        if (isContainsDefined(criteriaPassed))
            return isSpecifiedDefined(criteriaPassed);
        return false;
    }

    /**
     * @param criteriaPassed Criteria to check (can be null)
     * @param <T>            Filter generic type
     * @return True if some filter values overlap each others and will be ignored when building jpa specification
     */
    public static <T extends RangeFilter<?>> boolean isCriteriaOverlap(@Nullable final T criteriaPassed) {
        if (criteriaPassed == null)
            return false;
        if (isEqualsDefined(criteriaPassed))
            return isInDefined(criteriaPassed) || isSpecifiedDefined(criteriaPassed) || isAnyRangeDefined(criteriaPassed);
        if (isInDefined(criteriaPassed))
            return isSpecifiedDefined(criteriaPassed) || isAnyRangeDefined(criteriaPassed);
        return false;
    }

    /**
     * @param criteriaPassed Criteria to check (can be null)
     * @param <T>            Filter generic type
     * @return True if equals value is set
     */
    public static <T extends Filter<?>> boolean isEqualsDefined(@Nullable final T criteriaPassed) {
        if (criteriaPassed == null)
            return false;
        return criteriaPassed.getEquals() != null;
    }

    /**
     * @param criteriaPassed Criteria to check (can be null)
     * @param <T>            Filter generic type
     * @return True if in value is set
     */
    public static <T extends Filter<?>> boolean isInDefined(@Nullable final T criteriaPassed) {
        if (criteriaPassed == null)
            return false;
        return criteriaPassed.getIn() != null;
    }

    /**
     * @param criteriaPassed Criteria to check (can be null)
     * @return True if contains value is set
     */
    public static boolean isContainsDefined(@Nullable final StringFilter criteriaPassed) {
        if (criteriaPassed == null)
            return false;
        return criteriaPassed.getContains() != null;
    }

    /**
     * @param criteriaPassed Criteria to check (can be null)
     * @param <T>            Filter generic type
     * @return True if specified value is set
     */
    public static <T extends Filter<?>> boolean isSpecifiedDefined(@Nullable final T criteriaPassed) {
        if (criteriaPassed == null)
            return false;
        return criteriaPassed.getSpecified() != null;
    }

    /**
     * @param criteriaPassed Criteria to check (can be null)
     * @param <T>            Filter generic type
     * @return True if greaterThan value is set
     */
    public static <T extends RangeFilter<?>> boolean isGreaterThanDefined(@Nullable final T criteriaPassed) {
        if (criteriaPassed == null)
            return false;
        return criteriaPassed.getGreaterThan() != null;
    }

    /**
     * @param criteriaPassed Criteria to check (can be null)
     * @param <T>            Filter generic type
     * @return True if greaterThanOrEqual value is set
     */
    public static <T extends RangeFilter<?>> boolean isGreaterThanOrEqualDefined(@Nullable final T criteriaPassed) {
        if (criteriaPassed == null)
            return false;
        return criteriaPassed.getGreaterOrEqualThan() != null;
    }

    /**
     * @param criteriaPassed Criteria to check (can be null)
     * @param <T>            Filter generic type
     * @return True if lessThan value is set
     */
    public static <T extends RangeFilter<?>> boolean isLessThanDefined(@Nullable final T criteriaPassed) {
        if (criteriaPassed == null)
            return false;
        return criteriaPassed.getLessThan() != null;
    }

    /**
     * @param criteriaPassed Criteria to check (can be null)
     * @param <T>            Filter generic type
     * @return True if lessThanOrEqual value is set
     */
    public static <T extends RangeFilter<?>> boolean isLessThanOrEqualDefined(@Nullable final T criteriaPassed) {
        if (criteriaPassed == null)
            return false;
        return criteriaPassed.getLessOrEqualThan() != null;
    }

    /**
     * @param criteriaPassed Criteria to check (can be null)
     * @param <T>            Filter generic type
     * @return True if any of greaterThan/greaterThanOrEqual/lessThan/lessThanOrEqual value is set
     */
    public static <T extends RangeFilter<?>> boolean isAnyRangeDefined(@Nullable final T criteriaPassed) {
        if (criteriaPassed == null)
            return false;
        return isGreaterThanDefined(criteriaPassed) || isGreaterThanOrEqualDefined(criteriaPassed) || isLessThanDefined(criteriaPassed) || isLessThanOrEqualDefined(criteriaPassed);
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
     * <p>Care should be taken as equals value is not checked neither removed if not in list provided</p>
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
     * <p>Care should be taken as equals value is not checked neither removed if not in list provided</p>
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
     * @param values List of elements to put in filter IN (can not be empty)
     * @return A Filter with a non-empty IN list or throws
     * @see #buildInCriteria(List)
     */
    public static DoubleFilter buildInCriteriaDouble(@NotNull final List<Double> values) {
        return buildInCriteria(new DoubleFilter(), values);
    }

    /**
     * @param values List of elements to put in filter IN (can not be empty)
     * @return A Filter with a non-empty IN list or throws
     * @see #buildInCriteria(List)
     */
    public static FloatFilter buildInCriteriaFloat(@NotNull final List<Float> values) {
        return buildInCriteria(new FloatFilter(), values);
    }

    /**
     * @param values List of elements to put in filter IN (can not be empty)
     * @return A Filter with a non-empty IN list or throws
     * @see #buildInCriteria(List)
     */
    public static BigDecimalFilter buildInCriteriaBigDecimal(@NotNull final List<BigDecimal> values) {
        return buildInCriteria(new BigDecimalFilter(), values);
    }

    /**
     * @param values List of elements to put in filter IN (can not be empty)
     * @return A Filter with a non-empty IN list or throws
     * @see #buildInCriteria(List)
     */
    public static LocalDateFilter buildInCriteriaLocalDate(@NotNull final List<LocalDate> values) {
        return buildInCriteria(new LocalDateFilter(), values);
    }

    /**
     * @param values List of elements to put in filter IN (can not be empty)
     * @return A Filter with a non-empty IN list or throws
     * @see #buildInCriteria(List)
     */
    public static InstantFilter buildInCriteriaInstant(@NotNull final List<Instant> values) {
        return buildInCriteria(new InstantFilter(), values);
    }

    /**
     * Replace the IN values with the one passed in list param, leave other attribute untouched
     * <p>Care should be taken as equals value is not checked neither removed if not in list provided</p>
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
     * @param criteriaPassed Criteria to build the IN filtering (can be null)
     * @param values         List of elements to put in filter IN (can not be empty)
     * @return A Filter with a non-empty IN list or throws
     * @see #buildInCriteria(LongFilter, List)
     */
    public static DoubleFilter buildInCriteria(@Nullable final DoubleFilter criteriaPassed, @NotNull final List<Double> values) {
        return buildInCriteria(DoubleFilter.class, criteriaPassed, values);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null)
     * @param values         List of elements to put in filter IN (can not be empty)
     * @return A Filter with a non-empty IN list or throws
     * @see #buildInCriteria(LongFilter, List)
     */
    public static FloatFilter buildInCriteria(@Nullable final FloatFilter criteriaPassed, @NotNull final List<Float> values) {
        return buildInCriteria(FloatFilter.class, criteriaPassed, values);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null)
     * @param values         List of elements to put in filter IN (can not be empty)
     * @return A Filter with a non-empty IN list or throws
     * @see #buildInCriteria(LongFilter, List)
     */
    public static BigDecimalFilter buildInCriteria(@Nullable final BigDecimalFilter criteriaPassed, @NotNull final List<BigDecimal> values) {
        return buildInCriteria(BigDecimalFilter.class, criteriaPassed, values);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null)
     * @param values         List of elements to put in filter IN (can not be empty)
     * @return A Filter with a non-empty IN list or throws
     * @see #buildInCriteria(LongFilter, List)
     */
    public static LocalDateFilter buildInCriteria(@Nullable final LocalDateFilter criteriaPassed, @NotNull final List<LocalDate> values) {
        return buildInCriteria(LocalDateFilter.class, criteriaPassed, values);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null)
     * @param values         List of elements to put in filter IN (can not be empty)
     * @return A Filter with a non-empty IN list or throws
     * @see #buildInCriteria(LongFilter, List)
     */
    public static InstantFilter buildInCriteria(@Nullable final InstantFilter criteriaPassed, @NotNull final List<Instant> values) {
        return buildInCriteria(InstantFilter.class, criteriaPassed, values);
    }

    /**
     * Replace the IN values with the one passed in list param, leave other attribute untouched
     * <p>Care should be taken as equals value is not checked neither removed if not in list provided</p>
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
     * Remove from IN criteria un-authorized values, leave other attribute untouched.
     * <p>If IN is empty after remove un-authorized values, it is filled with list passed</p>
     * <p>Care should be taken as equals value is not checked neither removed if not in list provided</p>
     *
     * @param criteriaClass  Class to instantiate if criteria is null
     * @param criteriaPassed Criteria to build the IN filtering (can be null)
     * @param values         List of authorized elements for filter IN (can not be empty)
     * @param <T>            Filter generic type
     * @param <U>            Value generic type
     * @return Filter with in value set
     */
    public static <T extends Filter<U>, U> T buildInCriteriaFiltered(final Class<T> criteriaClass, @Nullable final T criteriaPassed, @NotNull final List<U> values) {
        if (values.isEmpty())
            throw new IllegalArgumentException("List param cannot be empty");

        final T criteria = criteriaPassed == null ? createCriteria(criteriaClass) : criteriaPassed;
        if (criteria.getIn() == null)
            criteria.setIn(new ArrayList<>(values.size()));

        criteria.getIn().retainAll(values);
        if (criteria.getIn().isEmpty())
            criteria.getIn().addAll(values);

        checkInNotEmpty(criteria);

        return criteria;
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null)
     * @param values         List of authorized elements for filter IN (can not be empty)
     * @return Filter with in value set
     * @see #buildInCriteriaFiltered(Class, Filter, List)
     */
    public static LongFilter buildInCriteriaFiltered(@Nullable final LongFilter criteriaPassed, @NotNull final List<Long> values) {
        return buildInCriteriaFiltered(LongFilter.class, criteriaPassed, values);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null)
     * @param values         List of authorized elements for filter IN (can not be empty)
     * @return Filter with in value set
     * @see #buildInCriteriaFiltered(Class, Filter, List)
     */
    public static ShortFilter buildInCriteriaFiltered(@Nullable final ShortFilter criteriaPassed, @NotNull final List<Short> values) {
        return buildInCriteriaFiltered(ShortFilter.class, criteriaPassed, values);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null)
     * @param values         List of authorized elements for filter IN (can not be empty)
     * @return Filter with in value set
     * @see #buildInCriteriaFiltered(Class, Filter, List)
     */
    public static IntegerFilter buildInCriteriaFiltered(@Nullable final IntegerFilter criteriaPassed, @NotNull final List<Integer> values) {
        return buildInCriteriaFiltered(IntegerFilter.class, criteriaPassed, values);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null)
     * @param values         List of authorized elements for filter IN (can not be empty)
     * @return Filter with in value set
     * @see #buildInCriteriaFiltered(Class, Filter, List)
     */
    public static StringFilter buildInCriteriaFiltered(@Nullable final StringFilter criteriaPassed, @NotNull final List<String> values) {
        return buildInCriteriaFiltered(StringFilter.class, criteriaPassed, values);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null)
     * @param values         List of authorized elements for filter IN (can not be empty)
     * @return Filter with in value set
     * @see #buildInCriteriaFiltered(Class, Filter, List)
     */
    public static DoubleFilter buildInCriteriaFiltered(@Nullable final DoubleFilter criteriaPassed, @NotNull final List<Double> values) {
        return buildInCriteriaFiltered(DoubleFilter.class, criteriaPassed, values);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null)
     * @param values         List of authorized elements for filter IN (can not be empty)
     * @return Filter with in value set
     * @see #buildInCriteriaFiltered(Class, Filter, List)
     */
    public static FloatFilter buildInCriteriaFiltered(@Nullable final FloatFilter criteriaPassed, @NotNull final List<Float> values) {
        return buildInCriteriaFiltered(FloatFilter.class, criteriaPassed, values);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null)
     * @param values         List of authorized elements for filter IN (can not be empty)
     * @return Filter with in value set
     * @see #buildInCriteriaFiltered(Class, Filter, List)
     */
    public static BigDecimalFilter buildInCriteriaFiltered(@Nullable final BigDecimalFilter criteriaPassed, @NotNull final List<BigDecimal> values) {
        return buildInCriteriaFiltered(BigDecimalFilter.class, criteriaPassed, values);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null)
     * @param values         List of authorized elements for filter IN (can not be empty)
     * @return Filter with in value set
     * @see #buildInCriteriaFiltered(Class, Filter, List)
     */
    public static LocalDateFilter buildInCriteriaFiltered(@Nullable final LocalDateFilter criteriaPassed, @NotNull final List<LocalDate> values) {
        return buildInCriteriaFiltered(LocalDateFilter.class, criteriaPassed, values);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null)
     * @param values         List of authorized elements for filter IN (can not be empty)
     * @return Filter with in value set
     * @see #buildInCriteriaFiltered(Class, Filter, List)
     */
    public static InstantFilter buildInCriteriaFiltered(@Nullable final InstantFilter criteriaPassed, @NotNull final List<Instant> values) {
        return buildInCriteriaFiltered(InstantFilter.class, criteriaPassed, values);
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
     * @param criteriaPassed Criteria to build the IN filtering (can be null) and check if equals is different than
     *                       all contained in the list
     * @param values         List of in
     * @return A Filter with equal or a non-empty IN list filter or throws
     * @see #buildInCriteria(LongFilter, List)
     */
    public static DoubleFilter buildInCriteriaOrThrow(@Nullable final DoubleFilter criteriaPassed, @NotNull final List<Double> values) {
        return buildInCriteriaOrThrow(DoubleFilter.class, criteriaPassed, values, true);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null) and check if equals is different than
     *                       all contained in the list
     * @param values         List of in
     * @return A Filter with equal or a non-empty IN list filter or throws
     * @see #buildInCriteria(LongFilter, List)
     */
    public static FloatFilter buildInCriteriaOrThrow(@Nullable final FloatFilter criteriaPassed, @NotNull final List<Float> values) {
        return buildInCriteriaOrThrow(FloatFilter.class, criteriaPassed, values, true);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null) and check if equals is different than
     *                       all contained in the list
     * @param values         List of in
     * @return A Filter with equal or a non-empty IN list filter or throws
     * @see #buildInCriteria(LongFilter, List)
     */
    public static BigDecimalFilter buildInCriteriaOrThrow(@Nullable final BigDecimalFilter criteriaPassed, @NotNull final List<BigDecimal> values) {
        return buildInCriteriaOrThrow(BigDecimalFilter.class, criteriaPassed, values, true);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null) and check if equals is different than
     *                       all contained in the list
     * @param values         List of in
     * @return A Filter with equal or a non-empty IN list filter or throws
     * @see #buildInCriteria(LongFilter, List)
     */
    public static LocalDateFilter buildInCriteriaOrThrow(@Nullable final LocalDateFilter criteriaPassed, @NotNull final List<LocalDate> values) {
        return buildInCriteriaOrThrow(LocalDateFilter.class, criteriaPassed, values, true);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null) and check if equals is different than
     *                       all contained in the list
     * @param values         List of in
     * @return A Filter with equal or a non-empty IN list filter or throws
     * @see #buildInCriteria(LongFilter, List)
     */
    public static InstantFilter buildInCriteriaOrThrow(@Nullable final InstantFilter criteriaPassed, @NotNull final List<Instant> values) {
        return buildInCriteriaOrThrow(InstantFilter.class, criteriaPassed, values, true);
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
     * @param criteriaPassed Criteria to build the IN filtering (can be null) and check if equals is different than
     *                       all contained in the list
     * @param values         List of elements to put in filter IN (can not be empty)
     * @return A Filter with equal or a non-empty IN list filter or throws
     * @see #buildInCriteriaOrThrowNoReplace(LongFilter, List)
     */
    public static DoubleFilter buildInCriteriaOrThrowNoReplace(@Nullable final DoubleFilter criteriaPassed, @NotNull final List<Double> values) {
        return buildInCriteriaOrThrow(DoubleFilter.class, criteriaPassed, values, false);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null) and check if equals is different than
     *                       all contained in the list
     * @param values         List of elements to put in filter IN (can not be empty)
     * @return A Filter with equal or a non-empty IN list filter or throws
     * @see #buildInCriteriaOrThrowNoReplace(LongFilter, List)
     */
    public static FloatFilter buildInCriteriaOrThrowNoReplace(@Nullable final FloatFilter criteriaPassed, @NotNull final List<Float> values) {
        return buildInCriteriaOrThrow(FloatFilter.class, criteriaPassed, values, false);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null) and check if equals is different than
     *                       all contained in the list
     * @param values         List of elements to put in filter IN (can not be empty)
     * @return A Filter with equal or a non-empty IN list filter or throws
     * @see #buildInCriteriaOrThrowNoReplace(LongFilter, List)
     */
    public static BigDecimalFilter buildInCriteriaOrThrowNoReplace(@Nullable final BigDecimalFilter criteriaPassed, @NotNull final List<BigDecimal> values) {
        return buildInCriteriaOrThrow(BigDecimalFilter.class, criteriaPassed, values, false);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null) and check if equals is different than
     *                       all contained in the list
     * @param values         List of elements to put in filter IN (can not be empty)
     * @return A Filter with equal or a non-empty IN list filter or throws
     * @see #buildInCriteriaOrThrowNoReplace(LongFilter, List)
     */
    public static LocalDateFilter buildInCriteriaOrThrowNoReplace(@Nullable final LocalDateFilter criteriaPassed, @NotNull final List<LocalDate> values) {
        return buildInCriteriaOrThrow(LocalDateFilter.class, criteriaPassed, values, false);
    }

    /**
     * @param criteriaPassed Criteria to build the IN filtering (can be null) and check if equals is different than
     *                       all contained in the list
     * @param values         List of elements to put in filter IN (can not be empty)
     * @return A Filter with equal or a non-empty IN list filter or throws
     * @see #buildInCriteriaOrThrowNoReplace(LongFilter, List)
     */
    public static InstantFilter buildInCriteriaOrThrowNoReplace(@Nullable final InstantFilter criteriaPassed, @NotNull final List<Instant> values) {
        return buildInCriteriaOrThrow(InstantFilter.class, criteriaPassed, values, false);
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

    /**
     * Build a filter with the Contains value with the one passed in param, leave other attribute untouched
     * <p>Throws if contains value is set and different from the one passed (not case sensitive)</p>
     *
     * @param criteriaPassed Criteria to build the Contains filtering (can be null)
     * @param value          Value to set (cannot be null or empty)
     * @param replaceValue   True if value of criteria contains should be replaced or throw if different (not case sensitive)
     * @return Filter with contains value set
     * @throws IllegalArgumentException if {@code value} is null or empty
     * @throws IllegalArgumentException if criteria contains value is not null and is different from value passed and {@code replaceValue} is false
     */
    protected static StringFilter buildContainsCriteria(@Nullable final StringFilter criteriaPassed, @NotNull final String value, final boolean replaceValue) {
        if (value == null || value.isEmpty())
            throw new IllegalArgumentException("Contains value cannot be empty");
        final StringFilter criteria = criteriaPassed == null ? new StringFilter() : criteriaPassed;
        if (!replaceValue && criteria.getContains() != null && !criteria.getContains().equalsIgnoreCase(value))
            throw new IllegalArgumentException("Contains filter value is not allowed");
        criteria.setContains(value);
        return criteria;
    }

    /**
     * Build a filter with the Contains value with the one passed in param, leave other attribute untouched
     *
     * @param value Value to set (cannot be null or empty)
     * @return Filter with contains value set
     * @throws IllegalArgumentException if {@code value} is null or empty
     */
    public static StringFilter buildContainsCriteria(@NotNull final String value) {
        return buildContainsCriteria(new StringFilter(), value);
    }

    /**
     * Replace the Contains value with the one passed in param, leave other attribute untouched
     *
     * @param criteriaPassed Criteria to build the Contains filtering (can be null)
     * @param value          Value to set (cannot be null or empty)
     * @return Filter with contains value set
     * @throws IllegalArgumentException if {@code value} is null or empty
     */
    public static StringFilter buildContainsCriteria(@Nullable final StringFilter criteriaPassed, @NotNull final String value) {
        return buildContainsCriteria(criteriaPassed, value, true);
    }

    /**
     * Build a filter with the Contains value with the one passed in param, leave other attribute untouched
     * <p>Throws if contains value is set and different from the one passed (not case sensitive)</p>
     *
     * @param criteriaPassed Criteria to build the Contains filtering (can be null)
     * @param value          Value to set (cannot be null or empty)
     * @return Filter with contains value set
     * @throws IllegalArgumentException if {@code value} is null or empty
     */
    public static StringFilter buildContainsCriteriaOrThrow(@Nullable final StringFilter criteriaPassed, @NotNull final String value) {
        return buildContainsCriteria(criteriaPassed, value, false);
    }


    /**
     * Build a filter with the Specified value with the one passed in param, leave other attribute untouched
     * <p>Throws if specified value is set and different from the one passed (not case sensitive)</p>
     *
     * @param criteriaClass  Class to instantiate if criteria is null
     * @param criteriaPassed Criteria to build the Specified filtering (can be null)
     * @param specified          Value to set (cannot be null)
     * @param replaceValue True if value of criteria specified should be replaced or throw if different
     * @return Filter with specified value set
     * @throws IllegalArgumentException if {@code value} is null or empty
     */
    public static <T extends Filter<?>> T buildSpecifiedCriteria(final Class<T> criteriaClass, @Nullable final T criteriaPassed, @NotNull final Boolean specified, final boolean replaceValue) {
        if (specified == null)
            throw new IllegalArgumentException("Specified value cannot be null");

        final T criteria = criteriaPassed == null ? createCriteria(criteriaClass) : criteriaPassed;
        if (!replaceValue && criteria.getSpecified() != null && !criteria.getSpecified().equals(specified))
            throw new IllegalArgumentException("Specified filter value is not allowed");
        criteria.setSpecified(specified);
        return criteria;
    }


    /**
     * Build a filter with the GreaterThan value with the one passed in param, leave other attribute untouched
     * <p>Throws if greaterThan value is set and different from the one passed</p>
     *
     * @param criteriaClass  Class to instantiate if criteria is null
     * @param criteriaPassed Criteria to build the greaterThan filtering (can be null)
     * @param value          Value to set (cannot be null)
     * @param replaceValue   True if value of criteria greaterThan should be replaced or throw if different
     * @return Filter with greaterThan value set
     * @throws IllegalArgumentException if {@code value} is null
     * @throws IllegalArgumentException if criteria greaterThan value is not null and is different from value passed and {@code replaceValue} is false
     */
    public static <T extends RangeFilter<U>, U extends Comparable<? super U>> T buildGreaterThanCriteriaOrThrow(final Class<T> criteriaClass, @Nullable final T criteriaPassed, @NotNull final U value, final boolean replaceValue) {
        if (value == null)
            throw new IllegalArgumentException("GreaterThan value cannot be null");

        final T criteria = criteriaPassed == null ? createCriteria(criteriaClass) : criteriaPassed;
        if (!replaceValue && criteria.getGreaterThan() != null && !criteria.getGreaterThan().equals(value))
            throw new IllegalArgumentException("GreaterThan filter value is not allowed");
        criteria.setGreaterThan(value);
        return criteria;
    }

    /**
     * Build a filter with the GreaterThan value with the one passed in param if not set already, leave other attribute untouched
     * <p>Does not replace greaterThan value if set.</p>
     * <p>Throws if greaterThan value is set and lower than the one passed.</p>
     *
     * @param criteriaClass  Class to instantiate if criteria is null
     * @param criteriaPassed Criteria to build the greaterThan filtering (can be null)
     * @param value          Value to set (cannot be null)
     * @return Filter with greaterThan value set
     * @throws IllegalArgumentException if {@code value} is null
     * @throws IllegalArgumentException if criteria greaterThan value is not null and is lower than value passed
     */
    public static <T extends RangeFilter<U>, U extends Comparable<? super U>> T buildGreaterThanCriteriaOrThrowOrMore(final Class<T> criteriaClass, @Nullable final T criteriaPassed, @NotNull final U value) {
        if (value == null)
            throw new IllegalArgumentException("GreaterThan value cannot be null");

        final T criteria = criteriaPassed == null ? createCriteria(criteriaClass) : criteriaPassed;
        if (criteria.getGreaterThan() != null && criteria.getGreaterThan().compareTo(value) < 0)
            throw new IllegalArgumentException("GreaterThan filter value is not allowed");
        if (criteria.getGreaterThan() == null)
            criteria.setGreaterThan(value);
        return criteria;
    }

    public static LongFilter buildGreaterThanCriteria(@NotNull final Long value) {
        return buildGreaterThanCriteria(new LongFilter(), value);
    }

    public static ShortFilter buildGreaterThanCriteria(@NotNull final Short value) {
        return buildGreaterThanCriteria(new ShortFilter(), value);
    }

    public static IntegerFilter buildGreaterThanCriteria(@NotNull final Integer value) {
        return buildGreaterThanCriteria(new IntegerFilter(), value);
    }

    public static DoubleFilter buildGreaterThanCriteria(@NotNull final Double value) {
        return buildGreaterThanCriteria(new DoubleFilter(), value);
    }

    public static FloatFilter buildGreaterThanCriteria(@NotNull final Float value) {
        return buildGreaterThanCriteria(new FloatFilter(), value);
    }

    public static BigDecimalFilter buildGreaterThanCriteria(@NotNull final BigDecimal value) {
        return buildGreaterThanCriteria(new BigDecimalFilter(), value);
    }

    public static LocalDateFilter buildGreaterThanCriteria(@NotNull final LocalDate value) {
        return buildGreaterThanCriteria(new LocalDateFilter(), value);
    }

    public static InstantFilter buildGreaterThanCriteria(@NotNull final Instant value) {
        return buildGreaterThanCriteria(new InstantFilter(), value);
    }

    public static LongFilter buildGreaterThanCriteria(@Nullable final LongFilter criteriaPassed, @NotNull final Long value) {
        return buildGreaterThanCriteriaOrThrow(LongFilter.class, criteriaPassed, value, true);
    }

    public static ShortFilter buildGreaterThanCriteria(@Nullable final ShortFilter criteriaPassed, @NotNull final Short value) {
        return buildGreaterThanCriteriaOrThrow(ShortFilter.class, criteriaPassed, value, true);
    }

    public static IntegerFilter buildGreaterThanCriteria(@Nullable final IntegerFilter criteriaPassed, @NotNull final Integer value) {
        return buildGreaterThanCriteriaOrThrow(IntegerFilter.class, criteriaPassed, value, true);
    }

    public static DoubleFilter buildGreaterThanCriteria(@Nullable final DoubleFilter criteriaPassed, @NotNull final Double value) {
        return buildGreaterThanCriteriaOrThrow(DoubleFilter.class, criteriaPassed, value, true);
    }

    public static FloatFilter buildGreaterThanCriteria(@Nullable final FloatFilter criteriaPassed, @NotNull final Float value) {
        return buildGreaterThanCriteriaOrThrow(FloatFilter.class, criteriaPassed, value, true);
    }

    public static BigDecimalFilter buildGreaterThanCriteria(@Nullable final BigDecimalFilter criteriaPassed, @NotNull final BigDecimal value) {
        return buildGreaterThanCriteriaOrThrow(BigDecimalFilter.class, criteriaPassed, value, true);
    }

    public static LocalDateFilter buildGreaterThanCriteria(@Nullable final LocalDateFilter criteriaPassed, @NotNull final LocalDate value) {
        return buildGreaterThanCriteriaOrThrow(LocalDateFilter.class, criteriaPassed, value, true);
    }

    public static InstantFilter buildGreaterThanCriteria(@Nullable final InstantFilter criteriaPassed, @NotNull final Instant value) {
        return buildGreaterThanCriteriaOrThrow(InstantFilter.class, criteriaPassed, value, true);
    }

    public static LongFilter buildGreaterThanCriteriaOrThrow(@Nullable final LongFilter criteriaPassed, @NotNull final Long value) {
        return buildGreaterThanCriteriaOrThrow(LongFilter.class, criteriaPassed, value, false);
    }

    public static ShortFilter buildGreaterThanCriteriaOrThrow(@Nullable final ShortFilter criteriaPassed, @NotNull final Short value) {
        return buildGreaterThanCriteriaOrThrow(ShortFilter.class, criteriaPassed, value, false);
    }

    public static IntegerFilter buildGreaterThanCriteriaOrThrow(@Nullable final IntegerFilter criteriaPassed, @NotNull final Integer value) {
        return buildGreaterThanCriteriaOrThrow(IntegerFilter.class, criteriaPassed, value, false);
    }

    public static DoubleFilter buildGreaterThanCriteriaOrThrow(@Nullable final DoubleFilter criteriaPassed, @NotNull final Double value) {
        return buildGreaterThanCriteriaOrThrow(DoubleFilter.class, criteriaPassed, value, false);
    }

    public static FloatFilter buildGreaterThanCriteriaOrThrow(@Nullable final FloatFilter criteriaPassed, @NotNull final Float value) {
        return buildGreaterThanCriteriaOrThrow(FloatFilter.class, criteriaPassed, value, false);
    }

    public static BigDecimalFilter buildGreaterThanCriteriaOrThrow(@Nullable final BigDecimalFilter criteriaPassed, @NotNull final BigDecimal value) {
        return buildGreaterThanCriteriaOrThrow(BigDecimalFilter.class, criteriaPassed, value, false);
    }

    public static LocalDateFilter buildGreaterThanCriteriaOrThrow(@Nullable final LocalDateFilter criteriaPassed, @NotNull final LocalDate value) {
        return buildGreaterThanCriteriaOrThrow(LocalDateFilter.class, criteriaPassed, value, false);
    }

    public static InstantFilter buildGreaterThanCriteriaOrThrow(@Nullable final InstantFilter criteriaPassed, @NotNull final Instant value) {
        return buildGreaterThanCriteriaOrThrow(InstantFilter.class, criteriaPassed, value, false);
    }

    public static LongFilter buildGreaterThanCriteriaOrThrowOrMore(@Nullable final LongFilter criteriaPassed, @NotNull final Long value) {
        return buildGreaterThanCriteriaOrThrowOrMore(LongFilter.class, criteriaPassed, value);
    }

    public static ShortFilter buildGreaterThanCriteriaOrThrowOrMore(@Nullable final ShortFilter criteriaPassed, @NotNull final Short value) {
        return buildGreaterThanCriteriaOrThrowOrMore(ShortFilter.class, criteriaPassed, value);
    }

    public static IntegerFilter buildGreaterThanCriteriaOrThrowOrMore(@Nullable final IntegerFilter criteriaPassed, @NotNull final Integer value) {
        return buildGreaterThanCriteriaOrThrowOrMore(IntegerFilter.class, criteriaPassed, value);
    }

    public static DoubleFilter buildGreaterThanCriteriaOrThrowOrMore(@Nullable final DoubleFilter criteriaPassed, @NotNull final Double value) {
        return buildGreaterThanCriteriaOrThrowOrMore(DoubleFilter.class, criteriaPassed, value);
    }

    public static FloatFilter buildGreaterThanCriteriaOrThrowOrMore(@Nullable final FloatFilter criteriaPassed, @NotNull final Float value) {
        return buildGreaterThanCriteriaOrThrowOrMore(FloatFilter.class, criteriaPassed, value);
    }

    public static BigDecimalFilter buildGreaterThanCriteriaOrThrowOrMore(@Nullable final BigDecimalFilter criteriaPassed, @NotNull final BigDecimal value) {
        return buildGreaterThanCriteriaOrThrowOrMore(BigDecimalFilter.class, criteriaPassed, value);
    }

    public static LocalDateFilter buildGreaterThanCriteriaOrThrowOrMore(@Nullable final LocalDateFilter criteriaPassed, @NotNull final LocalDate value) {
        return buildGreaterThanCriteriaOrThrowOrMore(LocalDateFilter.class, criteriaPassed, value);
    }

    public static InstantFilter buildGreaterThanCriteriaOrThrowOrMore(@Nullable final InstantFilter criteriaPassed, @NotNull final Instant value) {
        return buildGreaterThanCriteriaOrThrowOrMore(InstantFilter.class, criteriaPassed, value);
    }

    /**
     * Build a filter with the GreaterThanOrEqual value with the one passed in param, leave other attribute untouched
     * <p>Throws if greaterThanOrEqual value is set and different from the one passed</p>
     *
     * @param criteriaClass  Class to instantiate if criteria is null
     * @param criteriaPassed Criteria to build the greaterThanOrEqual filtering (can be null)
     * @param value          Value to set (cannot be null)
     * @param replaceValue   True if value of criteria greaterThanOrEqual should be replaced or throw if different
     * @return Filter with greaterThanOrEqual value set
     * @throws IllegalArgumentException if {@code value} is null
     * @throws IllegalArgumentException if criteria greaterThanOrEqual value is not null and is different from value passed and {@code replaceValue} is false
     */
    public static <T extends RangeFilter<U>, U extends Comparable<? super U>> T buildGreaterThanOrEqualCriteriaOrThrow(final Class<T> criteriaClass, @Nullable final T criteriaPassed, @NotNull final U value, final boolean replaceValue) {
        if (value == null)
            throw new IllegalArgumentException("GreaterThanOrEqual value cannot be null");

        final T criteria = criteriaPassed == null ? createCriteria(criteriaClass) : criteriaPassed;
        if (!replaceValue && criteria.getGreaterOrEqualThan() != null && !criteria.getGreaterOrEqualThan().equals(value))
            throw new IllegalArgumentException("GreaterThanOrEqual filter value is not allowed");
        criteria.setGreaterOrEqualThan(value);
        return criteria;
    }

    /**
     * Build a filter with the GreaterThanOrEqual value with the one passed in param if not set already, leave other attribute untouched
     * <p>Does not replace greaterThanOrEqual value if set.</p>
     * <p>Throws if greaterThanOrEqual value is set and lower than the one passed.</p>
     *
     * @param criteriaClass  Class to instantiate if criteria is null
     * @param criteriaPassed Criteria to build the greaterThanOrEqual filtering (can be null)
     * @param value          Value to set (cannot be null)
     * @return Filter with greaterThanOrEqual value set
     * @throws IllegalArgumentException if {@code value} is null
     * @throws IllegalArgumentException if criteria greaterThanOrEqual value is not null and is lower than value passed
     */
    public static <T extends RangeFilter<U>, U extends Comparable<? super U>> T buildGreaterThanOrEqualCriteriaOrThrowOrMore(final Class<T> criteriaClass, @Nullable final T criteriaPassed, @NotNull final U value) {
        if (value == null)
            throw new IllegalArgumentException("GreaterThanOrEqual value cannot be null");

        final T criteria = criteriaPassed == null ? createCriteria(criteriaClass) : criteriaPassed;
        if (criteria.getGreaterOrEqualThan() != null && criteria.getGreaterOrEqualThan().compareTo(value) < 0)
            throw new IllegalArgumentException("GreaterThanOrEqual filter value is not allowed");
        if (criteria.getGreaterOrEqualThan() == null)
            criteria.setGreaterOrEqualThan(value);
        return criteria;
    }

    public static LongFilter buildGreaterThanOrEqualCriteria(@NotNull final Long value) {
        return buildGreaterThanOrEqualCriteria(new LongFilter(), value);
    }

    public static ShortFilter buildGreaterThanOrEqualCriteria(@NotNull final Short value) {
        return buildGreaterThanOrEqualCriteria(new ShortFilter(), value);
    }

    public static IntegerFilter buildGreaterThanOrEqualCriteria(@NotNull final Integer value) {
        return buildGreaterThanOrEqualCriteria(new IntegerFilter(), value);
    }

    public static DoubleFilter buildGreaterThanOrEqualCriteria(@NotNull final Double value) {
        return buildGreaterThanOrEqualCriteria(new DoubleFilter(), value);
    }

    public static FloatFilter buildGreaterThanOrEqualCriteria(@NotNull final Float value) {
        return buildGreaterThanOrEqualCriteria(new FloatFilter(), value);
    }

    public static BigDecimalFilter buildGreaterThanOrEqualCriteria(@NotNull final BigDecimal value) {
        return buildGreaterThanOrEqualCriteria(new BigDecimalFilter(), value);
    }

    public static LocalDateFilter buildGreaterThanOrEqualCriteria(@NotNull final LocalDate value) {
        return buildGreaterThanOrEqualCriteria(new LocalDateFilter(), value);
    }

    public static InstantFilter buildGreaterThanOrEqualCriteria(@NotNull final Instant value) {
        return buildGreaterThanOrEqualCriteria(new InstantFilter(), value);
    }

    public static LongFilter buildGreaterThanOrEqualCriteria(@Nullable final LongFilter criteriaPassed, @NotNull final Long value) {
        return buildGreaterThanOrEqualCriteriaOrThrow(LongFilter.class, criteriaPassed, value, true);
    }

    public static ShortFilter buildGreaterThanOrEqualCriteria(@Nullable final ShortFilter criteriaPassed, @NotNull final Short value) {
        return buildGreaterThanOrEqualCriteriaOrThrow(ShortFilter.class, criteriaPassed, value, true);
    }

    public static IntegerFilter buildGreaterThanOrEqualCriteria(@Nullable final IntegerFilter criteriaPassed, @NotNull final Integer value) {
        return buildGreaterThanOrEqualCriteriaOrThrow(IntegerFilter.class, criteriaPassed, value, true);
    }

    public static DoubleFilter buildGreaterThanOrEqualCriteria(@Nullable final DoubleFilter criteriaPassed, @NotNull final Double value) {
        return buildGreaterThanOrEqualCriteriaOrThrow(DoubleFilter.class, criteriaPassed, value, true);
    }

    public static FloatFilter buildGreaterThanOrEqualCriteria(@Nullable final FloatFilter criteriaPassed, @NotNull final Float value) {
        return buildGreaterThanOrEqualCriteriaOrThrow(FloatFilter.class, criteriaPassed, value, true);
    }

    public static BigDecimalFilter buildGreaterThanOrEqualCriteria(@Nullable final BigDecimalFilter criteriaPassed, @NotNull final BigDecimal value) {
        return buildGreaterThanOrEqualCriteriaOrThrow(BigDecimalFilter.class, criteriaPassed, value, true);
    }

    public static LocalDateFilter buildGreaterThanOrEqualCriteria(@Nullable final LocalDateFilter criteriaPassed, @NotNull final LocalDate value) {
        return buildGreaterThanOrEqualCriteriaOrThrow(LocalDateFilter.class, criteriaPassed, value, true);
    }

    public static InstantFilter buildGreaterThanOrEqualCriteria(@Nullable final InstantFilter criteriaPassed, @NotNull final Instant value) {
        return buildGreaterThanOrEqualCriteriaOrThrow(InstantFilter.class, criteriaPassed, value, true);
    }

    public static LongFilter buildGreaterThanOrEqualCriteriaOrThrow(@Nullable final LongFilter criteriaPassed, @NotNull final Long value) {
        return buildGreaterThanOrEqualCriteriaOrThrow(LongFilter.class, criteriaPassed, value, false);
    }

    public static ShortFilter buildGreaterThanOrEqualCriteriaOrThrow(@Nullable final ShortFilter criteriaPassed, @NotNull final Short value) {
        return buildGreaterThanOrEqualCriteriaOrThrow(ShortFilter.class, criteriaPassed, value, false);
    }

    public static IntegerFilter buildGreaterThanOrEqualCriteriaOrThrow(@Nullable final IntegerFilter criteriaPassed, @NotNull final Integer value) {
        return buildGreaterThanOrEqualCriteriaOrThrow(IntegerFilter.class, criteriaPassed, value, false);
    }

    public static DoubleFilter buildGreaterThanOrEqualCriteriaOrThrow(@Nullable final DoubleFilter criteriaPassed, @NotNull final Double value) {
        return buildGreaterThanOrEqualCriteriaOrThrow(DoubleFilter.class, criteriaPassed, value, false);
    }

    public static FloatFilter buildGreaterThanOrEqualCriteriaOrThrow(@Nullable final FloatFilter criteriaPassed, @NotNull final Float value) {
        return buildGreaterThanOrEqualCriteriaOrThrow(FloatFilter.class, criteriaPassed, value, false);
    }

    public static BigDecimalFilter buildGreaterThanOrEqualCriteriaOrThrow(@Nullable final BigDecimalFilter criteriaPassed, @NotNull final BigDecimal value) {
        return buildGreaterThanOrEqualCriteriaOrThrow(BigDecimalFilter.class, criteriaPassed, value, false);
    }

    public static LocalDateFilter buildGreaterThanOrEqualCriteriaOrThrow(@Nullable final LocalDateFilter criteriaPassed, @NotNull final LocalDate value) {
        return buildGreaterThanOrEqualCriteriaOrThrow(LocalDateFilter.class, criteriaPassed, value, false);
    }

    public static InstantFilter buildGreaterThanOrEqualCriteriaOrThrow(@Nullable final InstantFilter criteriaPassed, @NotNull final Instant value) {
        return buildGreaterThanOrEqualCriteriaOrThrow(InstantFilter.class, criteriaPassed, value, false);
    }

    public static LongFilter buildGreaterThanOrEqualCriteriaOrThrowOrMore(@Nullable final LongFilter criteriaPassed, @NotNull final Long value) {
        return buildGreaterThanOrEqualCriteriaOrThrowOrMore(LongFilter.class, criteriaPassed, value);
    }

    public static ShortFilter buildGreaterThanOrEqualCriteriaOrThrowOrMore(@Nullable final ShortFilter criteriaPassed, @NotNull final Short value) {
        return buildGreaterThanOrEqualCriteriaOrThrowOrMore(ShortFilter.class, criteriaPassed, value);
    }

    public static IntegerFilter buildGreaterThanOrEqualCriteriaOrThrowOrMore(@Nullable final IntegerFilter criteriaPassed, @NotNull final Integer value) {
        return buildGreaterThanOrEqualCriteriaOrThrowOrMore(IntegerFilter.class, criteriaPassed, value);
    }

    public static DoubleFilter buildGreaterThanOrEqualCriteriaOrThrowOrMore(@Nullable final DoubleFilter criteriaPassed, @NotNull final Double value) {
        return buildGreaterThanOrEqualCriteriaOrThrowOrMore(DoubleFilter.class, criteriaPassed, value);
    }

    public static FloatFilter buildGreaterThanOrEqualCriteriaOrThrowOrMore(@Nullable final FloatFilter criteriaPassed, @NotNull final Float value) {
        return buildGreaterThanOrEqualCriteriaOrThrowOrMore(FloatFilter.class, criteriaPassed, value);
    }

    public static BigDecimalFilter buildGreaterThanOrEqualCriteriaOrThrowOrMore(@Nullable final BigDecimalFilter criteriaPassed, @NotNull final BigDecimal value) {
        return buildGreaterThanOrEqualCriteriaOrThrowOrMore(BigDecimalFilter.class, criteriaPassed, value);
    }

    public static LocalDateFilter buildGreaterThanOrEqualCriteriaOrThrowOrMore(@Nullable final LocalDateFilter criteriaPassed, @NotNull final LocalDate value) {
        return buildGreaterThanOrEqualCriteriaOrThrowOrMore(LocalDateFilter.class, criteriaPassed, value);
    }

    public static InstantFilter buildGreaterThanOrEqualCriteriaOrThrowOrMore(@Nullable final InstantFilter criteriaPassed, @NotNull final Instant value) {
        return buildGreaterThanOrEqualCriteriaOrThrowOrMore(InstantFilter.class, criteriaPassed, value);
    }


    /**
     * Build a filter with the LessThan value with the one passed in param, leave other attribute untouched
     * <p>Throws if lessThan value is set and different from the one passed</p>
     *
     * @param criteriaClass  Class to instantiate if criteria is null
     * @param criteriaPassed Criteria to build the lessThan filtering (can be null)
     * @param value          Value to set (cannot be null)
     * @param replaceValue   True if value of criteria lessThan should be replaced or throw if different
     * @return Filter with lessThan value set
     * @throws IllegalArgumentException if {@code value} is null
     * @throws IllegalArgumentException if criteria lessThan value is not null and is different from value passed and {@code replaceValue} is false
     */
    public static <T extends RangeFilter<U>, U extends Comparable<? super U>> T buildLessThanCriteriaOrThrow(final Class<T> criteriaClass, @Nullable final T criteriaPassed, @NotNull final U value, final boolean replaceValue) {
        if (value == null)
            throw new IllegalArgumentException("LessThan value cannot be null");

        final T criteria = criteriaPassed == null ? createCriteria(criteriaClass) : criteriaPassed;
        if (!replaceValue && criteria.getLessThan() != null && !criteria.getLessThan().equals(value))
            throw new IllegalArgumentException("LessThan filter value is not allowed");
        criteria.setLessThan(value);
        return criteria;
    }

    /**
     * Build a filter with the LessThan value with the one passed in param if not set already, leave other attribute untouched
     * <p>Does not replace lessThan value if set.</p>
     * <p>Throws if lessThan value is set and higher than the one passed.</p>
     *
     * @param criteriaClass  Class to instantiate if criteria is null
     * @param criteriaPassed Criteria to build the lessThan filtering (can be null)
     * @param value          Value to set (cannot be null)
     * @return Filter with lessThan value set
     * @throws IllegalArgumentException if {@code value} is null
     * @throws IllegalArgumentException if criteria lessThan value is not null and is higher than value passed
     */
    public static <T extends RangeFilter<U>, U extends Comparable<? super U>> T buildLessThanCriteriaOrThrowOrLess(final Class<T> criteriaClass, @Nullable final T criteriaPassed, @NotNull final U value) {
        if (value == null)
            throw new IllegalArgumentException("LessThan value cannot be null");

        final T criteria = criteriaPassed == null ? createCriteria(criteriaClass) : criteriaPassed;
        if (criteria.getLessThan() != null && criteria.getLessThan().compareTo(value) > 0)
            throw new IllegalArgumentException("LessThan filter value is not allowed");
        if (criteria.getLessThan() == null)
            criteria.setLessThan(value);
        return criteria;
    }

    public static LongFilter buildLessThanCriteria(@NotNull final Long value) {
        return buildLessThanCriteria(new LongFilter(), value);
    }

    public static ShortFilter buildLessThanCriteria(@NotNull final Short value) {
        return buildLessThanCriteria(new ShortFilter(), value);
    }

    public static IntegerFilter buildLessThanCriteria(@NotNull final Integer value) {
        return buildLessThanCriteria(new IntegerFilter(), value);
    }

    public static DoubleFilter buildLessThanCriteria(@NotNull final Double value) {
        return buildLessThanCriteria(new DoubleFilter(), value);
    }

    public static FloatFilter buildLessThanCriteria(@NotNull final Float value) {
        return buildLessThanCriteria(new FloatFilter(), value);
    }

    public static BigDecimalFilter buildLessThanCriteria(@NotNull final BigDecimal value) {
        return buildLessThanCriteria(new BigDecimalFilter(), value);
    }

    public static LocalDateFilter buildLessThanCriteria(@NotNull final LocalDate value) {
        return buildLessThanCriteria(new LocalDateFilter(), value);
    }

    public static InstantFilter buildLessThanCriteria(@NotNull final Instant value) {
        return buildLessThanCriteria(new InstantFilter(), value);
    }

    public static LongFilter buildLessThanCriteria(@Nullable final LongFilter criteriaPassed, @NotNull final Long value) {
        return buildLessThanCriteriaOrThrow(LongFilter.class, criteriaPassed, value, true);
    }

    public static ShortFilter buildLessThanCriteria(@Nullable final ShortFilter criteriaPassed, @NotNull final Short value) {
        return buildLessThanCriteriaOrThrow(ShortFilter.class, criteriaPassed, value, true);
    }

    public static IntegerFilter buildLessThanCriteria(@Nullable final IntegerFilter criteriaPassed, @NotNull final Integer value) {
        return buildLessThanCriteriaOrThrow(IntegerFilter.class, criteriaPassed, value, true);
    }

    public static DoubleFilter buildLessThanCriteria(@Nullable final DoubleFilter criteriaPassed, @NotNull final Double value) {
        return buildLessThanCriteriaOrThrow(DoubleFilter.class, criteriaPassed, value, true);
    }

    public static FloatFilter buildLessThanCriteria(@Nullable final FloatFilter criteriaPassed, @NotNull final Float value) {
        return buildLessThanCriteriaOrThrow(FloatFilter.class, criteriaPassed, value, true);
    }

    public static BigDecimalFilter buildLessThanCriteria(@Nullable final BigDecimalFilter criteriaPassed, @NotNull final BigDecimal value) {
        return buildLessThanCriteriaOrThrow(BigDecimalFilter.class, criteriaPassed, value, true);
    }

    public static LocalDateFilter buildLessThanCriteria(@Nullable final LocalDateFilter criteriaPassed, @NotNull final LocalDate value) {
        return buildLessThanCriteriaOrThrow(LocalDateFilter.class, criteriaPassed, value, true);
    }

    public static InstantFilter buildLessThanCriteria(@Nullable final InstantFilter criteriaPassed, @NotNull final Instant value) {
        return buildLessThanCriteriaOrThrow(InstantFilter.class, criteriaPassed, value, true);
    }

    public static LongFilter buildLessThanCriteriaOrThrow(@Nullable final LongFilter criteriaPassed, @NotNull final Long value) {
        return buildLessThanCriteriaOrThrow(LongFilter.class, criteriaPassed, value, false);
    }

    public static ShortFilter buildLessThanCriteriaOrThrow(@Nullable final ShortFilter criteriaPassed, @NotNull final Short value) {
        return buildLessThanCriteriaOrThrow(ShortFilter.class, criteriaPassed, value, false);
    }

    public static IntegerFilter buildLessThanCriteriaOrThrow(@Nullable final IntegerFilter criteriaPassed, @NotNull final Integer value) {
        return buildLessThanCriteriaOrThrow(IntegerFilter.class, criteriaPassed, value, false);
    }

    public static DoubleFilter buildLessThanCriteriaOrThrow(@Nullable final DoubleFilter criteriaPassed, @NotNull final Double value) {
        return buildLessThanCriteriaOrThrow(DoubleFilter.class, criteriaPassed, value, false);
    }

    public static FloatFilter buildLessThanCriteriaOrThrow(@Nullable final FloatFilter criteriaPassed, @NotNull final Float value) {
        return buildLessThanCriteriaOrThrow(FloatFilter.class, criteriaPassed, value, false);
    }

    public static BigDecimalFilter buildLessThanCriteriaOrThrow(@Nullable final BigDecimalFilter criteriaPassed, @NotNull final BigDecimal value) {
        return buildLessThanCriteriaOrThrow(BigDecimalFilter.class, criteriaPassed, value, false);
    }

    public static LocalDateFilter buildLessThanCriteriaOrThrow(@Nullable final LocalDateFilter criteriaPassed, @NotNull final LocalDate value) {
        return buildLessThanCriteriaOrThrow(LocalDateFilter.class, criteriaPassed, value, false);
    }

    public static InstantFilter buildLessThanCriteriaOrThrow(@Nullable final InstantFilter criteriaPassed, @NotNull final Instant value) {
        return buildLessThanCriteriaOrThrow(InstantFilter.class, criteriaPassed, value, false);
    }

    public static LongFilter buildLessThanCriteriaOrThrowOrLess(@Nullable final LongFilter criteriaPassed, @NotNull final Long value) {
        return buildLessThanCriteriaOrThrowOrLess(LongFilter.class, criteriaPassed, value);
    }

    public static ShortFilter buildLessThanCriteriaOrThrowOrLess(@Nullable final ShortFilter criteriaPassed, @NotNull final Short value) {
        return buildLessThanCriteriaOrThrowOrLess(ShortFilter.class, criteriaPassed, value);
    }

    public static IntegerFilter buildLessThanCriteriaOrThrowOrLess(@Nullable final IntegerFilter criteriaPassed, @NotNull final Integer value) {
        return buildLessThanCriteriaOrThrowOrLess(IntegerFilter.class, criteriaPassed, value);
    }

    public static DoubleFilter buildLessThanCriteriaOrThrowOrLess(@Nullable final DoubleFilter criteriaPassed, @NotNull final Double value) {
        return buildLessThanCriteriaOrThrowOrLess(DoubleFilter.class, criteriaPassed, value);
    }

    public static FloatFilter buildLessThanCriteriaOrThrowOrLess(@Nullable final FloatFilter criteriaPassed, @NotNull final Float value) {
        return buildLessThanCriteriaOrThrowOrLess(FloatFilter.class, criteriaPassed, value);
    }

    public static BigDecimalFilter buildLessThanCriteriaOrThrowOrLess(@Nullable final BigDecimalFilter criteriaPassed, @NotNull final BigDecimal value) {
        return buildLessThanCriteriaOrThrowOrLess(BigDecimalFilter.class, criteriaPassed, value);
    }

    public static LocalDateFilter buildLessThanCriteriaOrThrowOrLess(@Nullable final LocalDateFilter criteriaPassed, @NotNull final LocalDate value) {
        return buildLessThanCriteriaOrThrowOrLess(LocalDateFilter.class, criteriaPassed, value);
    }

    public static InstantFilter buildLessThanCriteriaOrThrowOrLess(@Nullable final InstantFilter criteriaPassed, @NotNull final Instant value) {
        return buildLessThanCriteriaOrThrowOrLess(InstantFilter.class, criteriaPassed, value);
    }


    /**
     * Build a filter with the LessThanOrEqual value with the one passed in param, leave other attribute untouched
     * <p>Throws if lessThanOrEqual value is set and different from the one passed</p>
     *
     * @param criteriaClass  Class to instantiate if criteria is null
     * @param criteriaPassed Criteria to build the lessThanOrEqual filtering (can be null)
     * @param value          Value to set (cannot be null)
     * @param replaceValue   True if value of criteria lessThanOrEqual should be replaced or throw if different
     * @return Filter with lessThanOrEqual value set
     * @throws IllegalArgumentException if {@code value} is null
     * @throws IllegalArgumentException if criteria lessThanOrEqual value is not null and is different from value passed and {@code replaceValue} is false
     */
    public static <T extends RangeFilter<U>, U extends Comparable<? super U>> T buildLessThanOrEqualCriteriaOrThrow(final Class<T> criteriaClass, @Nullable final T criteriaPassed, @NotNull final U value, final boolean replaceValue) {
        if (value == null)
            throw new IllegalArgumentException("LessThanOrEqual value cannot be null");

        final T criteria = criteriaPassed == null ? createCriteria(criteriaClass) : criteriaPassed;
        if (!replaceValue && criteria.getLessOrEqualThan() != null && !criteria.getLessOrEqualThan().equals(value))
            throw new IllegalArgumentException("LessThanOrEqual filter value is not allowed");
        criteria.setLessOrEqualThan(value);
        return criteria;
    }

    /**
     * Build a filter with the LessThanOrEqual value with the one passed in param if not set already, leave other attribute untouched
     * <p>Does not replace lessThanOrEqual value if set.</p>
     * <p>Throws if lessThanOrEqual value is set and higher than the one passed.</p>
     *
     * @param criteriaClass  Class to instantiate if criteria is null
     * @param criteriaPassed Criteria to build the lessThanOrEqual filtering (can be null)
     * @param value          Value to set (cannot be null)
     * @return Filter with lessThanOrEqual value set
     * @throws IllegalArgumentException if {@code value} is null
     * @throws IllegalArgumentException if criteria lessThanOrEqual value is not null and is higher than value passed
     */
    public static <T extends RangeFilter<U>, U extends Comparable<? super U>> T buildLessThanOrEqualCriteriaOrThrowOrLess(final Class<T> criteriaClass, @Nullable final T criteriaPassed, @NotNull final U value) {
        if (value == null)
            throw new IllegalArgumentException("LessThanOrEqual value cannot be null");

        final T criteria = criteriaPassed == null ? createCriteria(criteriaClass) : criteriaPassed;
        if (criteria.getLessOrEqualThan() != null && criteria.getLessOrEqualThan().compareTo(value) > 0)
            throw new IllegalArgumentException("LessThanOrEqual filter value is not allowed");
        if (criteria.getLessOrEqualThan() == null)
            criteria.setLessOrEqualThan(value);
        return criteria;
    }

    public static LongFilter buildLessThanOrEqualCriteria(@NotNull final Long value) {
        return buildLessThanOrEqualCriteria(new LongFilter(), value);
    }

    public static ShortFilter buildLessThanOrEqualCriteria(@NotNull final Short value) {
        return buildLessThanOrEqualCriteria(new ShortFilter(), value);
    }

    public static IntegerFilter buildLessThanOrEqualCriteria(@NotNull final Integer value) {
        return buildLessThanOrEqualCriteria(new IntegerFilter(), value);
    }

    public static DoubleFilter buildLessThanOrEqualCriteria(@NotNull final Double value) {
        return buildLessThanOrEqualCriteria(new DoubleFilter(), value);
    }

    public static FloatFilter buildLessThanOrEqualCriteria(@NotNull final Float value) {
        return buildLessThanOrEqualCriteria(new FloatFilter(), value);
    }

    public static BigDecimalFilter buildLessThanOrEqualCriteria(@NotNull final BigDecimal value) {
        return buildLessThanOrEqualCriteria(new BigDecimalFilter(), value);
    }

    public static LocalDateFilter buildLessThanOrEqualCriteria(@NotNull final LocalDate value) {
        return buildLessThanOrEqualCriteria(new LocalDateFilter(), value);
    }

    public static InstantFilter buildLessThanOrEqualCriteria(@NotNull final Instant value) {
        return buildLessThanOrEqualCriteria(new InstantFilter(), value);
    }

    public static LongFilter buildLessThanOrEqualCriteria(@Nullable final LongFilter criteriaPassed, @NotNull final Long value) {
        return buildLessThanOrEqualCriteriaOrThrow(LongFilter.class, criteriaPassed, value, true);
    }

    public static ShortFilter buildLessThanOrEqualCriteria(@Nullable final ShortFilter criteriaPassed, @NotNull final Short value) {
        return buildLessThanOrEqualCriteriaOrThrow(ShortFilter.class, criteriaPassed, value, true);
    }

    public static IntegerFilter buildLessThanOrEqualCriteria(@Nullable final IntegerFilter criteriaPassed, @NotNull final Integer value) {
        return buildLessThanOrEqualCriteriaOrThrow(IntegerFilter.class, criteriaPassed, value, true);
    }

    public static DoubleFilter buildLessThanOrEqualCriteria(@Nullable final DoubleFilter criteriaPassed, @NotNull final Double value) {
        return buildLessThanOrEqualCriteriaOrThrow(DoubleFilter.class, criteriaPassed, value, true);
    }

    public static FloatFilter buildLessThanOrEqualCriteria(@Nullable final FloatFilter criteriaPassed, @NotNull final Float value) {
        return buildLessThanOrEqualCriteriaOrThrow(FloatFilter.class, criteriaPassed, value, true);
    }

    public static BigDecimalFilter buildLessThanOrEqualCriteria(@Nullable final BigDecimalFilter criteriaPassed, @NotNull final BigDecimal value) {
        return buildLessThanOrEqualCriteriaOrThrow(BigDecimalFilter.class, criteriaPassed, value, true);
    }

    public static LocalDateFilter buildLessThanOrEqualCriteria(@Nullable final LocalDateFilter criteriaPassed, @NotNull final LocalDate value) {
        return buildLessThanOrEqualCriteriaOrThrow(LocalDateFilter.class, criteriaPassed, value, true);
    }

    public static InstantFilter buildLessThanOrEqualCriteria(@Nullable final InstantFilter criteriaPassed, @NotNull final Instant value) {
        return buildLessThanOrEqualCriteriaOrThrow(InstantFilter.class, criteriaPassed, value, true);
    }

    public static LongFilter buildLessThanOrEqualCriteriaOrThrow(@Nullable final LongFilter criteriaPassed, @NotNull final Long value) {
        return buildLessThanOrEqualCriteriaOrThrow(LongFilter.class, criteriaPassed, value, false);
    }

    public static ShortFilter buildLessThanOrEqualCriteriaOrThrow(@Nullable final ShortFilter criteriaPassed, @NotNull final Short value) {
        return buildLessThanOrEqualCriteriaOrThrow(ShortFilter.class, criteriaPassed, value, false);
    }

    public static IntegerFilter buildLessThanOrEqualCriteriaOrThrow(@Nullable final IntegerFilter criteriaPassed, @NotNull final Integer value) {
        return buildLessThanOrEqualCriteriaOrThrow(IntegerFilter.class, criteriaPassed, value, false);
    }

    public static DoubleFilter buildLessThanOrEqualCriteriaOrThrow(@Nullable final DoubleFilter criteriaPassed, @NotNull final Double value) {
        return buildLessThanOrEqualCriteriaOrThrow(DoubleFilter.class, criteriaPassed, value, false);
    }

    public static FloatFilter buildLessThanOrEqualCriteriaOrThrow(@Nullable final FloatFilter criteriaPassed, @NotNull final Float value) {
        return buildLessThanOrEqualCriteriaOrThrow(FloatFilter.class, criteriaPassed, value, false);
    }

    public static BigDecimalFilter buildLessThanOrEqualCriteriaOrThrow(@Nullable final BigDecimalFilter criteriaPassed, @NotNull final BigDecimal value) {
        return buildLessThanOrEqualCriteriaOrThrow(BigDecimalFilter.class, criteriaPassed, value, false);
    }

    public static LocalDateFilter buildLessThanOrEqualCriteriaOrThrow(@Nullable final LocalDateFilter criteriaPassed, @NotNull final LocalDate value) {
        return buildLessThanOrEqualCriteriaOrThrow(LocalDateFilter.class, criteriaPassed, value, false);
    }

    public static InstantFilter buildLessThanOrEqualCriteriaOrThrow(@Nullable final InstantFilter criteriaPassed, @NotNull final Instant value) {
        return buildLessThanOrEqualCriteriaOrThrow(InstantFilter.class, criteriaPassed, value, false);
    }

    public static LongFilter buildLessThanOrEqualCriteriaOrThrowOrLess(@Nullable final LongFilter criteriaPassed, @NotNull final Long value) {
        return buildLessThanOrEqualCriteriaOrThrowOrLess(LongFilter.class, criteriaPassed, value);
    }

    public static ShortFilter buildLessThanOrEqualCriteriaOrThrowOrLess(@Nullable final ShortFilter criteriaPassed, @NotNull final Short value) {
        return buildLessThanOrEqualCriteriaOrThrowOrLess(ShortFilter.class, criteriaPassed, value);
    }

    public static IntegerFilter buildLessThanOrEqualCriteriaOrThrowOrLess(@Nullable final IntegerFilter criteriaPassed, @NotNull final Integer value) {
        return buildLessThanOrEqualCriteriaOrThrowOrLess(IntegerFilter.class, criteriaPassed, value);
    }

    public static DoubleFilter buildLessThanOrEqualCriteriaOrThrowOrLess(@Nullable final DoubleFilter criteriaPassed, @NotNull final Double value) {
        return buildLessThanOrEqualCriteriaOrThrowOrLess(DoubleFilter.class, criteriaPassed, value);
    }

    public static FloatFilter buildLessThanOrEqualCriteriaOrThrowOrLess(@Nullable final FloatFilter criteriaPassed, @NotNull final Float value) {
        return buildLessThanOrEqualCriteriaOrThrowOrLess(FloatFilter.class, criteriaPassed, value);
    }

    public static BigDecimalFilter buildLessThanOrEqualCriteriaOrThrowOrLess(@Nullable final BigDecimalFilter criteriaPassed, @NotNull final BigDecimal value) {
        return buildLessThanOrEqualCriteriaOrThrowOrLess(BigDecimalFilter.class, criteriaPassed, value);
    }

    public static LocalDateFilter buildLessThanOrEqualCriteriaOrThrowOrLess(@Nullable final LocalDateFilter criteriaPassed, @NotNull final LocalDate value) {
        return buildLessThanOrEqualCriteriaOrThrowOrLess(LocalDateFilter.class, criteriaPassed, value);
    }

    public static InstantFilter buildLessThanOrEqualCriteriaOrThrowOrLess(@Nullable final InstantFilter criteriaPassed, @NotNull final Instant value) {
        return buildLessThanOrEqualCriteriaOrThrowOrLess(InstantFilter.class, criteriaPassed, value);
    }


}
