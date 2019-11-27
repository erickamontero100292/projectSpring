package com.projectRest.model;

public class RoleRequest {


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

    public RoleRequest() {
    }


    public RoleRequest(String name, String nameChange) {
        this.name = name;
        this.nameChange = nameChange;
    }

    public boolean isEmptyName(Role role) {
        if (role == null || role.getName() == null || role.getName().isEmpty()) {
            return true;
        }
        return false;
    }
}
