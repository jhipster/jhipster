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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UUIDFilterTest {

    private UUIDFilter filter;

    private UUID value = UUID.fromString("dbc36987-d354-4ddf-9b53-38ca19b5a409");

    @BeforeEach
    public void setup() {
        filter = new UUIDFilter();
    }

    @Test
    public void testConstructor() {
        assertThat(filter.getEquals()).isNull();
        assertThat(filter.getSpecified()).isNull();
        assertThat(filter.getIn()).isNull();
        assertThat(filter.getNotIn()).isNull();
        assertThat(filter.toString()).isEqualTo("UUIDFilter []");
    }

    @Test
    public void testSetEquals() {
        Filter<UUID> chain = filter.setEquals(value);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getEquals()).isEqualTo(value);
    }

    @Test
    public void testSetSpecified() {
        Filter<UUID> chain = filter.setSpecified(true);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getSpecified()).isEqualTo(true);
    }

    @Test
    public void testSetIn() {
        List<UUID> list = new LinkedList<>();
        Filter<UUID> chain = filter.setIn(list);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getIn()).isEqualTo(list);
    }

    @Test
    public void testSetNotIn() {
        List<UUID> list = new LinkedList<>();
        Filter<UUID> chain = filter.setNotIn(list);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getNotIn()).isEqualTo(list);
    }

    @Test
    public void testToString() {
        filter.setEquals(value);
        filter.setSpecified(true);
        filter.setIn(new LinkedList<>());
        filter.setNotIn(new LinkedList<>());
        assertThat(filter.toString()).isEqualTo("UUIDFilter [equals=dbc36987-d354-4ddf-9b53-38ca19b5a409, in=[], notIn=[], specified=true]");
    }
}
