package com.example.demo.dto;

public class ApiResponse {
    private int statusCode;
    public int getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    private String message;
    public ApiResponse() {

    }
    public ApiResponse(int statusCode,String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
