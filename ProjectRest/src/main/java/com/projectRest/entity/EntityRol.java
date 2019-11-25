package com.projectRest.entity;


import com.projectRest.model.Rol;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "roluser")
public class EntityRol {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
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

    public EntityRol() {
    }
    public EntityRol(Rol rol) {
        this.name = rol.getName();
    }

    public EntityRol updateRol(Rol rol) {
        this.name = rol.getNameChange().equals(this.name) ? this.name :rol.getNameChange()  ;
        return this;
    }
    public EntityRol(String name) {
        this.name = name;
    }

    public List<EntityUserApp> getAppArrayList() {
        return appArrayList;
    }

    public void setAppArrayList(List<EntityUserApp> appArrayList) {
        this.appArrayList = appArrayList;
    }
}
