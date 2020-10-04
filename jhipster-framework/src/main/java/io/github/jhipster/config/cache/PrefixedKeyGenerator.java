package io.github.jhipster.config.cache;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * <p>PrefixedKeyGenerator class.</p>
 */
public class PrefixedKeyGenerator implements KeyGenerator {

    private final String prefix;

    /**
     * <p>Constructor for PrefixedKeyGenerator.</p>
     *
     * @param gitProperties a {@link org.springframework.boot.info.GitProperties} object.
     * @param buildProperties a {@link org.springframework.boot.info.BuildProperties} object.
     */
    public PrefixedKeyGenerator(GitProperties gitProperties, BuildProperties buildProperties) {

        this.prefix = generatePrefix(gitProperties, buildProperties);
    }

    String getPrefix() {
        return this.prefix;
    }

    private String generatePrefix(GitProperties gitProperties, BuildProperties buildProperties) {

        String shortCommitId = null;
        if (Objects.nonNull(gitProperties)) {
            shortCommitId = gitProperties.getShortCommitId();
        }

        Instant time = null;
        String version = null;
        if (Objects.nonNull(buildProperties)) {
            time = buildProperties.getTime();
            version = buildProperties.getVersion();
        }
        Object p = ObjectUtils.firstNonNull(shortCommitId, time, version, RandomStringUtils.randomAlphanumeric(12));

        if (p instanceof Instant) {
            return DateTimeFormatter.ISO_INSTANT.format((Instant) p);
        }
        return p.toString();
    }


    /** {@inheritDoc} */
    @Override
    public Object generate(Object target, Method method, Object... params) {
        return new PrefixedSimpleKey(prefix, method.getName(), params);
    }
}
