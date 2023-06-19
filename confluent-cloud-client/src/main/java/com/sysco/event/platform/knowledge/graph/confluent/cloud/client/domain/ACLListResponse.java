package com.sysco.event.platform.knowledge.graph.confluent.cloud.client.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ACLListResponse {

    private List<ACLListResponse.Data> data;

    public List<ACLListResponse.Data> getData() {
        return this.data;
    }

    public void setData(List<ACLListResponse.Data> data) {
        this.data = data;
    }

    public static class Data {
        private String principal;

        @JsonProperty("strValcluster_id")
        private String clusterID;

        @JsonProperty("pattern_type")
        private String patternType;

        private String kind;

        @JsonProperty("resource_type")
        private String resourceType;

        private String host;

        private String permission;

        @JsonProperty("resource_name")
        private String resourceName;

        private String operation;

        public String getPrincipal() {
            return this.principal;
        }

        public void setPrincipal(String principal) {
            this.principal = principal;
        }

        public String getClusterID() {
            return this.clusterID;
        }

        public void setClusterID(String clusterID) {
            this.clusterID = clusterID;
        }

        public String getPatternType() {
            return this.patternType;
        }

        public void setPatternType(String patternType) {
            this.patternType = patternType;
        }

        public String getKind() {
            return this.kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public String getResourceType() {
            return this.resourceType;
        }

        public void setResourceType(String resourceType) {
            this.resourceType = resourceType;
        }

        public String getHost() {
            return this.host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getPermission() {
            return this.permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }

        public String getResourceName() {
            return this.resourceName;
        }

        public void setResourceName(String resourceName) {
            this.resourceName = resourceName;
        }

        public String getOperation() {
            return this.operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

    }
}


