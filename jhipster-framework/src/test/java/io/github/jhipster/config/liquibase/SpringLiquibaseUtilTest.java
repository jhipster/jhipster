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
package io.github.jhipster.config.liquibase;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.DataSourceClosingSpringLiquibase;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.type;

public class SpringLiquibaseUtilTest {

    @Test
    public void createSpringLiquibaseFromLiquibaseDataSource() {
        DataSource liquibaseDatasource = DataSourceBuilder.create().url("jdbc:h2:mem:liquibase").username("sa").build();
        LiquibaseProperties liquibaseProperties = null;
        DataSource normalDataSource = null;
        DataSourceProperties dataSourceProperties = null;

        SpringLiquibase liquibase = SpringLiquibaseUtil.createSpringLiquibase(liquibaseDatasource, liquibaseProperties, normalDataSource, dataSourceProperties);
        assertThat(liquibase).isNotInstanceOf(DataSourceClosingSpringLiquibase.class)
            .extracting(SpringLiquibase::getDataSource).isEqualTo(liquibaseDatasource)
            .asInstanceOf(type(HikariDataSource.class))
            .hasFieldOrPropertyWithValue("jdbcUrl", "jdbc:h2:mem:liquibase")
            .hasFieldOrPropertyWithValue("username", "sa")
            .hasFieldOrPropertyWithValue("password", null);
    }

    @Test
    public void createSpringLiquibaseFromNormalDataSource() {
        DataSource liquibaseDatasource = null;
        LiquibaseProperties liquibaseProperties = new LiquibaseProperties();
        DataSource normalDataSource = DataSourceBuilder.create().url("jdbc:h2:mem:normal").username("sa").build();
        DataSourceProperties dataSourceProperties = null;

        SpringLiquibase liquibase = SpringLiquibaseUtil.createSpringLiquibase(liquibaseDatasource, liquibaseProperties, normalDataSource, dataSourceProperties);
        assertThat(liquibase).isNotInstanceOf(DataSourceClosingSpringLiquibase.class)
            .extracting(SpringLiquibase::getDataSource).isEqualTo(normalDataSource)
            .asInstanceOf(type(HikariDataSource.class))
            .hasFieldOrPropertyWithValue("jdbcUrl", "jdbc:h2:mem:normal")
            .hasFieldOrPropertyWithValue("username", "sa")
            .hasFieldOrPropertyWithValue("password", null);
    }

    @Test
    public void createSpringLiquibaseFromLiquibaseProperties() {
        DataSource liquibaseDatasource = null;
        LiquibaseProperties liquibaseProperties = new LiquibaseProperties();
        liquibaseProperties.setUrl("jdbc:h2:mem:liquibase");
        liquibaseProperties.setUser("sa");
        DataSource normalDataSource = null;
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setPassword("password");

        SpringLiquibase liquibase = SpringLiquibaseUtil.createSpringLiquibase(liquibaseDatasource, liquibaseProperties, normalDataSource, dataSourceProperties);
        assertThat(liquibase)
            .asInstanceOf(type(DataSourceClosingSpringLiquibase.class))
            .extracting(SpringLiquibase::getDataSource)
            .asInstanceOf(type(HikariDataSource.class))
            .hasFieldOrPropertyWithValue("jdbcUrl", "jdbc:h2:mem:liquibase")
            .hasFieldOrPropertyWithValue("username", "sa")
            .hasFieldOrPropertyWithValue("password", "password");
    }

    @Test
    public void createAsyncSpringLiquibaseFromLiquibaseDataSource() {
        DataSource liquibaseDatasource = DataSourceBuilder.create().url("jdbc:h2:mem:liquibase").username("sa").build();
        LiquibaseProperties liquibaseProperties = null;
        DataSource normalDataSource = null;
        DataSourceProperties dataSourceProperties = null;

        AsyncSpringLiquibase liquibase = SpringLiquibaseUtil.createAsyncSpringLiquibase(null, null, liquibaseDatasource, liquibaseProperties, normalDataSource, dataSourceProperties);
        assertThat(liquibase.getDataSource()).isEqualTo(liquibaseDatasource)
            .asInstanceOf(type(HikariDataSource.class))
            .hasFieldOrPropertyWithValue("jdbcUrl", "jdbc:h2:mem:liquibase")
            .hasFieldOrPropertyWithValue("username", "sa")
            .hasFieldOrPropertyWithValue("password", null);
    }

    @Test
    public void createAsyncSpringLiquibaseFromNormalDataSource() {
        DataSource liquibaseDatasource = null;
        LiquibaseProperties liquibaseProperties = new LiquibaseProperties();
        DataSource normalDataSource = DataSourceBuilder.create().url("jdbc:h2:mem:normal").username("sa").build();
        DataSourceProperties dataSourceProperties = null;

        AsyncSpringLiquibase liquibase = SpringLiquibaseUtil.createAsyncSpringLiquibase(null, null, liquibaseDatasource, liquibaseProperties, normalDataSource, dataSourceProperties);
        assertThat(liquibase.getDataSource()).isEqualTo(normalDataSource)
            .asInstanceOf(type(HikariDataSource.class))
            .hasFieldOrPropertyWithValue("jdbcUrl", "jdbc:h2:mem:normal")
            .hasFieldOrPropertyWithValue("username", "sa")
            .hasFieldOrPropertyWithValue("password", null);
    }

    @Test
    public void createAsyncSpringLiquibaseFromLiquibaseProperties() {
        DataSource liquibaseDatasource = null;
        LiquibaseProperties liquibaseProperties = new LiquibaseProperties();
        liquibaseProperties.setUrl("jdbc:h2:mem:liquibase");
        liquibaseProperties.setUser("sa");
        DataSource normalDataSource = null;
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setPassword("password");

        AsyncSpringLiquibase liquibase = SpringLiquibaseUtil.createAsyncSpringLiquibase(null, null, liquibaseDatasource, liquibaseProperties, normalDataSource, dataSourceProperties);
        assertThat(liquibase.getDataSource())
            .asInstanceOf(type(HikariDataSource.class))
            .hasFieldOrPropertyWithValue("jdbcUrl", "jdbc:h2:mem:liquibase")
            .hasFieldOrPropertyWithValue("username", "sa")
            .hasFieldOrPropertyWithValue("password", "password");
    }

}
