package com.brilworks.accounts.role_permissions.enums;

public enum DefaultRole {
    ROLE_ADMIN_EMP("ADMIN","Acess to all modules of emp"),
    ROLE_ADMIN_OKR("ADMIN","Acess to all modules of okr"),
    ROLE_ADMIN_TASK("ADMIN","Acess to all modules of task"),
    ROLE_USER_EMP("USER","Acess to all modules of emp"),
    ROLE_USER_OKR("USER","Acess to all modules of okr"),
    ROLE_USER_TASK("UESR","Acess to all modules of task");

    private String displayName;
    private String description;

    DefaultRole(String name, String desc) {
        setDisplayName(name);
        setDescription(desc);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
