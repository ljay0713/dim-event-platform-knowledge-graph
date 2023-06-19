package com.sysco.event.platform.knowledge.graph.core.domain;

import java.util.List;

public class AggregatedTopics {
    private List<String> consumed;
    private List<String> produced;

    public AggregatedTopics(List<String> consumed, List<String> produced) {
        this.consumed = consumed;
        this.produced = produced;
    }

    public List<String> getConsumed() {
        return consumed;
    }

    public List<String> getProduced() {
        return produced;
    }
}
