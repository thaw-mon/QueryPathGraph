package com.database.tool.Service.Schemata.DocumentModel;

import java.util.ArrayList;
import java.util.List;

//add index info
public class DocumentScalarPropertyByRefExtendIndex {
    DocumentScalarPropertyByRef documentScalarPropertyByRef;
    List<String> indexes;

    public DocumentScalarPropertyByRefExtendIndex(DocumentScalarPropertyByRef documentScalarPropertyByRef) {
        this.documentScalarPropertyByRef = documentScalarPropertyByRef;
        indexes = new ArrayList<>();
    }

    public DocumentScalarPropertyByRef getDocumentScalarPropertyByRef() {
        return documentScalarPropertyByRef;
    }

    public void setDocumentScalarPropertyByRef(DocumentScalarPropertyByRef documentScalarPropertyByRef) {
        this.documentScalarPropertyByRef = documentScalarPropertyByRef;
    }

    public List<String> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<String> indexes) {
        this.indexes = indexes;
    }

    @Override
    public String toString() {
        return "{" +
                "" + documentScalarPropertyByRef +
                ", indexes=" + indexes +
                '}';
    }
}
