package com.sysco.event.platform.knowledge.graph.core.controller;

import com.sysco.event.platform.knowledge.graph.core.domain.AggregatedServiceAccounts;
import com.sysco.event.platform.knowledge.graph.core.service.ServiceAccountService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

import java.security.Principal;

import static com.sysco.event.platform.knowledge.graph.core.auditlog.AuditLog.auditLogFailed;
import static com.sysco.event.platform.knowledge.graph.core.auditlog.AuditLog.auditLogSuccess;

@Controller
public class ServiceAccountController {

    private final ServiceAccountService serviceAccountService;

    public ServiceAccountController(ServiceAccountService serviceAccountService) {
        this.serviceAccountService = serviceAccountService;
    }

    @Secured("ROLE_QUERY_USER")
    @QueryMapping
    public AggregatedServiceAccounts getServiceAccountsForTopic(Principal principal, @Argument String topicName, @Argument String confluentEnvironment) {
        try {
            AggregatedServiceAccounts serviceAccountsForTopic = serviceAccountService.getServiceAccountsForTopic(topicName, confluentEnvironment);

            auditLogSuccess(principal, "GET_SERVICE_ACCOUNTS_FOR_TOPICS", topicName, confluentEnvironment);

            return serviceAccountsForTopic;
        } catch (Exception e) {
            auditLogFailed(principal, "GET_SERVICE_ACCOUNTS_FOR_TOPICS", topicName, confluentEnvironment);
            throw e;
        }
    }
}
