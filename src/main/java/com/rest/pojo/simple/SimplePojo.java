package com.rest.pojo.simple;

public class SimplePojo {
    private String key1;
    private String key2;

    // Constructor
    public SimplePojo(){
    }

    // Constructor - overload
    public SimplePojo(String key1, String key2){
        this.key1 = key1;
        this.key2 = key2;
    }

    // Getter
    public String getKey1() {
        return key1;
    }

    // Setter
    public void setKey1(String key1) {
        this.key1 = key1;
    }

    // Getter
    public String getKey2() {
        return key2;
    }

    // Setter
    public void setKey2(String key2) {
        this.key2 = key2;
    }

}

