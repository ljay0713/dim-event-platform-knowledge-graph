package com.sysco.event.platform.knowledge.graph.core.exception;

public abstract class KnowledgeGraphBaseException extends RuntimeException{

    private ErrorCode errorCode;

    public KnowledgeGraphBaseException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
