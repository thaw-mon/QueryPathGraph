package com.database.tool.Entity.WorkLoad;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This definition of an edge in QPG
 */
public class Edge {
    String startNode;
    String endNode;

    String relationShip;
    char cardinality;
    List<Integer> queryNumbers;

    public Edge() {
        queryNumbers = new ArrayList<>();
    }

    public Edge(String start, String end) {
        startNode = start;
        endNode = end;
        queryNumbers = new ArrayList<>();
    }

    public Edge(String start, String end, String relation) {
        startNode = start;
        endNode = end;
        relationShip = relation;
        queryNumbers = new ArrayList<>();
    }


    public static void writeFile(String fileName, List<Edge> edges) {
        try {
            FileWriter fw = new FileWriter(fileName);
            for (Edge edge : edges) {
                fw.write(edge.toString());
                fw.write("\r\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addQueryNumber(int qNumber) {
        if (queryNumbers == null) {
            queryNumbers = new ArrayList<>();
        }
        if (queryNumbers.contains(qNumber)) {
            return false;
        }
        queryNumbers.add(qNumber);
        return true;
    }

    public String getStartNode() {
        return startNode;
    }

    public void setStartNode(String startNode) {
        this.startNode = startNode;
    }

    public String getEndNode() {
        return endNode;
    }

    public void setEndNode(String endNode) {
        this.endNode = endNode;
    }

    public String getRelationShip() {
        return relationShip;
    }

    public void setRelationShip(String relationShip) {
        this.relationShip = relationShip;
    }

    public char getCardinality() {
        return cardinality;
    }

    public void setCardinality(char cardinality) {
        this.cardinality = cardinality;
    }

    public List<Integer> getQueryNumbers() {
        return queryNumbers;
    }

    public void setQueryNumbers(List<Integer> queryNumbers) {
        this.queryNumbers = queryNumbers;
    }

    @Override
    public String toString() {
        String edgeIdentify = "<" + startNode + "," + endNode + ">/" + relationShip;

        return "Edge{" +
                "QPG edge=" + edgeIdentify +
                ", queryNumbers=" + queryNumbers +
                ", cardinality=" + cardinality +
                '}';
    }
}
