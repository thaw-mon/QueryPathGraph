package com.database.tool.Entity.QPG;

import com.database.tool.Entity.WorkLoad.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Get Graph's Edge, Node and QueryKeysEntity information
 * based on the input modelEntities and queryWorkloads
 */
public class QueryPathInfo {
    List<Edge> edgeList;

    List<Node> nodeList;

    List<QueryKeysEntity> queryKeysEntityList;

    public void initQPG(Input input) {
        setEdgeList(createEdges(input));
        setNodeList(createNodes(input));
        setQueryKeysEntityList(createQueryKeysEntities(input));
    }

    //Create edge data based on input
    public List<Edge> createEdges(Input input) {
        List<Edge> edges = new ArrayList<>();
        Map<String, Integer> edgeIndexMap = new HashMap<>();
        List<QueryWorkload> queryWorkloads = input.getQueryWorkloads();
        for (QueryWorkload queryWorkload : queryWorkloads) {
            //1.Gets the start node, the end node, and the edge attribute relationShip
            for (String property : queryWorkload.getFromProperties()) {
                String[] arr = property.split("\\.");
                for (int i = 0; i + 2 < arr.length; i += 2) {
                    String start = arr[i];
                    String end = arr[i + 2];
                    String relationShip = arr[i + 1];

                    String edgeIdentify = "<" + start + "," + end + ">/" + relationShip;
                    if (!edgeIndexMap.containsKey(edgeIdentify)) {
                        Edge edge = new Edge(start, end, relationShip);
                        //Gets Cardinality and qNumber between edges
                        char cardinality = input.getModelEntityByName(start).getCardinality(end, relationShip);
                        edge.setCardinality(cardinality);
                        edge.addQueryNumber(queryWorkload.getqNum());
                        //
                        edgeIndexMap.put(edgeIdentify, edges.size());
                        edges.add(edge);
                    } else {
                        Edge edge = edges.get(edgeIndexMap.get(edgeIdentify));
                        edge.addQueryNumber(queryWorkload.getqNum());
                    }
                }
            }
        }
        return edges;
    }


    //Create node based on input
    public List<Node> createNodes(Input input) {
        List<Node> nodes = new ArrayList<>();
        List<ModelEntity> modelEntities = input.getModelEntities();
        List<String> accessPoints = input.getAccessPoints();
        for (ModelEntity modelEntity : modelEntities) {
            Node node = new Node(modelEntity.getName());
            node.setScalarProperties(modelEntity.getProperties());
            // Determine if it is accessPoint
            node.setAccessPoint(accessPoints.contains(node.getNodeName()));
            // Get queryNumbers
            node.setQueryNumbers(input.getQueryNumber(node.getNodeName()));
            // Get the degree node
            node.setOutNeighbors(input.getOutNeighbors(node.getNodeName()));
            nodes.add(node);
        }
        return nodes;
    }

    public List<QueryKeysEntity> createQueryKeysEntities(Input input) {
        List<QueryKeysEntity> queryKeysEntities = new ArrayList<>();
        List<QueryWorkload> queryWorkloads = input.getQueryWorkloads();
        for (QueryWorkload queryWorkload : queryWorkloads) {
            QueryKeysEntity queryKeysEntity = new QueryKeysEntity();
            List<String> projection = new ArrayList<>();
            queryKeysEntity.setQueryNum(queryWorkload.getqNum());
            //Get EqualitySearchKeys and InequalitySearchKeys from the where clause
            for (String property : queryWorkload.getWhereProperties()) {
                //According to the >=;<=;= Three symbol division
                String[] arr = property.split("(=|>=|<=)");
                String key = arr[0].trim();
                if (property.contains(">=") || property.contains("<=")) {
                    queryKeysEntity.addInequalitySearchKeys(key);
                } else
                    queryKeysEntity.addEqualitySearchKey(key);
                if (!projection.contains(key)) projection.add(key);
            }
            //Get OrderingKeys from the ORDER clause
            queryKeysEntity.setOrderingKeys(queryWorkload.getOrderProperties());
            for (String orderKey : queryWorkload.getOrderProperties()) {
                if (!projection.contains(orderKey)) projection.add(orderKey);
            }
            //Gets SelectedKeys from the SELECT clause
            for (String selectKey : queryWorkload.getSelectProperties()) {
                if (!projection.contains(selectKey)) projection.add(selectKey);
            }
            queryKeysEntity.setSelectedKeys(queryWorkload.getSelectProperties());
            queryKeysEntities.add(queryKeysEntity);
        }
        return queryKeysEntities;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public List<QueryKeysEntity> getQueryKeysEntityList() {
        return queryKeysEntityList;
    }

    public void setQueryKeysEntityList(List<QueryKeysEntity> queryKeysEntityList) {
        this.queryKeysEntityList = queryKeysEntityList;
    }

    @Override
    public String toString() {
        return "QueryPathInfo{" +
                "edgeList=" + edgeList +
                ", nodeList=" + nodeList +
                ", queryKeysEntityList=" + queryKeysEntityList +
                '}';
    }
}
