package com.example.seguranca.enumeration;

public enum ProfileEnum {

    ROOT("ROLE_ROOT"),
    ADMIN("ROLE_ADMIN"),
    EDITOR("ROLE_EDITOR"),
    VISITOR("ROLE_VISITOR");

    private String value;

    ProfileEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
