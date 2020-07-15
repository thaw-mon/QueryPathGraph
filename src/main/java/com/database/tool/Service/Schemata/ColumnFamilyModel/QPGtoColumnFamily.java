package com.database.tool.Service.Schemata.ColumnFamilyModel;


import com.database.tool.Entity.QPG.Graph;
import com.database.tool.Entity.WorkLoad.Edge;
import com.database.tool.Entity.WorkLoad.Node;
import com.database.tool.Entity.WorkLoad.Property;
import com.database.tool.Entity.WorkLoad.QueryKeysEntity;
import com.database.tool.Service.Schemata.QPGToSchemata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class QPGtoColumnFamily extends QPGToSchemata {
    //    private Graph graph;
    private Map<Integer, ColumnFamilyScalarProperty> columnFamilyScalarPropertyMap;

    public QPGtoColumnFamily(Graph g) {
        super(g);
//        graph = g;
        columnFamilyScalarPropertyMap = new HashMap<>();
    }

    public Map<Integer, ColumnFamilyScalarProperty> getColumnFamilyScalarPropertyMap() {
        return columnFamilyScalarPropertyMap;
    }

    public void setColumnFamilyScalarPropertyMap(Map<Integer, ColumnFamilyScalarProperty> columnFamilyScalarPropertyMap) {
        this.columnFamilyScalarPropertyMap = columnFamilyScalarPropertyMap;
    }

    /**
     * Input : QPG
     * generateColumnFamilySchemata
     * output ： ColumnFamily Schemata
     * add type Value
     */
    public void generateSchemata() {
        columnFamilyScalarPropertyMap.clear();
        List<Node> nodes = graph.getAccessPoints();
        for (Node node : nodes) {
            for (int qNum : node.getQueryNumbers()) {
                if (!columnFamilyScalarPropertyMap.containsKey(qNum)) {
                    ColumnFamilyScalarProperty columnFamilyScalarProperty = new ColumnFamilyScalarProperty(qNum);
                    columnFamilyScalarPropertyMap.put(qNum, columnFamilyScalarProperty);
                }
                DesignCFSchemata(qNum, columnFamilyScalarPropertyMap.get(qNum), graph.queryKeysEntityMaps.get(qNum), node, null);
            }

        }

    }

    public void DesignCFSchemata(int qNum, ColumnFamilyScalarProperty columnFamilyProperty, QueryKeysEntity entity, Node U, Node T) {
        for (Property scalarProperty : U.getScalarProperties()) {
            //
            String strEntity = U.getNodeName() + "." + scalarProperty.getName();
            String key = U.getNodeName() + "_" + scalarProperty.getName();
            Property keyProperty = new Property(key, scalarProperty.getType(), scalarProperty.isPrimaryKey());
            if (entity.getEqualitySearchKeys().contains(strEntity) || (scalarProperty.getType().contains("[]") && entity.getEqualitySearchKeys().contains(strEntity.substring(0, strEntity.length() - 1)))) {
                if (!entity.getEqualitySearchKeys().contains(strEntity) &&scalarProperty.getType().contains("[]")) {
                    Property arrKey = new Property(key, scalarProperty.getType(), scalarProperty.isPrimaryKey());
                    columnFamilyProperty.addValues(arrKey); //Add the array type to values
                    keyProperty.setName(strEntity.substring(0, strEntity.length() - 1));
                    keyProperty.setType(scalarProperty.getType().split("\\[]")[0]);
                }
                columnFamilyProperty.addPartitionKey(keyProperty);
            } else if (entity.getInequalitySearchKeys().contains(strEntity)) {
                keyProperty.setName(key + ".ASC"); //默认升序
                columnFamilyProperty.addClusteringKey(keyProperty);
            } else if (U.getPrimaryKeys().contains(scalarProperty.getName())) {
                if (T == null || (graph.getCardinalityByNode(T, U) == '*')) {
                    keyProperty.setName(key + ".ASC"); //默认升序
                    columnFamilyProperty.addClusteringKey(keyProperty);
                }
            }
            else if (entity.getSelectedKeys().contains(strEntity)) {
                columnFamilyProperty.addValues(keyProperty);
            }
            //There are cases where the default is not (.des or.ASC)
            if (entity.getOrderingKeys().contains(strEntity) || entity.getOrderingKeys().contains(strEntity + "." + "DESC") || entity.getOrderingKeys().contains(strEntity + "." + "ASC")) {
                if (columnFamilyProperty.getValues().contains(keyProperty)) {
                    columnFamilyProperty.values.remove(keyProperty);
                }
                String order = "ASC";
                if (entity.getOrderingKeys().contains(strEntity + "." + "DESC")) {
                    order = "DESC";
                }
                keyProperty.setName(key + "." + order);
                if (!columnFamilyProperty.getClusteringKeys().contains(keyProperty))
                    columnFamilyProperty.addClusteringKeyToHead(keyProperty);
            }

        }

        for (Edge edge : graph.edgeMaps.getOrDefault(U.getNodeName(), new ArrayList<>())) {
            if (edge.getQueryNumbers().contains(qNum)) {
                DesignCFSchemata(qNum, columnFamilyProperty, entity, graph.nodeMaps.get(edge.getEndNode()), U);
            }
        }
    }

    public void printSchemata() {
        for (int key : columnFamilyScalarPropertyMap.keySet()) {
            System.out.println(columnFamilyScalarPropertyMap.get(key));
        }
    }

    public List<ColumnFamilyScalarProperty> getSchemataInfo() {
        List<ColumnFamilyScalarProperty> columnFamilyScalarPropertyList = new ArrayList<>();
        columnFamilyScalarPropertyList.addAll(columnFamilyScalarPropertyMap.values());
        return columnFamilyScalarPropertyList;
    }
}
