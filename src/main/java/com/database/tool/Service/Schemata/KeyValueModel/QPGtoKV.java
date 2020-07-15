package com.database.tool.Service.Schemata.KeyValueModel;


import com.database.tool.Entity.QPG.Graph;
import com.database.tool.Entity.WorkLoad.Edge;
import com.database.tool.Entity.WorkLoad.Node;
import com.database.tool.Entity.WorkLoad.Property;
import com.database.tool.Entity.WorkLoad.QueryKeysEntity;
import com.database.tool.Service.Schemata.ColumnFamilyModel.ColumnFamilyScalarProperty;
import com.database.tool.Service.Schemata.QPGToSchemata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Algorithm 4: QPGtoKV
 * Input: QPG
 * Output: The corresponding key-value schema
 */
public class QPGtoKV extends QPGToSchemata {

    //    private Graph graph;
    private Map<Integer, KeyValueProperty> keyValuePropertyMap;

    public QPGtoKV(Graph g) {
        super(g);
//        graph = g;
        keyValuePropertyMap = new HashMap<>();
    }

    public Map<Integer, KeyValueProperty> getKeyValuePropertyMap() {
        return keyValuePropertyMap;
    }

    public void setKeyValuePropertyMap(Map<Integer, KeyValueProperty> keyValuePropertyMap) {
        this.keyValuePropertyMap = keyValuePropertyMap;
    }

    public void generateSchemata() {
        keyValuePropertyMap.clear();
        List<Node> nodes = graph.getAccessPoints();
        for (Node node : nodes) {
            for (int qNum : node.getQueryNumbers()) {
                if (!keyValuePropertyMap.containsKey(qNum)) {
                    KeyValueProperty keyValueProperty = new KeyValueProperty(qNum);
                    keyValuePropertyMap.put(qNum, keyValueProperty);
                }
                DesignKeyValueComponents(qNum, keyValuePropertyMap.get(qNum), graph.queryKeysEntityMaps.get(qNum), node, null);
            }
        }
    }

    /**
     * Algorithm 5: DesignKeyValueComponents
     * Input: A query number qNum, an entity A as the access point of the query, a QPG node U and its parent T on the query tree
     * Output: Evolving the KV schema required to efficiently answer the query, according to the scalar properties of U
     */
    private void DesignKeyValueComponents(int qNum, KeyValueProperty keyValueProperty, QueryKeysEntity entity, Node node, Node T) {
        for (Property scalarProperty : node.getScalarProperties()) {
            String strEntity = node.getNodeName() + "." + scalarProperty.getName();
            assert (entity != null);
            if (entity.getEqualitySearchKeys().contains(strEntity)) {
                keyValueProperty.addMajorKeys(node.getNodeName() + "_" + scalarProperty.getName());
            } else if (node.getPrimaryKeys().contains(scalarProperty.getName())) {
                if (T == null || (graph.getCardinalityByNode(T, node) == '*')) {
                    keyValueProperty.addMinorKeys(node.getNodeName() + "_" + scalarProperty.getName());
                }
            } else if (entity.getInequalitySearchKeys().contains(strEntity)) {
                keyValueProperty.addMinorKeys(node.getNodeName() + "_" + scalarProperty.getName());
            } else if (entity.getOrderingKeys().contains(strEntity + "." + "DESC") || entity.getOrderingKeys().contains(strEntity + "." + "ASC")) {
                keyValueProperty.addMinorKeys(node.getNodeName() + "_" + scalarProperty.getName());
            } else if (entity.getSelectedKeys().contains(strEntity)) {
                keyValueProperty.addValues(node.getNodeName() + "_" + scalarProperty.getName());
            }
        }
        for (Edge edge : graph.edgeMaps.getOrDefault(node.getNodeName(),new ArrayList<>())) {
            if (edge.getQueryNumbers().contains(qNum)) {
                DesignKeyValueComponents(qNum, keyValueProperty, entity, graph.nodeMaps.get(edge.getEndNode()), node);
            }
        }

    }

    public void printSchemata(){
    }

    public List<KeyValueProperty> getSchemataInfo() {
        List<KeyValueProperty> keyValuePropertyList = new ArrayList<>();
        keyValuePropertyList.addAll(keyValuePropertyMap.values());
        return keyValuePropertyList;
    }

}
