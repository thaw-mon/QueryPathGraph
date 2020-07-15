package com.database.tool.Service;

import com.database.tool.Entity.QPG.Graph;
import com.database.tool.Entity.WorkLoad.*;
import com.database.tool.Service.Schemata.ColumnFamilyModel.ColumnFamilyScalarProperty;
import com.database.tool.Service.Schemata.ColumnFamilyModel.QPGtoColumnFamily;
import com.database.tool.Service.Schemata.DocumentModel.DocumentScalarPropertyByRef;
import com.database.tool.Service.Schemata.DocumentModel.QPGtoDocumentSchemataWithRef;
import com.database.tool.Service.Schemata.DocumentModel.QPGtoDocumentSchemataWithRef2;
import com.database.tool.Service.Schemata.KeyValueModel.KeyValueProperty;
import com.database.tool.Service.Schemata.KeyValueModel.QPGtoKV;
import com.database.tool.Entity.WorkLoad.Input;
import com.database.tool.Entity.QPG.QueryPathInfo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * A given query load information returns QPG information
 */
public class DatabaseGenerator {
    //1.readFile
    private String path;
    private String fileName;

    //Input file class
    private Input input;
    //QPG info
    private QueryPathInfo QPGInfo;
    private Graph graph;
    //Schemata Info
    // CF model
    public List<ColumnFamilyScalarProperty> columnFamilyScalarPropertyList;
    // Document model
    public List<DocumentScalarPropertyByRef> documentScalarPropertyList;
    // KV model
    public List<KeyValueProperty> keyValuePropertyList;

    public DatabaseGenerator(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
    }

    //1.Converts the file to the corresponding base class
    public Input initInputInfo() {
        input = new Input(path, fileName);
        input.transferFileToClass();
        return input;
    }

    //2.Convert the input class to QPG Info
    public void convertToQPGInfo() {
        if (input == null) {
            initInputInfo();
        }
        QPGInfo = new QueryPathInfo();
        QPGInfo.initQPG(input);
    }

    //3.Convert to QPG Graph according to QPG Info
    public void convertToQueryPathGraph() {
        if (QPGInfo == null) {
            convertToQPGInfo();
        }
        graph = new Graph();
        graph.init(QPGInfo.getNodeList(), QPGInfo.getEdgeList(), QPGInfo.getQueryKeysEntityList());
    }


    //4.Create Schemata based on graph
    public void createSchemata(int type) {
        if (graph == null) {
            convertToQueryPathGraph();
        }
        switch (type) {
            case 0:
                QPGtoKV qpGtoKV = new QPGtoKV(graph);
                qpGtoKV.generateSchemata();
                keyValuePropertyList = qpGtoKV.getSchemataInfo();
                break;
            case 1:
                QPGtoColumnFamily qpGtoColumnFamily = new QPGtoColumnFamily(graph);
                qpGtoColumnFamily.generateSchemata();
                columnFamilyScalarPropertyList = qpGtoColumnFamily.getSchemataInfo();
                break;
            case 2:
                QPGtoDocumentSchemataWithRef2 qpGtoDocumentSchemata = new QPGtoDocumentSchemataWithRef2(graph);
                qpGtoDocumentSchemata.generateSchemata();
                documentScalarPropertyList = qpGtoDocumentSchemata.getSchemataInfo();
                break;
            default:
                //do nothing
                break;
        }

    }

    //Print input class information
    public void printInputInfo() {
        System.out.println("entity :");
        for (ModelEntity modelEntity : input.getModelEntities()) {
            System.out.println(modelEntity);
        }
        System.out.println("query :");
        for (QueryWorkload queryWorkload : input.getQueryWorkloads()) {
            System.out.println(queryWorkload);
        }
    }


    //print QPG base Info
    public void printQPGInfo() {
        System.out.println("nodes : ");
        for (Node node : QPGInfo.getNodeList())
            System.out.println(node);
        System.out.println("edges : ");
        for (Edge edge : QPGInfo.getEdgeList())
            System.out.println(edge);
        System.out.println("specification : ");
        for (QueryKeysEntity queryKeysEntity : QPGInfo.getQueryKeysEntityList())
            System.out.println(queryKeysEntity);

    }

}
