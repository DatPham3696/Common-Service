package com.example.security_demo.domain.enums;

public enum LogInfor {
    LOGIN("user login"),
    UPDATE("user update information"),
    CHANGEPASSWORD("user change password"),
    RESETPASSWORD("user reset password"),
    IMPORTDATA("user import data from excel"),
    EXPORTDATA("user export data");
    private String description;
    LogInfor(String description){
        this.description = description;
    }
    public String getDescription(){
        return description;
    }
}
