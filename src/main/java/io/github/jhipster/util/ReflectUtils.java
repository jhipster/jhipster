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

package io.github.jhipster.util;

import java.lang.reflect.InvocationTargetException;

/**
 * This utility is intended to be used with optional dependencies that are only used in certain
 * Spring profiles, such as with Liquibase and Swagger, and potentially stripped entirely from
 * the classpath. Invoking methods via reflection avoids compile-time errors in those cases.
 */
public interface ReflectUtils {

    public static final String FAILED_CLASS_LOAD_MESSAGE = "Failed to load and initialize";
    public static final String FAILED_GET_METHOD_MESSAGE = "Failed to get method";
    public static final String FAILED_INVOKE_MESSAGE = "Failed to invoke method";
    public static final String TARGET_EXCEPTION_MESSAGE = "Checked exception from method";

    /**
     * Construct a new instance of the class given by name.
     *
     * @param className Name of the class to instantiate
     * @param args Arguments to pass to the constructor
     * @return The new instance
     * @throws RuntimeException Wraps anything that might have gone wrong
     */
    public static Object construct(String className, Object... args) {
        return invoke(null, load(className), null, args);
    }

    /**
     * Invoke an instance method on the given object.
     *
     * @param instance The instance
     * @param methodName The name of the method to invoke
     * @param args Arguments to pass to the method
     * @return The return value of the method, if any
     * @throws RuntimeException Wraps anything that might have gone wrong
     */
    public static Object invokeInstance(Object instance, String methodName, Object... args) {
        return invoke(instance, instance.getClass(), methodName, args);
    }

    /**
     * Invoke a static method on the class given by name.
     *
     * @param className Name of the class to invoke a method on
     * @param methodName The name of the method to invoke
     * @param args Arguments to pass to the method
     * @return The return value of the method, if any.
     * @throws RuntimeException Wraps anything that might have gone wrong
     */
    public static Object invokeStatic(String className, String methodName, Object... args) {
        return invoke(null, load(className), methodName, args);
    }

    /**
     * Load and initialize a class by name, using the current thread's context loader.
     *
     * @param className Name of the class to load
     * @return The loaded and initialized class
     * @throws RuntimeException Wraps anything that might have gone wrong
     */
    static Class<?> load(String className) {
        try {
            return Class.forName(className, true, Thread.currentThread().getContextClassLoader());

        } catch (ClassNotFoundException | LinkageError  e) {
            throw new RuntimeException(FAILED_CLASS_LOAD_MESSAGE + " " + className, e);
        }
    }

    /**
     * The actual logic to do the reflection and invoke the method, called by the public methods.
     *
     * @param instance The instance, or null for static access
     * @param clazz The loaded and initialized class
     * @param methodName The name of the method to invoke, or null for constructor
     * @param args The arguments to pass to the method / constructor
     * @return The method return value or newly constructed instance
     * @throws RuntimeException Wraps anything that might have gone wrong
     */
    static Object invoke(Object instance, Class<?> clazz, String methodName, Object... args) {
        if (methodName == null) {
            methodName = clazz.getSimpleName();
        }
        final String className = clazz.getName();
        final String targetName = className + "." + methodName;
        try {
            final Class<?>[] types = new Class<?>[args.length];
            for (int i = 0; i < types.length; i++) {
                types[i] = args[i].getClass();
                try {
                    // Unbox primitive types
                    types[i] = (Class<?>) types[i].getDeclaredField("TYPE").get(null);
                } catch (NoSuchFieldException e) {
                    // No problem
                }
            }

            if (methodName.equals(clazz.getSimpleName())) {
                return clazz.getConstructor(types).newInstance(args);
            } else {
                return clazz.getMethod(methodName, types).invoke(instance, args);
            }

        } catch (SecurityException | NoSuchMethodException e) {
            throw new RuntimeException(FAILED_GET_METHOD_MESSAGE + " " + targetName, e);

        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException e) {
            throw new RuntimeException(FAILED_INVOKE_MESSAGE + " " + targetName, e);

        } catch (InvocationTargetException e) {
            throw new RuntimeException(TARGET_EXCEPTION_MESSAGE + " " + targetName, e.getCause());
        }
    }
}
