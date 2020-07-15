package com.database.tool.Service.Schemata.DocumentModel;

import java.util.ArrayList;
import java.util.List;

//add index info
public class DocumentPropertyExtendIndex {
    DocumentProperty documentProperty;
    List<String> indexes;

    public DocumentPropertyExtendIndex(DocumentProperty documentProperty) {
        this.documentProperty = documentProperty;
        indexes = new ArrayList<>();
    }

    public DocumentProperty getDocumentProperty() {
        return documentProperty;
    }

    public void setDocumentProperty(DocumentProperty documentProperty) {
        this.documentProperty = documentProperty;
    }

    public List<String> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<String> indexes) {
        this.indexes = indexes;
    }

    @Override
    public String toString() {
        return "DocumentPropertyExtendIndex{" +
                "documentProperty=" + documentProperty +
                ", indexes=" + indexes +
                '}';
    }
}
