package com.sysco.event.platform.knowledge.graph.core.service;

import com.sysco.event.platform.knowledge.graph.confluent.cloud.client.domain.ACLListResponse;
import com.sysco.event.platform.knowledge.graph.confluent.cloud.client.services.ConfluentCloudClient;
import com.sysco.event.platform.knowledge.graph.core.domain.AggregatedTopics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    private static final Logger LOGGER = LogManager.getLogger(TopicService.class.getName());

    private final ConfluentCloudClient confluentCloudClient;

    public TopicService(ConfluentCloudClient confluentCloudClient) {
        this.confluentCloudClient = confluentCloudClient;
    }


    public AggregatedTopics getTopicsForServiceAccount(String serviceAccountID, String confluentEnvironment) {
        LOGGER.debug("Fetching topics for service account:{}, environment:{}", serviceAccountID, confluentEnvironment);

        List<ACLListResponse.Data> dataList = confluentCloudClient.getACLsForServiceAccount(serviceAccountID, confluentEnvironment).getData();

        List<String> consumed = dataList.stream().filter(acl -> acl.getOperation().equalsIgnoreCase("READ"))
                        .map(ACLListResponse.Data::getResourceName).toList();
        List<String> produced = dataList.stream().filter(acl -> acl.getOperation().equalsIgnoreCase("WRITE"))
                        .map(ACLListResponse.Data::getResourceName).toList();

        LOGGER.debug("Successfully fetched topics for service account:{}, environment:{} - consumed topics:{}, produced topics:{}",
                        serviceAccountID, confluentEnvironment, consumed, produced);
        return new AggregatedTopics(consumed, produced);
    }
}
