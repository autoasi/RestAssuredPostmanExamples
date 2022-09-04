package com.rest.pojo.complex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // ignore any unknown properties in response message body
public class Body {
    private String mode;
    private String raw;

    public Body(){

    }

    public Body(String mode, String raw) {
        this.mode = mode;
        this.raw = raw;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }


}
