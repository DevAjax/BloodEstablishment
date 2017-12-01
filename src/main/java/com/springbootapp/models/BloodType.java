package com.springbootapp.models;

public enum BloodType {
    ABRhPlus("ABRh+"),
    ABRhMinus("ABRh−"),
    ARhPlus("ARh+"),
    ARhMinus("ARh−"),
    BRhPlus("BRh+"),
    BRhMinus("BRh−"),
    ZeroRhPlus("0Rh+"),
    ZeroRhMinus("0Rh−");


    public String value;

    BloodType(String value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}