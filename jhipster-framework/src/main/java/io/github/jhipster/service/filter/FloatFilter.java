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

/**
 * Filter class for {@link java.lang.Float} type attributes.
 *
 * @see RangeFilter
 */
public class FloatFilter extends RangeFilter<Float> {

    private static final long serialVersionUID = 1L;

    /**
     * <p>Constructor for FloatFilter.</p>
     */
    public FloatFilter() {
    }

    /**
     * <p>Constructor for FloatFilter.</p>
     *
     * @param filter a {@link io.github.jhipster.service.filter.FloatFilter} object.
     */
    public FloatFilter(final FloatFilter filter) {
        super(filter);
    }

    /**
     * <p>copy.</p>
     *
     * @return a {@link io.github.jhipster.service.filter.FloatFilter} object.
     */
    public FloatFilter copy() {
        return new FloatFilter(this);
    }

}
