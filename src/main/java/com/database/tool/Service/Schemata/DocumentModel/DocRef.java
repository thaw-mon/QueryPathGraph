package com.database.tool.Service.Schemata.DocumentModel;

import com.database.tool.Entity.WorkLoad.Property;

import java.util.List;

public class DocRef {
    Boolean isArray;
    String refName;
    String collectionName;

    // add Property
    List<Property> property;

    DocRef() {

    }

    DocRef(String refName) {
        this.refName = refName;
    }

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    public Boolean getArray() {
        return isArray;
    }

    public void setArray(Boolean array) {
        isArray = array;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    @Override
    public String toString() {
        return "{" +
                "refName='" + refName + '\'' +
                ", collectionName='" + collectionName + '\'' +
                '}';
    }
}
