package com.rest.pojo.nested;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;

// JsonInclude will ignore the Null fileds (id in this case) and not append it to the message body when sending the message
//@JsonInclude(JsonInclude.Include.NON_NULL) // ignore default Null values - good for string but not for integer
//@JsonInclude(JsonInclude.Include.NON_DEFAULT)  // ignore all default values
@JsonIgnoreProperties(value = {"id"}, allowSetters = true) // this will use the id parameter only for deserialization
public class Workspace {
    private String name;
    private String type;
    private String description;

    // These parameters are for annotation examples only
   // @JsonInclude(JsonInclude.Include.NON_NULL) // this will handle only the id parameter
    private String id;  // id of the response message so not required for serialization
    @JsonInclude(JsonInclude.Include.NON_DEFAULT) // this will handle only the i parameter
    private int i; // default value of integer is 0, so needed the annotation to ignore NON_DEFAULT values
    //@JsonInclude(JsonInclude.Include.NON_EMPTY ) // NON_EMPTY includes the NON_NULL as well
    @JsonIgnore // this will ignore the parameter during both serialization and de-serialization
    private HashMap<String, String> myHashMap; // The empty hashmap needs to be ignored and not added to the request body

    // Jackson needs the default constructor for the de-serialization
    public Workspace(){

    }

    public Workspace(String name, String type, String description){
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMyHashMap(HashMap<String, String> myHashMap) {
    }
}

