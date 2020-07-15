package com.database.tool.Service.Schemata.DocumentModel;

import com.database.tool.Entity.WorkLoad.Property;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DocumentScalarProperty {
    String name;
    List<Property> property;
    List<DocumentScalarProperty> documentPropertyList; //Subnested arrays

    public DocumentScalarProperty(String str) {
        name = str;
        property = new ArrayList<>();
        documentPropertyList = new ArrayList<>();
    }

    //Converts a Java type to a Bson database type
    public Document generateDocument(int level) {
        Document document = new Document();
        if (level == 0)
            document.append("bsonType", "object");
        else
            document.append("bsonType", "array");
        //2.Add a String array : required
        List<String> required = new ArrayList<>();
        for (Property scalarProperty : property) {
            required.add(scalarProperty.getName());
        }
        //3.Add the object : properties
        Document documentProperties = new Document();
        for (Property scalarProperty : property) {
            documentProperties.append(scalarProperty.getName(), new Document().append("bsonType", getJsonType(scalarProperty.getType())));
        }
        //4.Add nested objects: properties
        for (DocumentScalarProperty documentScalarProperty : documentPropertyList) {
            documentProperties.append(documentScalarProperty.name, documentScalarProperty.generateDocument(level + 1));
        }

        //Decision data type
        if (document.get("bsonType").equals("array")) {
            Document items = new Document();
            items.append("bsonType", "object");
            items.append("required", required);
            items.append("properties", documentProperties);
            document.append("items", items);
        } else {
            document.append("required", required);
            document.append("properties", documentProperties);
        }

        return document;
    }

    public String getJsonType(String str) {
        if (String.class.getName().equals(str)) {
            return "string";
        } else if (int.class.getName().equals(str)) {
            return "int";
        } else if (List.class.getName().equals(str) || Set.class.getName().equals(str)) {
            return "array";
        }
        return "object";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Property> getProperty() {
        return property;
    }

    public void setProperty(List<Property> property) {
        this.property = property;
    }

    public List<DocumentScalarProperty> getDocumentPropertyList() {
        return documentPropertyList;
    }

    public void setDocumentPropertyList(List<DocumentScalarProperty> documentPropertyList) {
        this.documentPropertyList = documentPropertyList;
    }

    @Override
    public String toString() {
        return "DocumentScalarProperty{" +
                "name='" + name + '\'' +
                ", property=" + property +
                ", documentPropertyList=" + documentPropertyList +
                '}';
    }
}
