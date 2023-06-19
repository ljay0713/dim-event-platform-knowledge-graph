package com.sysco.event.platform.knowledge.graph.core.controller;

import com.sysco.event.platform.knowledge.graph.core.domain.AggregatedTopics;
import com.sysco.event.platform.knowledge.graph.core.service.TopicService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

import java.security.Principal;

import static com.sysco.event.platform.knowledge.graph.core.auditlog.AuditLog.auditLogFailed;
import static com.sysco.event.platform.knowledge.graph.core.auditlog.AuditLog.auditLogSuccess;

@Controller
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @Secured("ROLE_QUERY_USER")
    @QueryMapping
    public AggregatedTopics getTopicsForServiceAccount(Principal principal, @Argument String serviceAccountID, @Argument String confluentEnvironment) {
        try {
            AggregatedTopics topicsForServiceAccount = topicService.getTopicsForServiceAccount(serviceAccountID, confluentEnvironment);

            auditLogSuccess(principal, "GET_TOPICS_FOR_SERVICE_ACCOUNT", serviceAccountID, confluentEnvironment);

            return topicsForServiceAccount;
        } catch (Exception e) {
            auditLogFailed(principal, "GET_TOPICS_FOR_SERVICE_ACCOUNT", serviceAccountID, confluentEnvironment);
            throw e;
        }
    }
}
