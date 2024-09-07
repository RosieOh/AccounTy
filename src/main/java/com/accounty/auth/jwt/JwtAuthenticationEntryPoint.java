package com.accounty.auth.jwt;

import com.accounty.global.GlobalApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Log4j2
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        setResponse(response);
    }

    private void setResponse(HttpServletResponse response) throws IOException {
        log.error("인증 에러");

        response.setStatus(403);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        ObjectMapper objectMapper = new ObjectMapper();
        GlobalApiResponse<Object> globalApiResponse = GlobalApiResponse.builder()
                .message("인증 정보가 없습니다.")
                .status(403)
                .build();
        response.getWriter().write(objectMapper.writeValueAsString(globalApiResponse));
    }
}
