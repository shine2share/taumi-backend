package com.shine2share.core.middleware;

import com.shine2share.common.CommonUtil;
import com.shine2share.common.utils.KafkaUtils;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HttpLoggingFilter extends OncePerRequestFilter {
    protected static final Logger logger = LoggerFactory.getLogger(HttpLoggingFilter.class);
    private final KafkaUtils kafkaUtils;

    public HttpLoggingFilter(KafkaUtils kafkaUtils) {
        this.kafkaUtils = kafkaUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        Map<String, String> headers = getRequestHeader(httpServletRequest);
        String uuid;
        String clientMessageId = headers.get("clientMessageId");
        if (CommonUtil.isNullOrEmpty(clientMessageId)) {
            uuid = UUID.randomUUID().toString();
        } else {
            ThreadContext.remove("clientMessageId");
            ThreadContext.put("clientMessageId", headers.get("clientMessageId"));
            uuid = clientMessageId;
        }
        ThreadContext.put("clientMessageId", uuid);
        ThreadContext.put("startTime", String.valueOf(start));
        ThreadContext.put("path", httpServletRequest.getRequestURI());
        logger.info("before do filter");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        logger.info("after do filter");
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        logger.info(String.format("Time elapsed: uri=%s, id=%s, time=%s ms", httpServletRequest.getRequestURI(), uuid, timeElapsed));
        HttpInfo info = new HttpInfo();
        info.setRequestId(uuid);
        info.setDurationTime(timeElapsed);
        if (isLogRequest(httpServletRequest)) {
            //logInfo2Db(info);
        }
    }
    private boolean isLogRequest(HttpServletRequest request) {
        String url = request.getRequestURI();
        String method = request.getMethod();
        if (!("GET".equals(method) || "POST".equals(method) || "PUT".equals(method) || "DELETE".equals(method))) {
            return false;
        }
        if (url.contains("actuator") || url.contains("swagger") || url.contains("api-docs") || url.contains("csrf") || url.contains("/css/") || url.contains("/js/")) {
            return false;
        }
        return true;
    }

    /**
     * send to kafka to write log to db
     * @param httpInfo
     */
    private void logInfo2Db(HttpInfo httpInfo) {
        String message = CommonUtil.beanToString(httpInfo);
        kafkaUtils.sendMessage("taumi-log", message);
    }
    private Map<String, String> getRequestHeader(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Collections.list(request.getHeaderNames()).forEach(key -> headers.put(key, request.getHeader(key)));
        return headers;
    }
}
