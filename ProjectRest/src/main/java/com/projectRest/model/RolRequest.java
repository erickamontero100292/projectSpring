package com.projectRest.model;

public class RolRequest {


    private String name;
    private String nameChange;


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

    public RolRequest() {
    }


    public RolRequest(String name, String nameChange) {
        this.name = name;
        this.nameChange = nameChange;
    }

    public boolean isEmptyName(Rol rol) {
        if (rol == null || rol.getName() == null || rol.getName().isEmpty()) {
            return true;
        }
        return false;
    }
}
