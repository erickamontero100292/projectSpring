package com.projectRest.model;

import com.projectRest.entity.EntityRole;
import com.projectRest.entity.EntityUserApp;

import java.util.ArrayList;
import java.util.List;

public class Role {


    private Long id;
    private String name;
    private String nameChange;
    private List<EntityUserApp> appArrayList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameChange() {
        return nameChange;
    }

    public void setNameChange(String nameChange) {
        this.nameChange = nameChange;
    }

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public List<EntityUserApp> getAppArrayList() {
        return appArrayList;
    }

    public void setAppArrayList(List<EntityUserApp> appArrayList) {
        this.appArrayList = appArrayList;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role(EntityRole entityRole) {
        this.id = entityRole.getId();
        this.name = entityRole.getName();
    }

    public Role(RoleRequest roleRequest) {

        this.name = roleRequest.getName();
    }

    public boolean emptyRol(){
        if (this.id == null)
            return true;
        return false;
    }

    public boolean isEmptyName(Role role) {
        if (role == null || role.getName() == null || role.getName().isEmpty()) {
            return true;
        }
        return false;
    }
}
