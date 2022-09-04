package com.rest.pojo.complex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) // ignore any unknown properties in response message body
public class Request {
    //private String url;
    private Object url; // Change url type from String to Object to support differences between request and response bodies
    private String method;
    List<Header> header;
    Body body;
    private String description;

    public Request(){

    }

    public Request(Object url, String method, List<Header> header, Body body, String description) {
        this.url = url;
        this.method = method;
        this.header = header;
        this.body = body;
        this.description = description;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Header> getHeader() {
        return header;
    }

    public void setHeader(List<Header> header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
