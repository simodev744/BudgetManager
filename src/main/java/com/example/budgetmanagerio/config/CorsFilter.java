package com.example.budgetmanagerio.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.web.servlet.function.RequestPredicates.methods;

public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        httpResp.setHeader("Access-Control-Allow-Origin", "*"); // Replace with the allowed origins
        httpResp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE"); // Replace with the allowed
        methods (GET, POST,PUT,DELETE);
        httpResp.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Accept, Authorization"); // Replace


        if ("OPTIONS".equalsIgnoreCase(httpReq.getMethod())) {
            httpResp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}