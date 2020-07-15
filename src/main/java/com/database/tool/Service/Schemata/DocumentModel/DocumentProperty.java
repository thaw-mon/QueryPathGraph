package com.database.tool.Service.Schemata.DocumentModel;

import java.util.ArrayList;
import java.util.List;

public class DocumentProperty {
    String name;
    List<String> property;
    List<DocumentProperty> documentPropertyList; //Subnested arrays

    public DocumentProperty(String str){
        name = str;
        property = new ArrayList<>();
        documentPropertyList = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getProperty() {
        return property;
    }

    public void setProperty(List<String> property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "DocumentProperty{" +
                "name='" + name + '\'' +
                ", property=" + property +
                ", documentPropertyList=" + documentPropertyList.toString() +
                '}';
    }
}
