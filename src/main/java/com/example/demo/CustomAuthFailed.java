package com.example.demo;

import com.example.demo.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// AuthenticationEntryPoint, AuthenticationFailureHandler
@Component
public class CustomAuthFailed implements AuthenticationEntryPoint  {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException {
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please login first");
//        response.setContentType("application/json");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

//        ObjectMapper mapper = new ObjectMapper();
//        ApiResponse resp = new ApiResponse();
//        resp.setStatusCode(HttpServletResponse.SC_UNAUTHORIZED);
//        resp.setMessage("Authentication Failure-This user name and password combination is incc");
//        PrintWriter out = response.getWriter();
//        out.write(mapper.writeValueAsString(resp));
//        out.close();
        System.out.println("------------------CustomAuthFailed----------------------");
        //
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //response.getOutputStream().println("{ \"message\": \"Authorization Failure-This user does not have the sufficient level of\" }");

        ObjectMapper mapper = new ObjectMapper();
        ApiResponse resp = new ApiResponse();
        resp.setStatusCode(HttpServletResponse.SC_UNAUTHORIZED);
        resp.setMessage("Authentication: user is NOT login");
        PrintWriter out = response.getWriter();
        out.write(mapper.writeValueAsString(resp));
        out.close();

    }

    /*
//    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        System.out.println("------------------CustomAuthFailed----------------------");
        //
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //response.getOutputStream().println("{ \"message\": \"Authorization Failure-This user does not have the sufficient level of\" }");

        ObjectMapper mapper = new ObjectMapper();
        ApiResponse resp = new ApiResponse();
        resp.setStatusCode(HttpServletResponse.SC_UNAUTHORIZED);
        resp.setMessage("Authentication Failure-This user is NOT login");
        PrintWriter out = response.getWriter();
        out.write(mapper.writeValueAsString(resp));
        out.close();
    }
    */

}
