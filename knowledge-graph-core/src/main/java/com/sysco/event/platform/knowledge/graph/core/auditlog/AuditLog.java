package com.sysco.event.platform.knowledge.graph.core.auditlog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.Principal;

public class AuditLog {

    private AuditLog() {
    }

    private static final Logger LOGGER = LogManager.getLogger("AUDIT_LOG");

//    public static <R> R auditLog(String ref, Callable<R> func, String... params) throws Exception {
//        try {
//            R response = func.call();
//            LOGGER.info("{}|SUCCESS|params:[{}]", ref, params);
//            return response;
//        } catch (Exception e) {
//            LOGGER.info("{}|FAILED|params:[{}]", ref, params);
//            throw e;
//        }
//    }


    public static void auditLogSuccess(Principal principal, String ref, String... params) {
        LOGGER.info("{}|{}|SUCCESS|params:[{}]", principal.getName(), ref, params);
    }

    public static void auditLogFailed(Principal principal, String ref, String... params) {
        LOGGER.info("{}|{}|FAILED|params:[{}]", principal.getName(), ref, params);
    }
}
