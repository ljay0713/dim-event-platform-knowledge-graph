package com.sysco.event.platform.knowledge.graph.core.exception;

public class InvalidParameters extends KnowledgeGraphBaseException {
    public InvalidParameters(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
