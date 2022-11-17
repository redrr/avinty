package com.avinty.hr.modules.employee;

public enum Role {

    ROLE_ADMIN, ROLE_USER;

    public static String[] all() {
        return new String[]{ROLE_ADMIN.name(), ROLE_USER.name()};
    }
}
