/*
 * Copyright 2016-2017 the original author or authors from the JHipster project.
 *
 * This file is part of the JHipster project, see http://www.jhipster.tech/
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

package io.github.jhipster.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;

import javax.annotation.Nullable;

public class NullObjectProvider<T> implements ObjectProvider<T> {

    @Nullable
    @Override
    public T getObject(Object... args) throws BeansException {
        return null;
    }

    @Nullable
    @Override
    public T getIfAvailable() throws BeansException {
        return null;
    }

    @Nullable
    @Override
    public T getIfUnique() throws BeansException {
        return null;
    }

    @Nullable
    @Override
    public T getObject() throws BeansException {
        return null;
    }
}
