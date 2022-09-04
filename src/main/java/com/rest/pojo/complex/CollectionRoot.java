package com.rest.pojo.complex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // ignore any unknown properties in response message body
public class CollectionRoot {
    Collection collection;

    public CollectionRoot(){

    }

    public CollectionRoot(Collection collection) {
        this.collection = collection;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }
}
