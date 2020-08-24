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

package io.github.jhipster.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JHipsterPropertiesTest {

    private JHipsterProperties properties;

    @BeforeEach
    public void setup() {
        properties = new JHipsterProperties();
    }

    @Test
    public void testComplete() throws Exception {
        // Slightly pedantic; this checks if there are tests for each of the properties.
        Set<String> set = new LinkedHashSet<>(64, 1F);
        reflect(properties, set, "test");
        for (String name : set) {
            assertThat(this.getClass().getDeclaredMethod(name)).isNotNull();
        }
    }

    private void reflect(Object obj, Set<String> dst, String prefix) throws Exception {
        Class<?> src = obj.getClass();
        for (Method method : src.getDeclaredMethods()) {
            String name = method.getName();
            if (name.startsWith("get")) {
                Object res = method.invoke(obj, (Object[]) null);
                if (res != null && src.equals(res.getClass().getDeclaringClass())) {
                    reflect(res, dst, prefix + name.substring(3));
                }
            } else if (name.startsWith("set")) {
                dst.add(prefix + name.substring(3));
            }
        }
    }

    @Test
    public void testAsyncCorePoolSize() {
        JHipsterProperties.Async obj = properties.getAsync();
        int val = JHipsterDefaults.Async.corePoolSize;
        assertThat(obj.getCorePoolSize()).isEqualTo(val);
        val++;
        obj.setCorePoolSize(val);
        assertThat(obj.getCorePoolSize()).isEqualTo(val);
    }

    @Test
    public void testAsyncMaxPoolSize() {
        JHipsterProperties.Async obj = properties.getAsync();
        int val = JHipsterDefaults.Async.maxPoolSize;
        assertThat(obj.getMaxPoolSize()).isEqualTo(val);
        val++;
        obj.setMaxPoolSize(val);
        assertThat(obj.getMaxPoolSize()).isEqualTo(val);
    }

    @Test
    public void testAsyncQueueCapacity() {
        JHipsterProperties.Async obj = properties.getAsync();
        int val = JHipsterDefaults.Async.queueCapacity;
        assertThat(obj.getQueueCapacity()).isEqualTo(val);
        val++;
        obj.setQueueCapacity(val);
        assertThat(obj.getQueueCapacity()).isEqualTo(val);
    }

    @Test
    public void testHttpCacheTimeToLiveInDays() {
        JHipsterProperties.Http.Cache obj = properties.getHttp().getCache();
        int val = JHipsterDefaults.Http.Cache.timeToLiveInDays;
        assertThat(obj.getTimeToLiveInDays()).isEqualTo(val);
        val++;
        obj.setTimeToLiveInDays(val);
        assertThat(obj.getTimeToLiveInDays()).isEqualTo(val);
    }

    @Test
    public void testDatabaseCouchbaseBucketName() {
        JHipsterProperties.Database.Couchbase obj = properties.getDatabase().getCouchbase();
        assertThat(obj.getBucketName()).isEqualTo(null);
        obj.setBucketName("bucketName");
        assertThat(obj.getBucketName()).isEqualTo("bucketName");
    }

    @Test
    public void testCacheHazelcastTimeToLiveSeconds() {
        JHipsterProperties.Cache.Hazelcast obj = properties.getCache().getHazelcast();
        int val = JHipsterDefaults.Cache.Hazelcast.timeToLiveSeconds;
        assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
        val++;
        obj.setTimeToLiveSeconds(val);
        assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    }

    @Test
    public void testCacheHazelcastBackupCount() {
        JHipsterProperties.Cache.Hazelcast obj = properties.getCache().getHazelcast();
        int val = JHipsterDefaults.Cache.Hazelcast.backupCount;
        assertThat(obj.getBackupCount()).isEqualTo(val);
        val++;
        obj.setBackupCount(val);
        assertThat(obj.getBackupCount()).isEqualTo(val);
    }

    @Test
    public void testCacheHazelcastManagementCenterEnabled() {
        JHipsterProperties.Cache.Hazelcast.ManagementCenter obj =
            properties.getCache().getHazelcast().getManagementCenter();

        boolean val = JHipsterDefaults.Cache.Hazelcast.ManagementCenter.enabled;
        assertThat(obj.isEnabled()).isEqualTo(val);
        val = !val;
        obj.setEnabled(val);
        assertThat(obj.isEnabled()).isEqualTo(val);
    }

    @Test
    public void testCacheHazelcastManagementCenterUpdateInterval() {
        JHipsterProperties.Cache.Hazelcast.ManagementCenter obj =
            properties.getCache().getHazelcast().getManagementCenter();

        int val = JHipsterDefaults.Cache.Hazelcast.ManagementCenter.updateInterval;
        assertThat(obj.getUpdateInterval()).isEqualTo(val);
        val++;
        obj.setUpdateInterval(val);
        assertThat(obj.getUpdateInterval()).isEqualTo(val);
    }

    @Test
    public void testCacheHazelcastManagementCenterUrl() {
        JHipsterProperties.Cache.Hazelcast.ManagementCenter obj =
            properties.getCache().getHazelcast().getManagementCenter();

        String val = JHipsterDefaults.Cache.Hazelcast.ManagementCenter.url;
        assertThat(obj.getUrl()).isEqualTo(val);
        val = "http://localhost:8080";
        obj.setUrl(val);
        assertThat(obj.getUrl()).isEqualTo(val);
    }

    @Test
    public void testCacheCaffeineTimeToLiveSeconds() {
        JHipsterProperties.Cache.Caffeine obj = properties.getCache().getCaffeine();
        int val = JHipsterDefaults.Cache.Caffeine.timeToLiveSeconds;
        assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
        val++;
        obj.setTimeToLiveSeconds(val);
        assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    }

    @Test
    public void testCacheCaffeineMaxEntries() {
        JHipsterProperties.Cache.Caffeine obj = properties.getCache().getCaffeine();
        long val = JHipsterDefaults.Cache.Caffeine.maxEntries;
        assertThat(obj.getMaxEntries()).isEqualTo(val);
        val++;
        obj.setMaxEntries(val);
        assertThat(obj.getMaxEntries()).isEqualTo(val);
    }

    @Test
    public void testCacheEhcacheTimeToLiveSeconds() {
        JHipsterProperties.Cache.Ehcache obj = properties.getCache().getEhcache();
        int val = JHipsterDefaults.Cache.Ehcache.timeToLiveSeconds;
        assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
        val++;
        obj.setTimeToLiveSeconds(val);
        assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    }

    @Test
    public void testCacheEhcacheMaxEntries() {
        JHipsterProperties.Cache.Ehcache obj = properties.getCache().getEhcache();
        long val = JHipsterDefaults.Cache.Ehcache.maxEntries;
        assertThat(obj.getMaxEntries()).isEqualTo(val);
        val++;
        obj.setMaxEntries(val);
        assertThat(obj.getMaxEntries()).isEqualTo(val);
    }

    @Test
    public void testCacheInfinispanConfigFile() {
        JHipsterProperties.Cache.Infinispan obj = properties.getCache().getInfinispan();
        String val = JHipsterDefaults.Cache.Infinispan.configFile;
        assertThat(obj.getConfigFile()).isEqualTo(val);
        val = "1" + val;
        obj.setConfigFile(val);
        assertThat(obj.getConfigFile()).isEqualTo(val);
    }

    @Test
    public void testCacheInfinispanStatsEnabled() {
        JHipsterProperties.Cache.Infinispan obj = properties.getCache().getInfinispan();
        boolean val = JHipsterDefaults.Cache.Infinispan.statsEnabled;
        assertThat(obj.isStatsEnabled()).isEqualTo(val);
        val = !val;
        obj.setStatsEnabled(val);
        assertThat(obj.isStatsEnabled()).isEqualTo(val);
    }

    @Test
    public void testCacheInfinispanLocalTimeToLiveSeconds() {
        JHipsterProperties.Cache.Infinispan.Local obj = properties.getCache().getInfinispan().getLocal();
        long val = JHipsterDefaults.Cache.Infinispan.Local.timeToLiveSeconds;
        assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
        val++;
        obj.setTimeToLiveSeconds(val);
        assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    }

    @Test
    public void testCacheInfinispanLocalMaxEntries() {
        JHipsterProperties.Cache.Infinispan.Local obj = properties.getCache().getInfinispan().getLocal();
        long val = JHipsterDefaults.Cache.Infinispan.Local.maxEntries;
        assertThat(obj.getMaxEntries()).isEqualTo(val);
        val++;
        obj.setMaxEntries(val);
        assertThat(obj.getMaxEntries()).isEqualTo(val);
    }

    @Test
    public void testCacheInfinispanDistributedTimeToLiveSeconds() {
        JHipsterProperties.Cache.Infinispan.Distributed obj = properties.getCache().getInfinispan().getDistributed();
        long val = JHipsterDefaults.Cache.Infinispan.Distributed.timeToLiveSeconds;
        assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
        val++;
        obj.setTimeToLiveSeconds(val);
        assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    }

    @Test
    public void testCacheInfinispanDistributedMaxEntries() {
        JHipsterProperties.Cache.Infinispan.Distributed obj = properties.getCache().getInfinispan().getDistributed();
        long val = JHipsterDefaults.Cache.Infinispan.Distributed.maxEntries;
        assertThat(obj.getMaxEntries()).isEqualTo(val);
        val++;
        obj.setMaxEntries(val);
        assertThat(obj.getMaxEntries()).isEqualTo(val);
    }

    @Test
    public void testCacheInfinispanDistributedInstanceCount() {
        JHipsterProperties.Cache.Infinispan.Distributed obj = properties.getCache().getInfinispan().getDistributed();
        int val = JHipsterDefaults.Cache.Infinispan.Distributed.instanceCount;
        assertThat(obj.getInstanceCount()).isEqualTo(val);
        val++;
        obj.setInstanceCount(val);
        assertThat(obj.getInstanceCount()).isEqualTo(val);
    }

    @Test
    public void testCacheInfinispanReplicatedTimeToLiveSeconds() {
        JHipsterProperties.Cache.Infinispan.Replicated obj = properties.getCache().getInfinispan().getReplicated();
        long val = JHipsterDefaults.Cache.Infinispan.Replicated.timeToLiveSeconds;
        assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
        val++;
        obj.setTimeToLiveSeconds(val);
        assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    }

    @Test
    public void testCacheInfinispanReplicatedMaxEntries() {
        JHipsterProperties.Cache.Infinispan.Replicated obj = properties.getCache().getInfinispan().getReplicated();
        long val = JHipsterDefaults.Cache.Infinispan.Replicated.maxEntries;
        assertThat(obj.getMaxEntries()).isEqualTo(val);
        val++;
        obj.setMaxEntries(val);
        assertThat(obj.getMaxEntries()).isEqualTo(val);
    }

    @Test
    public void testCacheMemcachedEnabled() {
        JHipsterProperties.Cache.Memcached obj = properties.getCache().getMemcached();
        boolean val = JHipsterDefaults.Cache.Memcached.enabled;
        assertThat(obj.isEnabled()).isEqualTo(val);
        val = true;
        obj.setEnabled(val);
        assertThat(obj.isEnabled()).isEqualTo(val);
    }

    @Test
    public void testCacheMemcachedServers() {
        JHipsterProperties.Cache.Memcached obj = properties.getCache().getMemcached();
        String val = JHipsterDefaults.Cache.Memcached.servers;
        assertThat(obj.getServers()).isEqualTo(val);
        val = "myserver:1337";
        obj.setServers(val);
        assertThat(obj.getServers()).isEqualTo(val);
    }

    @Test
    public void testCacheMemcachedExpiration() {
        JHipsterProperties.Cache.Memcached obj = properties.getCache().getMemcached();
        int val = JHipsterDefaults.Cache.Memcached.expiration;
        assertThat(obj.getExpiration()).isEqualTo(val);
        val++;
        obj.setExpiration(val);
        assertThat(obj.getExpiration()).isEqualTo(val);
    }

    @Test
    public void testCacheMemcachedUseBinaryProtocol() {
        JHipsterProperties.Cache.Memcached obj = properties.getCache().getMemcached();
        boolean val = JHipsterDefaults.Cache.Memcached.useBinaryProtocol;
        assertThat(obj.isUseBinaryProtocol()).isEqualTo(val);
        val = false;
        obj.setUseBinaryProtocol(val);
        assertThat(obj.isUseBinaryProtocol()).isEqualTo(val);
    }

    @Test
    public void testCacheMemcachedAuthenticationEnabled() {
        JHipsterProperties.Cache.Memcached.Authentication obj = properties.getCache().getMemcached().getAuthentication();
        boolean val = JHipsterDefaults.Cache.Memcached.Authentication.enabled;
        assertThat(obj.isEnabled()).isEqualTo(val);
        val = false;
        obj.setEnabled(val);
        assertThat(obj.isEnabled()).isEqualTo(val);
    }

    @Test
    public void testCacheMemcachedAuthenticationPassword() {
        JHipsterProperties.Cache.Memcached.Authentication obj = properties.getCache().getMemcached().getAuthentication();
        assertThat(obj.getPassword()).isEqualTo(null);
        obj.setPassword("MEMCACHEPASSWORD");
        assertThat(obj.getPassword()).isEqualTo("MEMCACHEPASSWORD");
    }

    @Test
    public void testCacheMemcachedAuthenticationUsername() {
        JHipsterProperties.Cache.Memcached.Authentication obj = properties.getCache().getMemcached().getAuthentication();
        assertThat(obj.getUsername()).isEqualTo(null);
        obj.setUsername("MEMCACHEUSER");
        assertThat(obj.getUsername()).isEqualTo("MEMCACHEUSER");
    }

    @Test
    public void testCacheRedisServer() {
        JHipsterProperties.Cache.Redis obj = properties.getCache().getRedis();
        String[] val = JHipsterDefaults.Cache.Redis.server;
        assertThat(obj.getServer()).isEqualTo(val);
        val = new String[]{"myserver:1337"};
        obj.setServer(val);
        assertThat(obj.getServer()).isEqualTo(val);
    }

    @Test
    public void testCacheRedisExpiration() {
        JHipsterProperties.Cache.Redis obj = properties.getCache().getRedis();
        int val = JHipsterDefaults.Cache.Redis.expiration;
        assertThat(obj.getExpiration()).isEqualTo(val);
        val++;
        obj.setExpiration(val);
        assertThat(obj.getExpiration()).isEqualTo(val);
    }

    @Test
    public void testCacheRedisCluster() {
        JHipsterProperties.Cache.Redis obj = properties.getCache().getRedis();
        boolean val = JHipsterDefaults.Cache.Redis.cluster;
        assertThat(obj.isCluster()).isEqualTo(val);
        val = !val;
        obj.setCluster(val);
        assertThat(obj.isCluster()).isEqualTo(val);
    }

    @Test
    public void testCacheRedisConnectionMinimumIdleSize() {
        JHipsterProperties.Cache.Redis obj = properties.getCache().getRedis();
        int val = JHipsterDefaults.Cache.Redis.connectionMinimumIdleSize;
        assertThat(obj.getConnectionMinimumIdleSize()).isEqualTo(val);
        val++;
        obj.setConnectionMinimumIdleSize(val);
        assertThat(obj.getConnectionMinimumIdleSize()).isEqualTo(val);
    }

    @Test
    public void testCacheRedisConnectionPoolSize() {
        JHipsterProperties.Cache.Redis obj = properties.getCache().getRedis();
        int val = JHipsterDefaults.Cache.Redis.connectionPoolSize;
        assertThat(obj.getConnectionPoolSize()).isEqualTo(val);
        val++;
        obj.setConnectionPoolSize(val);
        assertThat(obj.getConnectionPoolSize()).isEqualTo(val);
    }

    @Test
    public void testCacheRedisSubscriptionConnectionMinimumIdleSize() {
        JHipsterProperties.Cache.Redis obj = properties.getCache().getRedis();
        int val = JHipsterDefaults.Cache.Redis.subscriptionConnectionMinimumIdleSize;
        assertThat(obj.getSubscriptionConnectionMinimumIdleSize()).isEqualTo(val);
        val++;
        obj.setSubscriptionConnectionMinimumIdleSize(val);
        assertThat(obj.getSubscriptionConnectionMinimumIdleSize()).isEqualTo(val);
    }

    @Test
    public void testCacheRedisSubscriptionConnectionPoolSize() {
        JHipsterProperties.Cache.Redis obj = properties.getCache().getRedis();
        int val = JHipsterDefaults.Cache.Redis.subscriptionConnectionPoolSize;
        assertThat(obj.getSubscriptionConnectionPoolSize()).isEqualTo(val);
        val++;
        obj.setSubscriptionConnectionPoolSize(val);
        assertThat(obj.getSubscriptionConnectionPoolSize()).isEqualTo(val);

    }

    @Test
    public void testMailFrom() {
        JHipsterProperties.Mail obj = properties.getMail();
        String val = JHipsterDefaults.Mail.from;
        assertThat(obj.getFrom()).isEqualTo(val);
        val = "1" + val;
        obj.setFrom(val);
        assertThat(obj.getFrom()).isEqualTo(val);
    }

    @Test
    public void testMailBaseUrl() {
        JHipsterProperties.Mail obj = properties.getMail();
        String val = JHipsterDefaults.Mail.baseUrl;
        assertThat(obj.getBaseUrl()).isEqualTo(val);
        val = "1" + val;
        obj.setBaseUrl(val);
        assertThat(obj.getBaseUrl()).isEqualTo(val);
    }

    @Test
    public void testMailEnabled() {
        JHipsterProperties.Mail obj = properties.getMail();
        boolean val = JHipsterDefaults.Mail.enabled;
        assertThat(obj.isEnabled()).isEqualTo(val);
        val = !val;
        obj.setEnabled(val);
        assertThat(obj.isEnabled()).isEqualTo(val);
    }

    @Test
    public void testSecurityClientAuthorizationAccessTokenUri() {
        JHipsterProperties.Security.ClientAuthorization obj = properties.getSecurity().getClientAuthorization();
        String val = JHipsterDefaults.Security.ClientAuthorization.accessTokenUri;
        assertThat(obj.getAccessTokenUri()).isEqualTo(val);
        val = "1" + val;
        obj.setAccessTokenUri(val);
        assertThat(obj.getAccessTokenUri()).isEqualTo(val);
    }

    @Test
    public void testSecurityClientAuthorizationTokenServiceId() {
        JHipsterProperties.Security.ClientAuthorization obj = properties.getSecurity().getClientAuthorization();
        String val = JHipsterDefaults.Security.ClientAuthorization.tokenServiceId;
        assertThat(obj.getTokenServiceId()).isEqualTo(val);
        val = "1" + val;
        obj.setTokenServiceId(val);
        assertThat(obj.getTokenServiceId()).isEqualTo(val);
    }

    @Test
    public void testSecurityClientAuthorizationClientId() {
        JHipsterProperties.Security.ClientAuthorization obj = properties.getSecurity().getClientAuthorization();
        String val = JHipsterDefaults.Security.ClientAuthorization.clientId;
        assertThat(obj.getClientId()).isEqualTo(val);
        val = "1" + val;
        obj.setClientId(val);
        assertThat(obj.getClientId()).isEqualTo(val);
    }

    @Test
    public void testSecurityClientAuthorizationClientSecret() {
        JHipsterProperties.Security.ClientAuthorization obj = properties.getSecurity().getClientAuthorization();
        String val = JHipsterDefaults.Security.ClientAuthorization.clientSecret;
        assertThat(obj.getClientSecret()).isEqualTo(val);
        val = "1" + val;
        obj.setClientSecret(val);
        assertThat(obj.getClientSecret()).isEqualTo(val);
    }

    @Test
    public void testSecurityAuthenticationJwtSecret() {
        JHipsterProperties.Security.Authentication.Jwt obj = properties.getSecurity().getAuthentication().getJwt();
        String val = JHipsterDefaults.Security.Authentication.Jwt.secret;
        assertThat(obj.getSecret()).isEqualTo(val);
        val = "1" + val;
        obj.setSecret(val);
        assertThat(obj.getSecret()).isEqualTo(val);
    }

    @Test
    public void testSecurityAuthenticationJwtBase64Secret() {
        JHipsterProperties.Security.Authentication.Jwt obj = properties.getSecurity().getAuthentication().getJwt();
        String val = JHipsterDefaults.Security.Authentication.Jwt.base64Secret;
        assertThat(obj.getSecret()).isEqualTo(val);
        val = "1" + val;
        obj.setBase64Secret(val);
        assertThat(obj.getBase64Secret()).isEqualTo(val);
    }

    @Test
    public void testSecurityAuthenticationJwtTokenValidityInSeconds() {
        JHipsterProperties.Security.Authentication.Jwt obj = properties.getSecurity().getAuthentication().getJwt();
        long val = JHipsterDefaults.Security.Authentication.Jwt.tokenValidityInSeconds;
        assertThat(obj.getTokenValidityInSeconds()).isEqualTo(val);
        val++;
        obj.setTokenValidityInSeconds(val);
        assertThat(obj.getTokenValidityInSeconds()).isEqualTo(val);
    }

    @Test
    public void testSecurityAuthenticationJwtTokenValidityInSecondsForRememberMe() {
        JHipsterProperties.Security.Authentication.Jwt obj = properties.getSecurity().getAuthentication().getJwt();
        long val = JHipsterDefaults.Security.Authentication.Jwt.tokenValidityInSecondsForRememberMe;
        assertThat(obj.getTokenValidityInSecondsForRememberMe()).isEqualTo(val);
        val++;
        obj.setTokenValidityInSecondsForRememberMe(val);
        assertThat(obj.getTokenValidityInSecondsForRememberMe()).isEqualTo(val);
    }

    @Test
    public void testSecurityRememberMeKey() {
        JHipsterProperties.Security.RememberMe obj = properties.getSecurity().getRememberMe();
        String val = JHipsterDefaults.Security.RememberMe.key;
        assertThat(obj.getKey()).isEqualTo(val);
        val = "1" + val;
        obj.setKey(val);
        assertThat(obj.getKey()).isEqualTo(val);
    }

    @Test
    public void testSecurityOauth2Audience() {
        JHipsterProperties.Security.OAuth2 obj = properties.getSecurity().getOauth2();
        assertThat(obj).isNotNull();
        assertThat(obj.getAudience()).isNotNull().isEmpty();

        obj.setAudience(Arrays.asList("default", "account"));
        assertThat(obj.getAudience()).isNotEmpty().size().isEqualTo(2);
        assertThat(obj.getAudience()).contains("default", "account");
    }

    @Test
    public void testApiDocsTitle() {
        JHipsterProperties.ApiDocs obj = properties.getApiDocs();
        String val = JHipsterDefaults.ApiDocs.title;
        assertThat(obj.getTitle()).isEqualTo(val);
        val = "1" + val;
        obj.setTitle(val);
        assertThat(obj.getTitle()).isEqualTo(val);
    }

    @Test
    public void testApiDocsDescription() {
        JHipsterProperties.ApiDocs obj = properties.getApiDocs();
        String val = JHipsterDefaults.ApiDocs.description;
        assertThat(obj.getDescription()).isEqualTo(val);
        val = "1" + val;
        obj.setDescription(val);
        assertThat(obj.getDescription()).isEqualTo(val);
    }

    @Test
    public void testApiDocsVersion() {
        JHipsterProperties.ApiDocs obj = properties.getApiDocs();
        String val = JHipsterDefaults.ApiDocs.version;
        assertThat(obj.getVersion()).isEqualTo(val);
        val = "1" + val;
        obj.setVersion(val);
        assertThat(obj.getVersion()).isEqualTo(val);
    }

    @Test
    public void testApiDocsTermsOfServiceUrl() {
        JHipsterProperties.ApiDocs obj = properties.getApiDocs();
        String val = JHipsterDefaults.ApiDocs.termsOfServiceUrl;
        assertThat(obj.getTermsOfServiceUrl()).isEqualTo(val);
        val = "1" + val;
        obj.setTermsOfServiceUrl(val);
        assertThat(obj.getTermsOfServiceUrl()).isEqualTo(val);
    }

    @Test
    public void testApiDocsContactName() {
        JHipsterProperties.ApiDocs obj = properties.getApiDocs();
        String val = JHipsterDefaults.ApiDocs.contactName;
        assertThat(obj.getContactName()).isEqualTo(val);
        val = "1" + val;
        obj.setContactName(val);
        assertThat(obj.getContactName()).isEqualTo(val);
    }

    @Test
    public void testApiDocsContactUrl() {
        JHipsterProperties.ApiDocs obj = properties.getApiDocs();
        String val = JHipsterDefaults.ApiDocs.contactUrl;
        assertThat(obj.getContactUrl()).isEqualTo(val);
        val = "1" + val;
        obj.setContactUrl(val);
        assertThat(obj.getContactUrl()).isEqualTo(val);
    }

    @Test
    public void testApiDocsContactEmail() {
        JHipsterProperties.ApiDocs obj = properties.getApiDocs();
        String val = JHipsterDefaults.ApiDocs.contactEmail;
        assertThat(obj.getContactEmail()).isEqualTo(val);
        val = "1" + val;
        obj.setContactEmail(val);
        assertThat(obj.getContactEmail()).isEqualTo(val);
    }

    @Test
    public void testApiDocsLicense() {
        JHipsterProperties.ApiDocs obj = properties.getApiDocs();
        String val = JHipsterDefaults.ApiDocs.license;
        assertThat(obj.getLicense()).isEqualTo(val);
        val = "1" + val;
        obj.setLicense(val);
        assertThat(obj.getLicense()).isEqualTo(val);
    }

    @Test
    public void testApiDocsLicenseUrl() {
        JHipsterProperties.ApiDocs obj = properties.getApiDocs();
        String val = JHipsterDefaults.ApiDocs.licenseUrl;
        assertThat(obj.getLicenseUrl()).isEqualTo(val);
        val = "1" + val;
        obj.setLicenseUrl(val);
        assertThat(obj.getLicenseUrl()).isEqualTo(val);
    }

    @Test
    public void testApiDocsDefaultIncludePattern() {
        JHipsterProperties.ApiDocs obj = properties.getApiDocs();
        String val = JHipsterDefaults.ApiDocs.defaultIncludePattern;
        assertThat(obj.getDefaultIncludePattern()).isEqualTo(val);
        val = "1" + val;
        obj.setDefaultIncludePattern(val);
        assertThat(obj.getDefaultIncludePattern()).isEqualTo(val);
    }

    @Test
    public void testApiDocsHost() {
        JHipsterProperties.ApiDocs obj = properties.getApiDocs();
        String val = JHipsterDefaults.ApiDocs.host;
        assertThat(obj.getHost()).isEqualTo(val);
        val = "1" + val;
        obj.setHost(val);
        assertThat(obj.getHost()).isEqualTo(val);
    }

    @Test
    public void testApiDocsProtocols() {
        JHipsterProperties.ApiDocs obj = properties.getApiDocs();
        String[] def = JHipsterDefaults.ApiDocs.protocols;
        ArrayList<String> val;
        if (def != null) {
            val = newArrayList(def);
            assertThat(obj.getProtocols()).containsExactlyElementsOf(newArrayList(val));
        } else {
            assertThat(obj.getProtocols()).isNull();
            def = new String[1];
            val = new ArrayList<>(1);
        }
        val.add("1");
        obj.setProtocols(val.toArray(def));
        assertThat(obj.getProtocols()).containsExactlyElementsOf(newArrayList(val));
    }

    @Test
    public void testApiDocsServers() {
        JHipsterProperties.ApiDocs obj = properties.getApiDocs();
        assertThat(obj.getServers().length).isEqualTo(0);
        JHipsterProperties.ApiDocs.Server server = new JHipsterProperties.ApiDocs.Server();
        server.setUrl("url");
        server.setDescription("description");
        server.setName("name");

        JHipsterProperties.ApiDocs.Server[] val = new JHipsterProperties.ApiDocs.Server[]{server};

        obj.setServers(val);
        assertThat(obj.getServers().length).isEqualTo(1);
        assertThat(obj.getServers()[0].getName()).isEqualTo(server.getName());
        assertThat(obj.getServers()[0].getUrl()).isEqualTo(server.getUrl());
        assertThat(obj.getServers()[0].getDescription()).isEqualTo(server.getDescription());
    }

    @Test
    public void testApiDocsUseDefaultResponseMessages() {
        JHipsterProperties.ApiDocs obj = properties.getApiDocs();
        boolean val = JHipsterDefaults.ApiDocs.useDefaultResponseMessages;
        assertThat(obj.isUseDefaultResponseMessages()).isEqualTo(val);
        val = false;
        obj.setUseDefaultResponseMessages(val);
        assertThat(obj.isUseDefaultResponseMessages()).isEqualTo(val);
    }

    @Test
    public void testMetricsLogsEnabled() {
        JHipsterProperties.Metrics.Logs obj = properties.getMetrics().getLogs();
        boolean val = JHipsterDefaults.Metrics.Logs.enabled;
        assertThat(obj.isEnabled()).isEqualTo(val);
        val = !val;
        obj.setEnabled(val);
        assertThat(obj.isEnabled()).isEqualTo(val);
    }

    @Test
    public void testMetricsLogsReportFrequency() {
        JHipsterProperties.Metrics.Logs obj = properties.getMetrics().getLogs();
        long val = JHipsterDefaults.Metrics.Logs.reportFrequency;
        assertThat(obj.getReportFrequency()).isEqualTo(val);
        val++;
        obj.setReportFrequency(val);
        assertThat(obj.getReportFrequency()).isEqualTo(val);
    }

    @Test
    public void testLoggingUseJsonFormat() {
        JHipsterProperties.Logging obj = properties.getLogging();
        boolean val = JHipsterDefaults.Logging.useJsonFormat;
        assertThat(obj.isUseJsonFormat()).isEqualTo(val);
        val = true;
        obj.setUseJsonFormat(val);
        assertThat(obj.isUseJsonFormat()).isEqualTo(val);
    }

    @Test
    public void testLoggingLogstashEnabled() {
        JHipsterProperties.Logging.Logstash obj = properties.getLogging().getLogstash();
        boolean val = JHipsterDefaults.Logging.Logstash.enabled;
        assertThat(obj.isEnabled()).isEqualTo(val);
        val = !val;
        obj.setEnabled(val);
        assertThat(obj.isEnabled()).isEqualTo(val);
    }

    @Test
    public void testLoggingLogstashHost() {
        JHipsterProperties.Logging.Logstash obj = properties.getLogging().getLogstash();
        String val = JHipsterDefaults.Logging.Logstash.host;
        assertThat(obj.getHost()).isEqualTo(val);
        val = "1" + val;
        obj.setHost(val);
        assertThat(obj.getHost()).isEqualTo(val);
    }

    @Test
    public void testLoggingLogstashPort() {
        JHipsterProperties.Logging.Logstash obj = properties.getLogging().getLogstash();
        int val = JHipsterDefaults.Logging.Logstash.port;
        assertThat(obj.getPort()).isEqualTo(val);
        val++;
        obj.setPort(val);
        assertThat(obj.getPort()).isEqualTo(val);
    }

    @Test
    public void testLoggingLogstashQueueSize() {
        JHipsterProperties.Logging.Logstash obj = properties.getLogging().getLogstash();
        int val = JHipsterDefaults.Logging.Logstash.queueSize;
        assertThat(obj.getQueueSize()).isEqualTo(val);
        val++;
        obj.setQueueSize(val);
        assertThat(obj.getQueueSize()).isEqualTo(val);
    }

    @Test
    public void testSocialRedirectAfterSignIn() {
        JHipsterProperties.Social obj = properties.getSocial();
        String val = JHipsterDefaults.Social.redirectAfterSignIn;
        assertThat(obj.getRedirectAfterSignIn()).isEqualTo(val);
        val = "1" + val;
        obj.setRedirectAfterSignIn(val);
        assertThat(obj.getRedirectAfterSignIn()).isEqualTo(val);
    }

    @Test
    public void testGatewayAuthorizedMicroservicesEndpoints() {
        JHipsterProperties.Gateway obj = properties.getGateway();
        Map<String, List<String>> val = JHipsterDefaults.Gateway.authorizedMicroservicesEndpoints;
        assertThat(obj.getAuthorizedMicroservicesEndpoints()).isEqualTo(val);
        val.put("1", null);
        obj.setAuthorizedMicroservicesEndpoints(val);
        assertThat(obj.getAuthorizedMicroservicesEndpoints()).isEqualTo(val);
    }

    @Test
    public void testGatewayRateLimitingEnabled() {
        JHipsterProperties.Gateway.RateLimiting obj = properties.getGateway().getRateLimiting();
        boolean val = JHipsterDefaults.Gateway.RateLimiting.enabled;
        assertThat(obj.isEnabled()).isEqualTo(val);
        val = !val;
        obj.setEnabled(val);
        assertThat(obj.isEnabled()).isEqualTo(val);
    }

    @Test
    public void testGatewayRateLimitingLimit() {
        JHipsterProperties.Gateway.RateLimiting obj = properties.getGateway().getRateLimiting();
        long val = JHipsterDefaults.Gateway.RateLimiting.limit;
        assertThat(obj.getLimit()).isEqualTo(val);
        val++;
        obj.setLimit(val);
        assertThat(obj.getLimit()).isEqualTo(val);
    }

    @Test
    public void testGatewayRateLimitingDurationInSeconds() {
        JHipsterProperties.Gateway.RateLimiting obj = properties.getGateway().getRateLimiting();
        int val = JHipsterDefaults.Gateway.RateLimiting.durationInSeconds;
        assertThat(obj.getDurationInSeconds()).isEqualTo(val);
        val++;
        obj.setDurationInSeconds(val);
        assertThat(obj.getDurationInSeconds()).isEqualTo(val);
    }

    @Test
    public void testRegistryPassword() {
        JHipsterProperties.Registry obj = properties.getRegistry();
        String val = JHipsterDefaults.Registry.password;
        assertThat(obj.getPassword()).isEqualTo(val);
        val = "1" + val;
        obj.setPassword(val);
        assertThat(obj.getPassword()).isEqualTo(val);
    }

    @Test
    public void testClientAppName() {
        JHipsterProperties.ClientApp obj = properties.getClientApp();
        String val = JHipsterDefaults.ClientApp.name;
        assertThat(obj.getName()).isEqualTo(val);
        val = "1" + val;
        obj.setName(val);
        assertThat(obj.getName()).isEqualTo(val);
    }

    @Test
    public void testAuditEventsRetentionPeriod() {
        JHipsterProperties.AuditEvents obj = properties.getAuditEvents();
        int val = JHipsterDefaults.AuditEvents.retentionPeriod;
        assertThat(obj.getRetentionPeriod()).isEqualTo(val);
        val++;
        obj.setRetentionPeriod(val);
        assertThat(obj.getRetentionPeriod()).isEqualTo(val);
    }
}
