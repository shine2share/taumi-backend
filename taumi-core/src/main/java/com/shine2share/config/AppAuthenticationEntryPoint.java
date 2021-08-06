package com.shine2share.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shine2share.base.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Component
public class AppAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        ResponseData<?> responseData = new ResponseData<>();
        if (e.getMessage().startsWith("Access token expired:")) {
            String str = "\u0054\u006f\u006b\u0065\u006e \u0111\u00e3 \u0068\u1ebf\u0074 \u0068\u1ea1\u006e\u002c \u0076\u0075\u0069 \u006c\u00f2\u006e\u0067 \u0074\u0068\u1eed \u006c\u1ea1\u0069\u0021";
            byte[] charset = str.getBytes(StandardCharsets.UTF_8);
            String result = new String(charset, StandardCharsets.UTF_8);
            responseData.error(1005,
                    "", result);
        } else {
            String str = "\u0054\u006f\u006b\u0065\u006e \u006b\u0068\u00f4\u006e\u0067 \u0111\u00fa\u006e\u0067\u002c \u0076\u0075\u0069 \u006c\u00f2\u006e\u0067 \u0074\u0068\u1eed \u006c\u1ea1\u0069\u0021";
            byte[] charset = str.getBytes(StandardCharsets.UTF_8);
            String result = new String(charset, StandardCharsets.UTF_8);
            responseData.error(1004,
                    "", result);
        }
        PrintWriter writer = httpServletResponse.getWriter();
        writer.println(objectMapper.writeValueAsString(responseData));
    }
}
