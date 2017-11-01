package io.github.jhipster.domain.util;

import org.junit.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JSR310DateConvertersTest {

    @Test
    public void testThatLongIsConvertedToZonedDateTimeProperly() {
        JSR310DateConverters.LongToZonedDateTimeConverter longToZonedDateTimeConverter = JSR310DateConverters.LongToZonedDateTimeConverter.INSTANCE;

        ZonedDateTime expectedResult = ZonedDateTime.now(ZoneOffset.UTC);

        ZonedDateTime result = longToZonedDateTimeConverter.convert(expectedResult.toInstant().toEpochMilli());

        assertThat(result, equalTo(expectedResult));
    }

    @Test
    public void testThatZonedDateTimeIsConvertedToLongProperly() {
        JSR310DateConverters.ZonedDateTimeToLongConverter zonedDateTimeToLongConverter = JSR310DateConverters.ZonedDateTimeToLongConverter.INSTANCE;

        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

        long expectedResult = now.toInstant().toEpochMilli();

        long result = zonedDateTimeToLongConverter.convert(now);

        assertThat(result, equalTo(expectedResult));
    }
}
