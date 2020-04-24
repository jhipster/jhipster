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

public class StringFilterTest {

    private StringFilter filter;

    private String value = "foo";

    @BeforeEach
    public void setup() {
        filter = new StringFilter();
    }

    @Test
    public void testConstructor() {
        assertThat(filter.getEquals()).isNull();
        assertThat(filter.getNotEquals()).isNull();
        assertThat(filter.getSpecified()).isNull();
        assertThat(filter.getIn()).isNull();
        assertThat(filter.getNotIn()).isNull();
        assertThat(filter.getContains()).isNull();
        assertThat(filter.getDoesNotContain()).isNull();
        assertThat(filter.toString()).isEqualTo("StringFilter []");
    }

    @Test
    public void testCopy() {
        final StringFilter copy = filter.copy();
        assertThat(copy).isNotSameAs(filter);
        assertThat(copy.getEquals()).isNull();
        assertThat(copy.getNotEquals()).isNull();
        assertThat(copy.getSpecified()).isNull();
        assertThat(copy.getIn()).isNull();
        assertThat(copy.getNotIn()).isNull();
        assertThat(copy.getContains()).isNull();
        assertThat(copy.getDoesNotContain()).isNull();
        assertThat(copy.toString()).isEqualTo("StringFilter []");
    }

    @Test
    public void testSetEquals() {
        Filter<String> chain = filter.setEquals(value);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getEquals()).isEqualTo(value);
    }

    @Test
    public void testSetNotEquals() {
        Filter<String> chain = filter.setNotEquals(value);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getNotEquals()).isEqualTo(value);
    }

    @Test
    public void testSetSpecified() {
        Filter<String> chain = filter.setSpecified(true);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getSpecified()).isEqualTo(true);
    }

    @Test
    public void testSetIn() {
        List<String> list = new LinkedList<>();
        Filter<String> chain = filter.setIn(list);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getIn()).isEqualTo(list);
    }

    @Test
    public void testSetNotIn() {
        List<String> list = new LinkedList<>();
        Filter<String> chain = filter.setNotIn(list);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getNotIn()).isEqualTo(list);
    }

    @Test
    public void testSetContains() {
        Filter<String> chain = filter.setContains(value);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getContains()).isEqualTo(value);
    }

    @Test
    public void testSetDoesNotContain() {
        Filter<String> chain = filter.setDoesNotContain(value);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getDoesNotContain()).isEqualTo(value);
    }

    @Test
    public void testEquals() {
        final StringFilter filter2 = new StringFilter();
        assertThat(filter).isEqualTo(filter2);
        filter.setEquals(value);
        assertThat(filter2).isNotEqualTo(filter);
        filter2.setEquals(value);
        assertThat(filter).isEqualTo(filter2);
        filter.setNotEquals(value);
        assertThat(filter2).isNotEqualTo(filter);
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
        filter.setContains(value);
        assertThat(filter2).isNotEqualTo(filter);
        filter2.setContains(value);
        assertThat(filter).isEqualTo(filter2);
        filter.setDoesNotContain(value);
        assertThat(filter2).isNotEqualTo(filter);
        filter2.setDoesNotContain(value);
        assertThat(filter).isEqualTo(filter2);
        final StringFilter filter3 = new StringFilter();
        filter3.setEquals(value);
        assertThat(filter3).isNotEqualTo(filter);
        assertThat(filter3).isNotEqualTo(filter2);
    }

    @Test
    public void testHashCode() {
        final StringFilter filter2 = new StringFilter();
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
        filter.setContains(value);
        filter2.setContains(value);
        assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
        filter.setDoesNotContain(value);
        filter2.setDoesNotContain(value);
        assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
        final StringFilter filter3 = new StringFilter();
        filter3.setEquals(value);
        assertThat(filter3.hashCode()).isNotEqualTo(filter.hashCode());
        assertThat(filter3.hashCode()).isNotEqualTo(filter2.hashCode());
    }

    @Test
    public void testToString() {
        filter.setEquals(value);
        filter.setNotEquals(value);
        filter.setSpecified(true);
        filter.setIn(new LinkedList<>());
        filter.setNotIn(new LinkedList<>());
        filter.setContains(value);
        filter.setDoesNotContain(value);
        String str = value;
        assertThat(filter.toString()).isEqualTo("StringFilter [equals=" + str + ", notEquals=" + str + ", specified=true, in=[], notIn=[], contains=" + str + ", doesNotContain=" + str + "]");
    }
}
