package com.projectRest.model;

public class UserAppRequest {

    private String user;
    private String password;
    private String roleName;


    public UserAppRequest(String user, String password, String roleName) {
        this.user = user;
        this.password = password;
        this.roleName = roleName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}