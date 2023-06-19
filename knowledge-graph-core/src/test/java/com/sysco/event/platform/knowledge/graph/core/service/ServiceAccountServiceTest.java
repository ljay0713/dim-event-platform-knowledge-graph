package com.sysco.event.platform.knowledge.graph.core.service;

import com.sysco.event.platform.knowledge.graph.confluent.cloud.client.domain.ACLListResponse;
import com.sysco.event.platform.knowledge.graph.confluent.cloud.client.services.ConfluentCloudClient;
import com.sysco.event.platform.knowledge.graph.core.domain.AggregatedServiceAccounts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

class ServiceAccountServiceTest {

    @Test
    void getServiceAccountForTopicTest() {
        String envName = "testEnv";
        String topicName = "testTopic";
        ACLListResponse.Data acl1 = new ACLListResponse.Data();
        acl1.setOperation("READ");
        acl1.setPrincipal("sa_01");
        ACLListResponse.Data acl2 = new ACLListResponse.Data();
        acl2.setOperation("WRITE");
        acl1.setPrincipal("sa_02");
        ACLListResponse.Data acl3 = new ACLListResponse.Data();
        acl3.setOperation("READ");
        acl1.setPrincipal("sa_03");
        ACLListResponse.Data acl4 = new ACLListResponse.Data();
        acl4.setOperation("WRITE");
        acl1.setPrincipal("sa_04");
        ACLListResponse aclListResponse = new ACLListResponse();
        List<ACLListResponse.Data> aclList = new ArrayList<>();
        aclList.add(acl1);
        aclList.add(acl2);
        aclList.add(acl3);
        aclList.add(acl4);
        aclListResponse.setData(aclList);
        try (MockedConstruction<ConfluentCloudClient> cloudClientMockedConstruction = mockConstruction(ConfluentCloudClient.class, (mock, context) -> {
            when(mock.getALCsForTopic(topicName, envName)).thenReturn(aclListResponse);
        })) {
            ServiceAccountService topicService = new ServiceAccountService(new ConfluentCloudClient(null));
            AggregatedServiceAccounts serviceAccountsForTopic = topicService.getServiceAccountsForTopic(topicName, envName);

            Assertions.assertEquals(2, serviceAccountsForTopic.getConsumers().size());
            Assertions.assertEquals(2, serviceAccountsForTopic.getProducers().size());
            Assertions.assertEquals(aclList.stream().filter(data -> data.getOperation().equalsIgnoreCase("READ"))
                            .map(ACLListResponse.Data::getPrincipal).toList(), serviceAccountsForTopic.getConsumers());
            Assertions.assertEquals(aclList.stream().filter(data -> data.getOperation().equalsIgnoreCase("WRITE"))
                            .map(ACLListResponse.Data::getPrincipal).toList(), serviceAccountsForTopic.getProducers());
        }
    }

    @Test
    void getServiceAccountsForTopicTestWIthNoServiceAccounts() {
        String envName = "testEnv";
        String topicName = "testTopic";
        ACLListResponse aclListResponse = new ACLListResponse();
        List<ACLListResponse.Data> aclList = new ArrayList<>();
        aclListResponse.setData(aclList);
        try (MockedConstruction<ConfluentCloudClient> cloudClientMockedConstruction = mockConstruction(ConfluentCloudClient.class, (mock, context) -> {
            when(mock.getALCsForTopic(topicName, envName)).thenReturn(aclListResponse);
        })) {
            ServiceAccountService topicService = new ServiceAccountService(new ConfluentCloudClient(null));
            AggregatedServiceAccounts serviceAccountsForTopic = topicService.getServiceAccountsForTopic(topicName, envName);

            Assertions.assertEquals(0, serviceAccountsForTopic.getConsumers().size());
            Assertions.assertEquals(0, serviceAccountsForTopic.getProducers().size());
        }
    }
}
