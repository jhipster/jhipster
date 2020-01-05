package io.github.jhipster.config.cache;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class PrefixedKeyGenerator implements KeyGenerator {

    private final String prefix;

    public PrefixedKeyGenerator(GitProperties gitProperties, BuildProperties buildProperties) {

        String shortCommitId = gitProperties.getShortCommitId();
        Instant time = buildProperties.getTime();
        String version = buildProperties.getVersion();
        Object prefixObject = ObjectUtils.firstNonNull(shortCommitId, time, version, RandomStringUtils.randomAlphanumeric(12));

        if (prefixObject instanceof  Instant) {
            this.prefix = DateTimeFormatter.ISO_INSTANT.format((Instant) prefixObject);
        } else {
            this.prefix = prefixObject.toString();
        }
    }


    @Override
    public Object generate(Object target, Method method, Object... params) {
        return new PrefixedSimpleKey(prefix, method.getName(), params);
    }
}
