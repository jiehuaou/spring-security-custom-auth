package com.example.demo;

import com.example.demo.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @RequestMapping("/course")
    public ResponseEntity<ApiResponse> adminCourse() {

        return new ResponseEntity(new ApiResponse(200, "Added by admin"), HttpStatus.OK);
    }

    @RequestMapping("/student")
    public ResponseEntity<ApiResponse> user() {
//        return "this is user";
        return new ResponseEntity(new ApiResponse(200, "Added by student"), HttpStatus.OK);
    }

    @RequestMapping("/other")
    public ResponseEntity<ApiResponse> other() {
        return new ResponseEntity(new ApiResponse(200, "Added by other"), HttpStatus.OK);
    }

    @RequestMapping("/hello")
    public ResponseEntity<ApiResponse> hello() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        if(auth!=null && auth instanceof AnonymousAuthenticationToken){

            System.out.println("when auth is anonymousUser, Principal Type is " + auth.getPrincipal().getClass());
            //return "hello " + auth.getPrincipal() ;
            return new ResponseEntity(new ApiResponse(200, "hello " + auth.getPrincipal()), HttpStatus.OK);
        }else{
            System.out.println(auth);
            if(auth.getPrincipal()!=null && auth.getPrincipal() instanceof User){
                User u = (User) auth.getPrincipal();
                System.out.println("This is Authenticated user: " + u.getUsername());
                //return "hello " + u.getUsername();
                return new ResponseEntity(new ApiResponse(200, "hello " + u.getUsername()), HttpStatus.OK);
            }
            //return "hello " + auth.getPrincipal() ;
            return new ResponseEntity(new ApiResponse(200, "hello " + auth.getPrincipal()), HttpStatus.OK);
        }
//        return "hello world";
    }
}
