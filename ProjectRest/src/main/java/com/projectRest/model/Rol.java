package com.projectRest.model;

import com.projectRest.entity.EntityRol;
import com.projectRest.entity.EntityUserApp;

import java.util.ArrayList;
import java.util.List;

public class Rol {


    private Long id;
    private String name;
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

    public Rol() {
    }

    public Rol(String name) {
        this.name = name;
    }

    public List<EntityUserApp> getAppArrayList() {
        return appArrayList;
    }

    public void setAppArrayList(List<EntityUserApp> appArrayList) {
        this.appArrayList = appArrayList;
    }

    public Rol(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Rol(EntityRol entityRol) {
        this.id = entityRol.getId();
        this.name = entityRol.getName();
    }

    public boolean emptyRol(){
        if (this.id == null)
            return true;
        return false;
    }
}
