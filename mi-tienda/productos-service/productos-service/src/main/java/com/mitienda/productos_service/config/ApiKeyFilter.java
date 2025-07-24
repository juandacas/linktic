package com.mitienda.productos_service.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApiKeyFilter implements Filter{
    private static final String API_KEY_HEADER = "x-api-key";
    private static final String VALID_API_KEY = "clave-secreta";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String apiKey = req.getHeader(API_KEY_HEADER);

        if (!VALID_API_KEY.equals(apiKey)) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "API Key inválida");
            return;
        }

        chain.doFilter(request, response);
    }
}
