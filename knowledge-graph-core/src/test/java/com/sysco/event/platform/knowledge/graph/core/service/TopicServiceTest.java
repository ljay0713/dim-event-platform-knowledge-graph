package com.sysco.event.platform.knowledge.graph.core.service;

import com.sysco.event.platform.knowledge.graph.confluent.cloud.client.domain.ACLListResponse;
import com.sysco.event.platform.knowledge.graph.confluent.cloud.client.services.ConfluentCloudClient;
import com.sysco.event.platform.knowledge.graph.core.domain.AggregatedTopics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

class TopicServiceTest {

    @Test
    void getTopicsForServiceAccountTest() {
        String envName = "testEnv";
        String serviceAccountName = "testServiceAccount";
        ACLListResponse.Data acl1 = new ACLListResponse.Data();
        acl1.setOperation("READ");
        acl1.setResourceName("topic1");
        ACLListResponse.Data acl2 = new ACLListResponse.Data();
        acl2.setOperation("WRITE");
        acl2.setResourceName("topic2");
        ACLListResponse.Data acl3 = new ACLListResponse.Data();
        acl3.setOperation("READ");
        acl3.setResourceName("topic3");
        ACLListResponse.Data acl4 = new ACLListResponse.Data();
        acl4.setOperation("WRITE");
        acl4.setResourceName("topic4");
        ACLListResponse aclListResponse = new ACLListResponse();
        List<ACLListResponse.Data> aclList = new ArrayList<>();
        aclList.add(acl1);
        aclList.add(acl2);
        aclList.add(acl3);
        aclList.add(acl4);
        aclListResponse.setData(aclList);
        try (MockedConstruction<ConfluentCloudClient> cloudClientMockedConstruction = mockConstruction(ConfluentCloudClient.class, (mock, context) -> {
            when(mock.getACLsForServiceAccount(serviceAccountName, envName)).thenReturn(aclListResponse);
        })) {
            TopicService topicService = new TopicService(new ConfluentCloudClient(null));
            AggregatedTopics topicsForServiceAccount = topicService.getTopicsForServiceAccount(serviceAccountName, envName);

            Assertions.assertEquals(2, topicsForServiceAccount.getConsumed().size());
            Assertions.assertEquals(2, topicsForServiceAccount.getProduced().size());
            Assertions.assertEquals(aclList.stream().filter(data -> data.getOperation().equalsIgnoreCase("READ"))
                            .map(ACLListResponse.Data::getResourceName).toList(), topicsForServiceAccount.getConsumed());
            Assertions.assertEquals(aclList.stream().filter(data -> data.getOperation().equalsIgnoreCase("WRITE"))
                            .map(ACLListResponse.Data::getResourceName).toList(), topicsForServiceAccount.getProduced());
        }
    }

    @Test
    void getTopicsForServiceAccountTestWIthNoTopics() {
        String envName = "testEnv";
        String serviceAccountName = "testServiceAccount";
        ACLListResponse aclListResponse = new ACLListResponse();
        List<ACLListResponse.Data> aclList = new ArrayList<>();
        aclListResponse.setData(aclList);
        try (MockedConstruction<ConfluentCloudClient> cloudClientMockedConstruction = mockConstruction(ConfluentCloudClient.class, (mock, context) -> {
            when(mock.getACLsForServiceAccount(serviceAccountName, envName)).thenReturn(aclListResponse);
        })) {
            TopicService topicService = new TopicService(new ConfluentCloudClient(null));
            AggregatedTopics topicsForServiceAccount = topicService.getTopicsForServiceAccount(serviceAccountName, envName);

            Assertions.assertEquals(0, topicsForServiceAccount.getConsumed().size());
            Assertions.assertEquals(0, topicsForServiceAccount.getProduced().size());
        }
    }
}
