package com.thonbecker.endurance.configuration;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSession;
import org.springframework.session.hazelcast.HazelcastIndexedSessionRepository;
import org.springframework.session.hazelcast.HazelcastSessionSerializer;
import org.springframework.session.hazelcast.PrincipalNameExtractor;
import org.springframework.session.hazelcast.config.annotation.SpringSessionHazelcastInstance;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
@EnableHazelcastHttpSession
public class SessionConfiguration {
    public static final String SESSIONS_MAP_NAME = "spring:session:sessions";
    public static final String CLUSTER_NAME = "spring-session-cluster";
    public static final String COOKIE_NAME = "JSESSIONID";
    public static final String COOKIE_PATH = "/";
    public static final String DOMAIN_NAME_PATTERN = "^.+?\\.(\\w+\\.[a-z]+)$";

    @Bean
    @SpringSessionHazelcastInstance
    public HazelcastInstance hazelcastInstance() {
        Config config = createHazelcastConfig();
        return Hazelcast.newHazelcastInstance(config);
    }

    private Config createHazelcastConfig() {
        Config config = new Config();
        config.setClusterName(CLUSTER_NAME);

        AttributeConfig attributeConfig = new AttributeConfig()
                .setName(HazelcastIndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
                .setExtractorClassName(PrincipalNameExtractor.class.getName());

        config.getMapConfig(SESSIONS_MAP_NAME)
                .addAttributeConfig(attributeConfig)
                .addIndexConfig(
                        new IndexConfig(IndexType.HASH, HazelcastIndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE));

        SerializerConfig serializerConfig = new SerializerConfig();
        serializerConfig.setImplementation(new HazelcastSessionSerializer()).setTypeClass(MapSession.class);
        config.getSerializationConfig().addSerializerConfig(serializerConfig);

        return config;
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName(COOKIE_NAME);
        serializer.setCookiePath(COOKIE_PATH);
        serializer.setDomainNamePattern(DOMAIN_NAME_PATTERN);
        return serializer;
    }
}
