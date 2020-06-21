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

package io.github.jhipster.service.filter;

import java.util.Objects;

/**
 * Filter class for Comparable types, where less than / greater than / etc relations could be interpreted. It can be
 * added to a criteria class as a member, to support the following query parameters:
 * <pre>
 *      fieldName.equals=42
 *      fieldName.specified=true
 *      fieldName.specified=false
 *      fieldName.in=43,42
 *      fieldName.greaterThan=41
 *      fieldName.lessThan=44
 *      fieldName.greaterOrEqualThan=42
 *      fieldName.lessOrEqualThan=44
 * </pre>
 * Due to problems with the type conversions, the descendant classes should be used, where the generic type parameter
 * is materialized.
 *
 * @param <FIELD_TYPE> the type of filter.
 * @see IntegerFilter
 * @see DoubleFilter
 * @see FloatFilter
 * @see LongFilter
 * @see LocalDateFilter
 * @see InstantFilter
 * @see ShortFilter
 * @see ZonedDateTimeFilter
 */
public class RangeFilter<FIELD_TYPE extends Comparable<? super FIELD_TYPE>> extends Filter<FIELD_TYPE> {

    private static final long serialVersionUID = 1L;

    private FIELD_TYPE greaterThan;
    private FIELD_TYPE lessThan;
    private FIELD_TYPE greaterOrEqualThan;
    private FIELD_TYPE lessOrEqualThan;

    /**
     * <p>Constructor for RangeFilter.</p>
     */
    public RangeFilter() {
    }

    /**
     * <p>Constructor for RangeFilter.</p>
     *
     * @param filter a {@link io.github.jhipster.service.filter.RangeFilter} object.
     */
    public RangeFilter(final RangeFilter<FIELD_TYPE> filter) {
        super(filter);
        this.greaterThan = filter.greaterThan;
        this.lessThan = filter.lessThan;
        this.greaterOrEqualThan = filter.greaterOrEqualThan;
        this.lessOrEqualThan = filter.lessOrEqualThan;
    }

    /**
     * <p>copy.</p>
     *
     * @return a {@link io.github.jhipster.service.filter.RangeFilter} object.
     */
    public RangeFilter<FIELD_TYPE> copy() {
        return new RangeFilter<>(this);
    }

    /**
     * <p>Getter for the field <code>greaterThan</code>.</p>
     *
     * @return a FIELD_TYPE object.
     */
    public FIELD_TYPE getGreaterThan() {
        return greaterThan;
    }

    /**
     * <p>Setter for the field <code>greaterThan</code>.</p>
     *
     * @param greaterThan a FIELD_TYPE object.
     * @return a {@link io.github.jhipster.service.filter.RangeFilter} object.
     */
    public RangeFilter<FIELD_TYPE> setGreaterThan(FIELD_TYPE greaterThan) {
        this.greaterThan = greaterThan;
        return this;
    }

    /**
     * <p>Getter for the field <code>greaterOrEqualThan</code>.</p>
     *
     * @return a FIELD_TYPE object.
     */
    public FIELD_TYPE getGreaterOrEqualThan() {
        return greaterOrEqualThan;
    }

    /**
     * <p>Setter for the field <code>greaterOrEqualThan</code>.</p>
     *
     * @param greaterOrEqualThan a FIELD_TYPE object.
     * @return a {@link io.github.jhipster.service.filter.RangeFilter} object.
     */
    public RangeFilter<FIELD_TYPE> setGreaterOrEqualThan(FIELD_TYPE greaterOrEqualThan) {
        this.greaterOrEqualThan = greaterOrEqualThan;
        return this;
    }

    /**
     * <p>Getter for the field <code>lessThan</code>.</p>
     *
     * @return a FIELD_TYPE object.
     */
    public FIELD_TYPE getLessThan() {
        return lessThan;
    }

    /**
     * <p>Setter for the field <code>lessThan</code>.</p>
     *
     * @param lessThan a FIELD_TYPE object.
     * @return a {@link io.github.jhipster.service.filter.RangeFilter} object.
     */
    public RangeFilter<FIELD_TYPE> setLessThan(FIELD_TYPE lessThan) {
        this.lessThan = lessThan;
        return this;
    }

    /**
     * <p>Getter for the field <code>lessOrEqualThan</code>.</p>
     *
     * @return a FIELD_TYPE object.
     */
    public FIELD_TYPE getLessOrEqualThan() {
        return lessOrEqualThan;
    }

    /**
     * <p>Setter for the field <code>lessOrEqualThan</code>.</p>
     *
     * @param lessOrEqualThan a FIELD_TYPE object.
     * @return a {@link io.github.jhipster.service.filter.RangeFilter} object.
     */
    public RangeFilter<FIELD_TYPE> setLessOrEqualThan(FIELD_TYPE lessOrEqualThan) {
        this.lessOrEqualThan = lessOrEqualThan;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final RangeFilter<?> that = (RangeFilter<?>) o;
        return Objects.equals(greaterThan, that.greaterThan) &&
            Objects.equals(lessThan, that.lessThan) &&
            Objects.equals(greaterOrEqualThan, that.greaterOrEqualThan) &&
            Objects.equals(lessOrEqualThan, that.lessOrEqualThan);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), greaterThan, lessThan, greaterOrEqualThan, lessOrEqualThan);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getFilterName() + " ["
            + (getGreaterThan() != null ? "greaterThan=" + getGreaterThan() + ", " : "")
            + (getGreaterOrEqualThan() != null ? "greaterOrEqualThan=" + getGreaterOrEqualThan() + ", " : "")
            + (getLessThan() != null ? "lessThan=" + getLessThan() + ", " : "")
            + (getLessOrEqualThan() != null ? "lessOrEqualThan=" + getLessOrEqualThan() + ", " : "")
            + (getEquals() != null ? "equals=" + getEquals() + ", " : "")
            + (getSpecified() != null ? "specified=" + getSpecified() + ", " : "")
            + (getIn() != null ? "in=" + getIn() : "")
            + "]";
    }

}
