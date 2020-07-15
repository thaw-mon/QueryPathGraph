package com.database.tool.Service.Schemata.DocumentModel;

import com.database.tool.Entity.WorkLoad.Property;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DocumentScalarPropertyByRef {
    String name;
    List<Property> property;
    List<DocumentScalarPropertyByRef> docArray;
    List<DocRef> docRefs; //The reference data

    public DocumentScalarPropertyByRef(String name) {
        this.name = name;
        property = new ArrayList<>();
        docArray = new ArrayList<>();
        docRefs = new ArrayList<>();
    }

    public Document generateDocument(int level) {
        Document document = new Document();
        if (level == 0)
            document.append("bsonType", "object");
        else
            document.append("bsonType", "array");
        //2. required
        List<String> required = new ArrayList<>();
        for (Property scalarProperty : property) {
            required.add(scalarProperty.getName());
        }
        for (DocumentScalarPropertyByRef documentScalarPropertyByRef : docArray) {
            required.add(documentScalarPropertyByRef.getName());
        }
        for (DocRef docRef : docRefs) {
            required.add(docRef.getRefName());
        }

        //3.Add the object : properties
        Document documentProperties = new Document();
        for (Property scalarProperty : property) {
            documentProperties.append(scalarProperty.getName(), new Document().append("bsonType", getJsonType(scalarProperty.getType())));
        }
        //4.Add nested objects :  properties
        for (DocumentScalarPropertyByRef documentScalarProperty : docArray) {
            documentProperties.append(documentScalarProperty.name, documentScalarProperty.generateDocument(level + 1));
        }
        //5.Add a reference type attribute
        for (DocRef docRef : docRefs) {
            Document refDocument = new Document();
            refDocument.append("bsonType", "array");
            refDocument.append("items", new Document().append("bsonType", "objectId"));
            documentProperties.append(docRef.getRefName(), refDocument);

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
        return "object"; //
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

    public List<DocumentScalarPropertyByRef> getDocArray() {
        return docArray;
    }

    public void setDocArray(List<DocumentScalarPropertyByRef> docArray) {
        this.docArray = docArray;
    }

    public List<DocRef> getDocRefs() {
        return docRefs;
    }

    public void setDocRefs(List<DocRef> docRefs) {
        this.docRefs = docRefs;
    }

    @Override
    public String toString() {
        StringBuilder propertyStr = new StringBuilder();
        propertyStr.append("[");
        for (Property p : property) {
            propertyStr.append(p.getType()).append(" ").append(p.getName()).append(",");
        }
        if (!property.isEmpty()) propertyStr.deleteCharAt(propertyStr.length() - 1);
        propertyStr.append("]");
        return "{" +
                "name='" + name + '\'' +
                ", property=" + propertyStr.toString() +
                ", docArray=" + docArray +
                ", docRefs=" + docRefs +
                '}';
    }
}
