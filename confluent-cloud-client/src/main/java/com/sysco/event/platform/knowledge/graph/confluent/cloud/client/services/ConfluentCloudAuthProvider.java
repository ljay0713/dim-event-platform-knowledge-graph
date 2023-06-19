package com.sysco.event.platform.knowledge.graph.confluent.cloud.client.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ConfluentCloudAuthProvider {

    private static final Logger LOGGER = LogManager.getLogger(ConfluentCloudAuthProvider.class.getName());

    private final Environment env;

    public ConfluentCloudAuthProvider(Environment env) {
        this.env = env;
    }

    public AuthConfig getConfluentCloudAuthConfigForEnv(String confluentEnv) {
        LOGGER.debug("Fetching auth config for environment: {}", confluentEnv);
        AuthConfig authConfig = new AuthConfig();
        authConfig.setApiKey(env.getProperty(String.format("confluent.cloud.credentials.%s.api-key", confluentEnv)));
        authConfig.setApiSecret(env.getProperty(String.format("confluent.cloud.credentials.%s.api-secret", confluentEnv)));
        authConfig.setClusterID(env.getProperty(String.format("confluent.cloud.credentials.%s.cluster-id", confluentEnv)));
        authConfig.setApiEndpoint(env.getProperty(String.format("confluent.cloud.credentials.%s.api-endpoint", confluentEnv)));

        return authConfig;
    }
}
