package com.shine2share.middleware;

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

//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class HttpLoggingFilter extends OncePerRequestFilter {
    protected static final Logger logger = LoggerFactory.getLogger(HttpLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        logger.debug("HttpLoggingFilter[HttpServletRequest]: " + httpServletRequest.getRequestURI());
        logger.debug("HttpLoggingFilter[HttpServletRequest]: " + httpServletRequest.getServletPath());
        logger.debug("HttpLoggingFilter[HttpServletRequest]: " + httpServletRequest.getAuthType());
        logger.debug("HttpLoggingFilter[HttpServletRequest]: " + httpServletRequest.getPathInfo());
        logger.debug("HttpLoggingFilter[HttpServletRequest]: " + httpServletRequest.getQueryString());
        logger.debug("HttpLoggingFilter[HttpServletRequest]: " + httpServletRequest.getContentType());
        logger.debug("HttpLoggingFilter[HttpServletRequest]: " + httpServletRequest.getLocalAddr());
        logger.debug("HttpLoggingFilter[HttpServletRequest]: " + httpServletRequest.getUserPrincipal());
        logger.debug("HttpLoggingFilter[HttpServletResponse]: " + httpServletResponse);
        logger.debug("HttpLoggingFilter[HttpServletResponse]: " + httpServletResponse.getContentType());
        logger.debug("HttpLoggingFilter[HttpServletResponse]: " + httpServletResponse.getStatus());
        logger.debug("HttpLoggingFilter[FilterChain]: " + filterChain);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
