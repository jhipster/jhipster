package io.github.jhipster.config.cache;

import org.junit.jupiter.api.Test;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class PrefixedKeyGeneratorTest {

    @Test
    public void generatePrefixFromShortCommitId() {

        Properties gitProperties = new Properties();
        gitProperties.put("commit.id.abbrev", "1234");

        PrefixedKeyGenerator prefixedKeyGenerator = new PrefixedKeyGenerator(new GitProperties(gitProperties), null);

        assertThat(prefixedKeyGenerator.getPrefix()).isEqualTo("1234");
    }

    @Test
    public void generatePrefixFromCommitId() {

        Properties gitProperties = new Properties();
        gitProperties.put("commit.id", "1234567");

        PrefixedKeyGenerator prefixedKeyGenerator = new PrefixedKeyGenerator(new GitProperties(gitProperties), null);

        assertThat(prefixedKeyGenerator.getPrefix()).isEqualTo("1234567");
    }

    @Test
    public void generatePrefixFromBuildVersion() {

        Properties buildProperties = new Properties();
        buildProperties.put("version", "1.0.0");

        PrefixedKeyGenerator prefixedKeyGenerator = new PrefixedKeyGenerator(null, new BuildProperties(buildProperties));

        assertThat(prefixedKeyGenerator.getPrefix()).isEqualTo("1.0.0");
    }

    @Test
    public void generatePrefixFromBuildTime() {

        Properties buildProperties = new Properties();
        buildProperties.put("time", "1583955265");

        PrefixedKeyGenerator prefixedKeyGenerator = new PrefixedKeyGenerator(null, new BuildProperties(buildProperties));

        assertThat(prefixedKeyGenerator.getPrefix()).isEqualTo("1970-01-19T07:59:15.265Z");
    }

    @Test
    public void generatesRandomPrefix() {

        PrefixedKeyGenerator prefixedKeyGenerator = new PrefixedKeyGenerator(null, null);

        assertThat(prefixedKeyGenerator.getPrefix().length()).isEqualTo(12);
    }

}
