/*
 * Copyright 2016-2019 the original author or authors from the JHipster project.
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

package io.github.jhipster.security;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class PersistentTokenCacheTest {

    @Test
    public void testConstructorThrows() {
        Throwable caught = catchThrowable(() -> new PersistentTokenCache<String>(-1l));
        assertThat(caught).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testAbsent() {
        PersistentTokenCache<String> cache = new PersistentTokenCache<>(100l);
        assertThat(cache.get("key")).isNull();
    }

    @Test
    public void testAccess() {
        PersistentTokenCache<String> cache = new PersistentTokenCache<>(100l);
        cache.put("key", "val");
        assertThat(cache.size()).isEqualTo(1);
        assertThat(cache.get("key")).isEqualTo("val");
    }

    @Test
    public void testReplace() {
        PersistentTokenCache<String> cache = new PersistentTokenCache<>(100l);
        cache.put("key", "val");
        cache.put("key", "foo");
        assertThat(cache.get("key")).isEqualTo("foo");
    }

    @Test
    public void testExpires() {
        PersistentTokenCache<String> cache = new PersistentTokenCache<>(1l);
        cache.put("key", "val");
        try {
            Thread.sleep(100l);
        } catch (InterruptedException x) {
            // This should not happen
            throw new Error(x);
        }
        assertThat(cache.get("key")).isNull();
    }

    @Test
    public void testPurge() {
        PersistentTokenCache<String> cache = new PersistentTokenCache<>(1l);
        cache.put("key", "val");
        try {
            Thread.sleep(100l);
        } catch (InterruptedException x) {
            // This should not happen
            throw new Error(x);
        }
        assertThat(cache.size()).isEqualTo(1);
        cache.purge();
        assertThat(cache.size()).isEqualTo(0);
    }

}
