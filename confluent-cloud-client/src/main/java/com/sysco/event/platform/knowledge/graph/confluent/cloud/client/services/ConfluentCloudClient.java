package com.sysco.event.platform.knowledge.graph.confluent.cloud.client.services;

import com.sysco.event.platform.knowledge.graph.confluent.cloud.client.domain.ACLListResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ConfluentCloudClient {

    private static final Logger LOGGER = LogManager.getLogger(ConfluentCloudClient.class.getName());

    private final ConfluentCloudAuthProvider authProvider;
    private final WebClient webClient;

    public ConfluentCloudClient(ConfluentCloudAuthProvider authProvider) {
        this.webClient = WebClient.create();
        this.authProvider = authProvider;
    }

    public ACLListResponse getACLsForServiceAccount(String serviceAccountID, String confluentEnv) {
        try {
            LOGGER.info("Fetching ACLs for service account {}", serviceAccountID);
            AuthConfig authConfig = authProvider.getConfluentCloudAuthConfigForEnv(confluentEnv);

            String url = String.format("%s/kafka/v3/clusters/%s/acls?resource_type=TOPIC&permission=ALLOW&principal=ServiceAccount:%s"
                            , authConfig.getApiEndpoint(), authConfig.getClusterID(), serviceAccountID);
            return this.webClient.get()
                            .uri(url)
                            .headers(httpHeaders -> httpHeaders.setBasicAuth(authConfig.getApiKey(), authConfig.getApiSecret()))
                            .retrieve().bodyToMono(ACLListResponse.class)
                            .block();
        } catch (Exception e) {
            LOGGER.error("Error occurred while fetching ACLs for service account", e);
            throw e;
        }
    }

    public ACLListResponse getALCsForTopic(String topicName, String confluentEnv) {
        try {
            LOGGER.info("Fetching ACLs for topic {}", topicName);
            AuthConfig authConfig = authProvider.getConfluentCloudAuthConfigForEnv(confluentEnv);

            String url = String.format("%s/kafka/v3/clusters/%s/acls?resource_type=TOPIC&permission=ALLOW&resource_name=%s"
                            , authConfig.getApiEndpoint(), authConfig.getClusterID(), topicName);
            return this.webClient.get()
                            .uri(url)
                            .headers(httpHeaders -> httpHeaders.setBasicAuth(authConfig.getApiKey(), authConfig.getApiSecret()))
                            .retrieve().bodyToMono(ACLListResponse.class)
                            .block();
        } catch (Exception e) {
            LOGGER.error("Error occurred while fetching ACLs for topic", e);
            throw e;
        }
    }
}
