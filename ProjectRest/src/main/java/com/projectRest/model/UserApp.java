package com.projectRest.model;

import com.projectRest.entity.EntityRole;
import com.projectRest.entity.EntityUserApp;

import java.util.Date;


public class UserApp {


    private Long id;
    private Date dateCreate;
    private String user;
    private String password;
    private EntityRole role;


    public UserApp(EntityUserApp userApp) {
        this.id = userApp.getId();
        this.dateCreate = userApp.getDateCreate();
        this.user = userApp.getUser();
        this.password = userApp.getPassword();

    }

    public UserApp(UserAppRequest requestEntity) {
        this.dateCreate = new Date();
        this.user = requestEntity.getUser();
        this.password = requestEntity.getPassword();

    }

    public UserApp(UserAppRequest requestEntity, EntityRole entityRole) {
        this.dateCreate = new Date();
        this.user = requestEntity.getUser();
        this.password = requestEntity.getPassword();
        this.role = entityRole;
    }


    public boolean emptyUser() {
        if (this.id == null)
            return true;
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
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

    public EntityRole getRole() {
        return role;
    }

    public void setRole(EntityRole role) {
        this.role = role;
    }
}