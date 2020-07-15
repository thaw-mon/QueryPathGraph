package com.database.tool.Entity.QPG;

import com.database.tool.Entity.WorkLoad.Edge;
import com.database.tool.Entity.WorkLoad.Node;
import com.database.tool.Entity.WorkLoad.QueryKeysEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Define the Query Path Graph
public class Graph {
    //1.Define all nodes
    List<Node> nodes;
    //2.Define all edges
    List<Edge> edges;
    //5.Define query entities
    List<QueryKeysEntity> queryKeysEntities;

    //Use a Map to store edges to facilitate direct access to nodes' outgoing edges
    public Map<String, List<Edge>> edgeMaps;
    //Using a Map to store nodes makes it easy to access nodes directly
    public Map<String, Node> nodeMaps;
    public Map<Integer, QueryKeysEntity> queryKeysEntityMaps;


    //3.Add nodes or edges
    public void addNode(Node node) {
        nodes.add(node);
        nodeMaps.put(node.getNodeName(), node);
    }
    public void addEdge(Edge edge) {
        edges.add(edge);
        if (!edgeMaps.containsKey(edge.getStartNode())) {
            edgeMaps.put(edge.getStartNode(), new ArrayList<>());
        }
        edgeMaps.get(edge.getStartNode()).add(edge);
    }

    //Add the query number of the node through the query path
    public void addQueryKeyEntity(QueryKeysEntity queryKeysEntity) {
        queryKeysEntities.add(queryKeysEntity);
        queryKeysEntityMaps.put(queryKeysEntity.getQueryNum(), queryKeysEntity);
    }

    public Edge getEdgeByNode(Node start, Node end) {
        for (Edge edge : edgeMaps.get(start.getNodeName())) {
            if (edge.getEndNode().equals(end.getNodeName())) {
                return edge;
            }
        }
        return null;
    }

    public Edge getEdgeByEndPointAndQNum(String end, int qNum) {
        for (Edge edge : edges) {
            if (edge.getQueryNumbers().contains(qNum) && edge.getEndNode().equals(end)) {
                return edge;
            }
        }
        return null;
    }

    public Edge getEdgeByNodeAndNum(Node start, Node end, int qNum) {
        for (Edge edge : edgeMaps.get(start.getNodeName())) {
            if (edge.getQueryNumbers().contains(qNum) && edge.getEndNode().equals(end.getNodeName())) {
                return edge;
            }
        }
        return null;
    }

    public List<Edge> getEdgesByNode(Node start, Node end) {
        List<Edge> edges = new ArrayList<>();
        for (Edge edge : edgeMaps.get(start.getNodeName())) {
            if (edge.getEndNode().equals(end.getNodeName())) {
                edges.add(edge);
            }
        }
        return edges;
    }

    public Character getCardinalityByNode(Node start, Node end) {
        for (Edge edge : edgeMaps.get(start.getNodeName())) {
            if (edge.getEndNode().equals(end.getNodeName())) {
                return edge.getCardinality();
            }
        }
        return null;
    }

    //4.Traverse the graph

    //The initialization of a graph
    public Graph() {
        nodes = new ArrayList<>();
        nodeMaps = new HashMap<>();
        edges = new ArrayList<>();
        edgeMaps = new HashMap<>();
        queryKeysEntities = new ArrayList<>();
        queryKeysEntityMaps = new HashMap<>();
    }

    public void init(List<Node> nodeList, List<Edge> edgeList, List<QueryKeysEntity> queryKeysEntityList) {
        for (Node node : nodeList) {
            addNode(node);
        }
        for (Edge edge : edgeList) {
            addEdge(edge);
        }
        for (QueryKeysEntity queryKeysEntity : queryKeysEntityList) {
            addQueryKeyEntity(queryKeysEntity);
        }
    }

    public List<Node> getAccessPoints() {
        List<Node> nodeList = new ArrayList<>();
        for (Node node : nodes) {
            if (node.isAccessPoint())
                nodeList.add(node);
        }
        return nodeList;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public List<QueryKeysEntity> getQueryKeysEntities() {
        return queryKeysEntities;
    }

    public void setQueryKeysEntities(List<QueryKeysEntity> queryKeysEntities) {
        this.queryKeysEntities = queryKeysEntities;
    }
}
