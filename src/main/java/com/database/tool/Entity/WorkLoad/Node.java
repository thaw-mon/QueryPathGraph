package com.database.tool.Entity.WorkLoad;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * QPG node
 */
public class Node {
    String nodeName;
    List<Property> scalarProperties;
    boolean isAccessPoint;

    List<Integer> queryNumbers;
    List<String> outNeighbors;

    public Node(String name) {
        nodeName = name;
        scalarProperties = new ArrayList<>();
        queryNumbers = new ArrayList<>();
        isAccessPoint = false;
        outNeighbors = new ArrayList<>();
    }

    public List<String> getPrimaryKeys() {
        List<String> ret = new ArrayList<>();
        for (Property scalarProperty : scalarProperties) {
            if (scalarProperty.isPrimaryKey()) {
                ret.add(scalarProperty.getName());
            }
        }
        return ret;
    }

    public static void writeFile(String fileName, List<Node> nodes) {
        try {
            FileWriter fw = new FileWriter(fileName);
            for (Node node : nodes) {
                fw.write(node.toString());
                fw.write("\r\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public List<Property> getScalarProperties() {
        return scalarProperties;
    }

    public void setScalarProperties(List<Property> scalarProperties) {
        this.scalarProperties = scalarProperties;
    }

    public List<Integer> getQueryNumbers() {
        return queryNumbers;
    }

    public void setQueryNumbers(List<Integer> queryNumbers) {
        this.queryNumbers = queryNumbers;
    }

    public boolean isAccessPoint() {
        return isAccessPoint;
    }

    public void setAccessPoint(boolean accessPoint) {
        isAccessPoint = accessPoint;
    }

    public List<String> getOutNeighbors() {
        return outNeighbors;
    }

    public void setOutNeighbors(List<String> outNeighbors) {
        this.outNeighbors = outNeighbors;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Property property : scalarProperties) {
            sb.append(property.toNodeString()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return "Node{" +
                "nodeName='" + nodeName + '\'' +
                ", scalarProperties=" + sb.toString() +
                ", isAccessPoint=" + isAccessPoint +
                ", queryNumbers=" + queryNumbers +
                ", outNeighbors=" + outNeighbors +
                '}';
    }
}
