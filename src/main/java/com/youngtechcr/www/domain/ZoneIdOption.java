package com.youngtechcr.www.domain;

public enum ZoneIdOption {

    COSTA_RICA("America/Costa_Rica");

    private String id;
    ZoneIdOption(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
