/*
 * Copyright 2016-2018 the original author or authors from the JHipster project.
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

package io.github.jhipster.config.jcache;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Properties;
import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.spi.CachingProvider;

import org.hibernate.cache.jcache.internal.JCacheRegionFactory;

/**
 * Fixes Spring classloader issues that were introduced in Spring Boot 2.0.3.
 *
 * This allows to use the same classloader for ehcache, both for the Spring Cache abstraction and for the Hibernate
 * 2nd level cache.
 *
 * See https://github.com/jhipster/generator-jhipster/issues/7783 for more information.
 */
public class BeanClassLoaderAwareJCacheRegionFactory extends JCacheRegionFactory {

    public static final String EXCEPTION_MESSAGE = "Please set Spring's classloader in the setBeanClassLoader method before using this class in Hibernate";
    
    private static volatile ClassLoader classLoader;

    /**
     * This method must be called from a Spring Bean to get the classloader.
     * For example: BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
     *
     * @param classLoader The Spring classloader
     */
    public static void setBeanClassLoader(ClassLoader classLoader) {
        BeanClassLoaderAwareJCacheRegionFactory.classLoader = classLoader;
    }

    @Override
    protected ClassLoader getClassLoader(CachingProvider cachingProvider) {
        return Objects.requireNonNull(classLoader, EXCEPTION_MESSAGE);
    }
    
}
