package com.database.tool.Service.Schemata;

import com.database.tool.Entity.QPG.Graph;

public class QPGToSchemata {
    public Graph graph;
    public QPGToSchemata(Graph g) {
        graph = g;
    }


    public void generateSchemata() {
        System.out.println("QPGToSchemata class");
    }

    public void printSchemataInfo(){

    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }
}
