package com.u8.darts.utils;

import org.slf4j.Logger;
import org.springframework.web.context.request.WebRequest;
import javax.servlet.http.HttpServletRequest;


public class LoggingUtils {

    private LoggingUtils() {}

    public static void logTrace(Logger logger, HttpServletRequest request, Class clazz, String method) {
        logger.trace("{} Reached {}.{}()", logPrefix(request), clazz.getSimpleName(), method);
    }

    public static void logEndpointRequest(Logger logger, HttpServletRequest request, String messagePrefix) {
        final String queryString = (request.getQueryString() != null) ? "?" + request.getQueryString() : "";
        logger.info("{} {} {} {}{}", logPrefix(request), messagePrefix, request.getMethod(), request.getPathInfo(), queryString);
    }

    public static String logPrefix(WebRequest request) {
        String uuid = (String) request.getAttribute("request_id", 0);
        String remoteHost = (String) request.getAttribute("request_host", 0);
        return logPrefix(uuid, remoteHost);
    }

    public static String logPrefix(HttpServletRequest request) {
        String uuid = (String) request.getAttribute("request_id");
        String remoteHost = (String) request.getAttribute("request_host");
        return logPrefix(uuid, remoteHost);
    }

    private static String logPrefix(String uuid, String remoteHost) {
        return "[" + uuid + "|" + remoteHost + "|" + Thread.currentThread().getId() + "]";
    }
}
