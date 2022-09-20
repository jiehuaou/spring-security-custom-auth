package com.example.demo;

import com.example.demo.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException {
//        httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "you are not allow access");
        System.out.println("------------------CustomDeny----------------------");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        //response.getOutputStream().println("{ \"message\": \"Authorization Failure-This user does not have the sufficient level of\" }");

        ObjectMapper mapper = new ObjectMapper();
        ApiResponse resp = new ApiResponse();
        resp.setStatusCode(HttpServletResponse.SC_FORBIDDEN);
        resp.setMessage("Authorization: user does not have ACCESS");
        PrintWriter out = response.getWriter();
        out.write(mapper.writeValueAsString(resp));
        out.close();
    }
}
