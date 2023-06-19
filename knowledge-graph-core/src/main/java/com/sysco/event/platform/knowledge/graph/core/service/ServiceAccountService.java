package com.sysco.event.platform.knowledge.graph.core.service;

import com.sysco.event.platform.knowledge.graph.confluent.cloud.client.domain.ACLListResponse;
import com.sysco.event.platform.knowledge.graph.confluent.cloud.client.services.ConfluentCloudClient;
import com.sysco.event.platform.knowledge.graph.core.domain.AggregatedServiceAccounts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceAccountService {

    private static final Logger LOGGER = LogManager.getLogger(ServiceAccountService.class.getName());


    private final ConfluentCloudClient confluentCloudClient;

    public ServiceAccountService(ConfluentCloudClient confluentCloudClient) {
        this.confluentCloudClient = confluentCloudClient;
    }

    public AggregatedServiceAccounts getServiceAccountsForTopic(String topicName, String confluentEnvironment) {

        LOGGER.debug("Fetching topics for topic:{}, environment:{}", topicName, confluentEnvironment);
        List<ACLListResponse.Data> dataList = confluentCloudClient.getALCsForTopic(topicName, confluentEnvironment).getData();

        List<String> consumers = dataList.stream().filter(acl -> acl.getOperation().equalsIgnoreCase("READ"))
                        .map(ACLListResponse.Data::getPrincipal).toList();
        List<String> producers = dataList.stream().filter(acl -> acl.getOperation().equalsIgnoreCase("WRITE"))
                        .map(ACLListResponse.Data::getPrincipal).toList();

        LOGGER.debug("Successfully fetched service accounts for topic:{}, environment:{} - consumer service accounts:{}, " +
                        "producer service accounts:{}", topicName, confluentEnvironment, consumers, producers);
        return new AggregatedServiceAccounts(consumers, producers);
    }
}
