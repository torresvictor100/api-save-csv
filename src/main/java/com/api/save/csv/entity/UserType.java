package com.api.save.csv.entity;

public enum UserType {
    ADMIN("admin");

    private String type;

    UserType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
