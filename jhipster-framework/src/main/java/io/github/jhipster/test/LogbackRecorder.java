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

package io.github.jhipster.test;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.AppenderBase;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Utility, mainly for unit tests, to assert content written to logback. A classical usage would be
 * the following.
 * <p>
 * {@code
 * LogbackRecorder recorder = LogbackRecorder.forClass(TestedClass.class)
 *   .reset()
 *   .capture(Level.WARN.name());
 * // do something that logs
 * List<LogbackRecorder.Event> events = recorder.release().play();
 * // perform assertions on the events
 * }
 */
@ConditionalOnClass({LoggerContext.class})
public class LogbackRecorder {

    /** Constant <code>DEFAULT_MUTE=true</code> */
    public static final boolean DEFAULT_MUTE = true;
    /** Constant <code>DEFAULT_LEVEL="ALL"</code> */
    public static final String DEFAULT_LEVEL = "ALL";

    /** Constant <code>LOGBACK_EXCEPTION_MESSAGE="Expected logback"</code> */
    public static final String LOGBACK_EXCEPTION_MESSAGE = "Expected logback";
    /** Constant <code>CAPTURE_EXCEPTION_MESSAGE="Already capturing"</code> */
    public static final String CAPTURE_EXCEPTION_MESSAGE = "Already capturing";
    /** Constant <code>RELEASE_EXCEPTION_MESSAGE="Not currently capturing"</code> */
    public static final String RELEASE_EXCEPTION_MESSAGE = "Not currently capturing";

    private static final LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
    private static final Object lock = context.getConfigurationLock();

    private static final Map<Logger, LogbackRecorder> instances = new WeakHashMap<>(32, 0.75F);

    /**
     * Create a recorder for a logback logger identified by the class name. Instances of a recorder are cached per logger.
     * Make sure to reset it before starting capture.
     *
     * @param clazz class whose logger as its name
     * @return the recorder for this class
     */
    public static final LogbackRecorder forClass(Class<?> clazz) {
        return forLogger(context.getLogger(clazz));
    }

    /**
     * Create a recorder for a logback logger identified by its name. Instances of a recorder are cached per logger.
     * Make sure to reset it before starting capture.
     *
     * @param name the name of the logger
     * @return the recorder for this class
     */
    public static final LogbackRecorder forName(String name) {
        return forLogger(context.getLogger(name));
    }

    /**
     * Create a recorder for a logback logger. Instances of a recorder are cached per logger.
     * Make sure to reset it before starting capture.
     *
     * @param logger the logger to record
     * @return the recorder for this logger
     */
    public static final LogbackRecorder forLogger(org.slf4j.Logger logger) {
        synchronized (instances) {
            if (!(logger instanceof Logger)) {
                throw new IllegalArgumentException(LOGBACK_EXCEPTION_MESSAGE);
            }
            LogbackRecorder recorder = instances.get(logger);
            if (recorder == null) {
                recorder = new LogbackRecorder((Logger) logger);
                instances.put(recorder.logger, recorder);
            }
            return recorder;
        }
    }

    private final Logger logger;
    private final List<Event> events;
    private final AppenderBase<ILoggingEvent> appender;
    private boolean active;
    private boolean additive;
    private Level level;

    private LogbackRecorder(Logger logger) {
        this.logger = logger;
        this.events = new ArrayList<>();
        this.appender = new AppenderBase<ILoggingEvent>() {
            @Override
            protected synchronized void append(ILoggingEvent event) {
                events.add(new Event(event));
            }
        };
    }

    /**
     * Resets the logger by clearing everything that was recorded so far.
     *
     * @return this
     */
    public synchronized LogbackRecorder reset() {
        this.events.clear();
        return this;
    }

    /**
     * Start capturing whatever is logged for this level of worse.
     *
     * @param level the level at which to start capturing
     * @return this
     */
    public LogbackRecorder capture(String level) {
        synchronized (lock) {
            if (this.active) {
                throw new IllegalStateException(CAPTURE_EXCEPTION_MESSAGE);
            }
            this.active = true;
            this.additive = logger.isAdditive();
            this.logger.setAdditive(false);
            this.level = logger.getLevel();
            this.logger.setLevel(Level.valueOf(level.toUpperCase()));
            this.logger.addAppender(this.appender);
            this.appender.start();
        }
        return this;
    }

    /**
     * Stop recording and detach from the logger.
     *
     * @return this
     */
    public synchronized LogbackRecorder release() {
        synchronized (lock) {
            if (!this.active) {
                throw new IllegalStateException(RELEASE_EXCEPTION_MESSAGE);
            }
            this.appender.stop();
            this.logger.detachAppender(this.appender);
            this.logger.setLevel(this.level);
            this.logger.setAdditive(this.additive);
        }
        this.active = false;
        return this;
    }

    /**
     * Return all recorded events.
     *
     * @return all recorded events so far
     */
    public List<Event> play() {
        return new ArrayList<>(this.events);
    }

    /**
     * A recorded event. It contains all information sent to the logger.
     */
    public static final class Event {

        private final Marker marker;
        private final String level;
        private final String message;
        private final Object[] arguments;
        private final String thrown;

        Event(ILoggingEvent event) {
            this.marker = event.getMarker();
            this.level = event.getLevel().toString();
            this.message = event.getMessage();
            this.arguments = event.getArgumentArray();
            final IThrowableProxy proxy = event.getThrowableProxy();
            this.thrown = proxy == null ? null : proxy.getClassName() + ": " + proxy.getMessage();
        }

        /**
         * Slf4j market used.
         *
         * @return the marker
         */
        public Marker getMarker() {
            return this.marker;
        }

        /**
         * Level of the log.
         *
         * @return the level
         */
        public String getLevel() {
            return this.level;
        }

        /**
         * Message passed to the logger with the original placeholders ('{}').
         *
         * @return the logged message
         */
        public String getMessage() {
            return this.message;
        }

        /**
         * The arguments passed to the logger to be used by a placeholder. Logged exceptions are not included.
         *
         * @return the parameters passed to the logger
         */
        public Object[] getArguments() {
            return this.arguments;
        }

        /**
         * Logged exception passed in argument to the logger or null if none.
         *
         * @return the logged exception as {@code exception.getClass().getName() + ": " + exception.getMessage()}
         */
        public String getThrown() {
            return this.thrown;
        }
    }
}
