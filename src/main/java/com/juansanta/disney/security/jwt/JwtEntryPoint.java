package com.juansanta.disney.security.jwt;

import com.juansanta.disney.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Checks if a token exists if not returns an unauthorized 401
 */
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    // We implement a logger to see which method gives an error in case of failure
    private final static Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

    // Implemented method of AuthenticationEntryPoint
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        logger.error("Fallo el metodo commence");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No esta autorizado");
    }
}
