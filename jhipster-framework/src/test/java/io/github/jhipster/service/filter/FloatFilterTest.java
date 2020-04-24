/*
 * Copyright 2016-2020 the original author or authors from the JHipster project.
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

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FloatFilterTest {

    private FloatFilter filter;

    private Float value = 42F;

    @BeforeEach
    public void setup() {
        filter = new FloatFilter();
    }

    @Test
    public void testConstructor() {
        assertThat(filter.getEquals()).isNull();
        assertThat(filter.getNotEquals()).isNull();
        assertThat(filter.getSpecified()).isNull();
        assertThat(filter.getIn()).isNull();
        assertThat(filter.getNotIn()).isNull();
        assertThat(filter.getGreaterThan()).isNull();
        assertThat(filter.getLessThan()).isNull();
        assertThat(filter.getGreaterThanOrEqual()).isNull();
        assertThat(filter.getLessThanOrEqual()).isNull();
        assertThat(filter.toString()).isEqualTo("FloatFilter []");
    }

    @Test
    public void testCopy() {
        final FloatFilter copy = filter.copy();
        assertThat(copy).isNotSameAs(filter);
        assertThat(copy.getEquals()).isNull();
        assertThat(copy.getNotEquals()).isNull();
        assertThat(copy.getSpecified()).isNull();
        assertThat(copy.getIn()).isNull();
        assertThat(copy.getNotIn()).isNull();
        assertThat(copy.getGreaterThan()).isNull();
        assertThat(copy.getLessThan()).isNull();
        assertThat(copy.getGreaterThanOrEqual()).isNull();
        assertThat(copy.getLessThanOrEqual()).isNull();
        assertThat(copy.toString()).isEqualTo("FloatFilter []");
    }

    @Test
    public void testSetEquals() {
        Filter<Float> chain = filter.setEquals(value);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getEquals()).isEqualTo(value);
    }

    @Test
    public void testSetNotEquals() {
        Filter<Float> chain = filter.setNotEquals(value);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getNotEquals()).isEqualTo(value);
    }

    @Test
    public void testSetSpecified() {
        Filter<Float> chain = filter.setSpecified(true);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getSpecified()).isEqualTo(true);
    }

    @Test
    public void testSetIn() {
        List<Float> list = new LinkedList<>();
        Filter<Float> chain = filter.setIn(list);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getIn()).isEqualTo(list);
    }

    @Test
    public void testSetNotIn() {
        List<Float> list = new LinkedList<>();
        Filter<Float> chain = filter.setNotIn(list);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getNotIn()).isEqualTo(list);
    }

    @Test
    public void testSetGreaterThan() {
        Filter<Float> chain = filter.setGreaterThan(value);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getGreaterThan()).isEqualTo(value);
    }

    @Test
    public void testSetLessThan() {
        Filter<Float> chain = filter.setLessThan(value);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getLessThan()).isEqualTo(value);
    }

    @Test
    public void testSetGreaterThanOrEqual() {
        Filter<Float> chain = filter.setGreaterThanOrEqual(value);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getGreaterThanOrEqual()).isEqualTo(value);
    }

    @Test
    public void testSetLessThanOrEqual() {
        Filter<Float> chain = filter.setLessThanOrEqual(value);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getLessThanOrEqual()).isEqualTo(value);
    }

    @Test
    public void testEquals() {
        final FloatFilter filter2 = new FloatFilter();
        assertThat(filter).isEqualTo(filter2);
        filter.setEquals(value);
        filter2.setEquals(value);
        assertThat(filter).isEqualTo(filter2);
        filter.setNotEquals(value);
        filter2.setNotEquals(value);
        assertThat(filter).isEqualTo(filter2);
        filter.setSpecified(false);
        assertThat(filter2).isNotEqualTo(filter);
        filter2.setSpecified(false);
        assertThat(filter).isEqualTo(filter2);
        filter.setIn(Lists.newArrayList(value, value));
        assertThat(filter2).isNotEqualTo(filter);
        filter2.setIn(Lists.newArrayList(value, value));
        assertThat(filter).isEqualTo(filter2);
        filter.setNotIn(Lists.newArrayList(value, value));
        assertThat(filter2).isNotEqualTo(filter);
        filter2.setNotIn(Lists.newArrayList(value, value));
        assertThat(filter).isEqualTo(filter2);
        filter.setGreaterThan(value);
        assertThat(filter).isNotEqualTo(filter2);
        filter2.setGreaterThan(value);
        assertThat(filter).isEqualTo(filter2);
        filter.setLessThan(value);
        assertThat(filter).isNotEqualTo(filter2);
        filter2.setLessThan(value);
        assertThat(filter).isEqualTo(filter2);
        filter.setGreaterThanOrEqual(value);
        assertThat(filter).isNotEqualTo(filter2);
        filter2.setGreaterThanOrEqual(value);
        assertThat(filter).isEqualTo(filter2);
        filter.setLessThanOrEqual(value);
        assertThat(filter).isNotEqualTo(filter2);
        filter2.setLessThanOrEqual(value);
        assertThat(filter).isEqualTo(filter2);
        final FloatFilter filter3 = new FloatFilter();
        filter3.setEquals(value);
        assertThat(filter3).isNotEqualTo(filter);
        assertThat(filter3).isNotEqualTo(filter2);
    }

    @Test
    public void testHashCode() {
        final FloatFilter filter2 = new FloatFilter();
        assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
        filter.setEquals(value);
        filter2.setEquals(value);
        assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
        filter.setNotEquals(value);
        filter2.setNotEquals(value);
        assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
        filter.setSpecified(false);
        filter2.setSpecified(false);
        assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
        filter.setIn(Lists.newArrayList(value, value));
        filter2.setIn(Lists.newArrayList(value, value));
        assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
        filter.setNotIn(Lists.newArrayList(value, value));
        filter2.setNotIn(Lists.newArrayList(value, value));
        assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
        filter.setGreaterThan(value);
        filter2.setGreaterThan(value);
        assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
        filter.setLessThan(value);
        filter2.setLessThan(value);
        assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
        filter.setGreaterThanOrEqual(value);
        filter2.setGreaterThanOrEqual(value);
        assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
        filter.setLessThanOrEqual(value);
        filter2.setLessThanOrEqual(value);
        assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
    }

    @Test
    public void testToString() {
        filter.setEquals(value);
        filter.setNotEquals(value);
        filter.setSpecified(true);
        filter.setIn(new LinkedList<>());
        filter.setNotIn(new LinkedList<>());
        filter.setGreaterThan(value);
        filter.setLessThan(value);
        filter.setGreaterThanOrEqual(value);
        filter.setLessThanOrEqual(value);
        String str = value.toString();
        assertThat(filter.toString()).isEqualTo("FloatFilter [equals=" + str + ", notEquals=" + str + ", specified=true, in=[], notIn=[], greaterThan=" + str + ", lessThan=" + str + ", greaterThanOrEqual=" + str + ", lessThanOrEqual=" + str + "]");
    }
}
