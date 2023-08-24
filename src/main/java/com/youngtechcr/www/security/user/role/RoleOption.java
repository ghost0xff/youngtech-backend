package com.youngtechcr.www.security.user.role;

public enum RoleOption {

    ADMIN("ROLE_ADMIN"),
    EMPLOYEE("ROLE_EMPLOYEE"),
    CUSTOMER("ROLE_CUSTOMER"),
    USER("ROLE_USER"),
    ;

    private String identifier;
    RoleOption(String identifier) {
       this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
