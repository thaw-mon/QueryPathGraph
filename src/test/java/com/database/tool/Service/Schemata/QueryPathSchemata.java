package com.database.tool.Service.Schemata;

import com.database.tool.Service.DatabaseGenerator;
import com.database.tool.Service.Schemata.ColumnFamilyModel.ColumnFamilyScalarProperty;
import com.database.tool.Service.Schemata.DocumentModel.DocumentScalarPropertyByRef;
import com.database.tool.Service.Schemata.KeyValueModel.KeyValueProperty;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Test the generated schema Schemata
 */
public class QueryPathSchemata {
    String path = "src/main/resources/experiments";
    DatabaseGenerator databaseGenerator;

    @Test
    public void getCFSchemataByEAC() {
        String fileName = "EAC.info";
        databaseGenerator = new DatabaseGenerator(path, fileName);
        databaseGenerator.createSchemata(1);
        List<ColumnFamilyScalarProperty> columnFamilyScalarPropertyList = databaseGenerator.columnFamilyScalarPropertyList;
        for (ColumnFamilyScalarProperty columnFamilyScalarProperty : columnFamilyScalarPropertyList) {
            System.out.println(columnFamilyScalarProperty);
        }
    }

    @Test
    public void getCFSchemataTableByEAC() {
        String fileName = "EAC.info";
        databaseGenerator = new DatabaseGenerator(path, fileName);
        databaseGenerator.createSchemata(1);
        List<ColumnFamilyScalarProperty> columnFamilyScalarPropertyList = databaseGenerator.columnFamilyScalarPropertyList;
        for (ColumnFamilyScalarProperty columnFamilyScalarProperty : columnFamilyScalarPropertyList) {
            System.out.println(columnFamilyScalarProperty.generateColumnFamilySql());
        }
    }

    @Test
    public void getDocumentSchemataByOnlineStore() {
        String fileName = "OnlineStore.info";
        databaseGenerator = new DatabaseGenerator(path, fileName);
        databaseGenerator.createSchemata(2);
        List<DocumentScalarPropertyByRef> documentScalarPropertyList = databaseGenerator.documentScalarPropertyList;
        for (DocumentScalarPropertyByRef documentScalarProperty : documentScalarPropertyList) {
            System.out.println(documentScalarProperty);
        }
    }

    @Test
    public void getDocumentSchemataTableByOnlineStore() {
        String fileName = "OnlineStore.info";
        databaseGenerator = new DatabaseGenerator(path, fileName);
        databaseGenerator.createSchemata(2);
        List<DocumentScalarPropertyByRef> documentScalarPropertyList = databaseGenerator.documentScalarPropertyList;
        for (DocumentScalarPropertyByRef documentScalarProperty : documentScalarPropertyList) {
            System.out.println(documentScalarProperty.generateDocument(0));
        }
    }


    @Test
    public void getCFSchemataByDL() {
        String fileName = "DL.info";
        databaseGenerator = new DatabaseGenerator(path, fileName);
        databaseGenerator.createSchemata(1);
        List<ColumnFamilyScalarProperty> columnFamilyScalarPropertyList = databaseGenerator.columnFamilyScalarPropertyList;
        for (ColumnFamilyScalarProperty columnFamilyScalarProperty : columnFamilyScalarPropertyList) {
            System.out.println(columnFamilyScalarProperty);
        }
    }

    @Test
    public void getCFSchemataTableByDL() {
        String fileName = "DL.info";
        databaseGenerator = new DatabaseGenerator(path, fileName);
        databaseGenerator.createSchemata(1);
        List<ColumnFamilyScalarProperty> columnFamilyScalarPropertyList = databaseGenerator.columnFamilyScalarPropertyList;
        for (ColumnFamilyScalarProperty columnFamilyScalarProperty : columnFamilyScalarPropertyList) {
            System.out.println(columnFamilyScalarProperty.generateColumnFamilySql());
        }
    }

    @Test
    public void getKVSchemataByEAC() {
        String fileName = "EAC.info";
        databaseGenerator = new DatabaseGenerator(path, fileName);
        databaseGenerator.createSchemata(0);
        List<KeyValueProperty> keyValuePropertyList = databaseGenerator.keyValuePropertyList;
        for (KeyValueProperty keyValueProperty : keyValuePropertyList) {
            System.out.println(keyValueProperty);
        }
    }
}
