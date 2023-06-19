package com.sysco.event.platform.knowledge.graph.core.domain;

import java.util.List;

public class AggregatedServiceAccounts {
    private List<String> consumers;
    private List<String> producers;

    public AggregatedServiceAccounts(List<String> consumers, List<String> producers) {
        this.consumers = consumers;
        this.producers = producers;
    }

    public List<String> getConsumers() {
        return consumers;
    }

    public List<String> getProducers() {
        return producers;
    }
}
