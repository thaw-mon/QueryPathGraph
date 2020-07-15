package com.database.tool.Service.Schemata.DocumentModel;

import java.util.ArrayList;
import java.util.List;

//add index info
public class DocumentScalarPropertyExtendIndex {

    DocumentScalarProperty documentScalarProperty;
    List<String> indexes;

    public DocumentScalarPropertyExtendIndex(DocumentScalarProperty documentScalarProperty) {
        this.documentScalarProperty = documentScalarProperty;
        indexes = new ArrayList<>();
    }

    public DocumentScalarProperty getDocumentScalarProperty() {
        return documentScalarProperty;
    }

    public void setDocumentScalarProperty(DocumentScalarProperty documentScalarProperty) {
        this.documentScalarProperty = documentScalarProperty;
    }

    public List<String> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<String> indexes) {
        this.indexes = indexes;
    }

    @Override
    public String toString() {
        return "DocumentScalarPropertyExtendIndex{" +
                "documentScalarProperty=" + documentScalarProperty +
                ", indexes=" + indexes +
                '}';
    }
}
