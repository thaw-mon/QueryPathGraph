package com.database.tool.Service.Schemata.ColumnFamilyModel;


import com.database.tool.Entity.WorkLoad.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ColumnFamilyScalarProperty {
    int id;
    List<Property> partitionKeys;
    List<Property> clusteringKeys;
    List<Property> values;

    public ColumnFamilyScalarProperty(int id) {
        this.id = id;
        partitionKeys = new ArrayList<>();
        clusteringKeys = new ArrayList<>();
        values = new ArrayList<>();
    }

    public String generateColumnFamilySql() {
        StringBuilder sb = new StringBuilder();
        sb.append("create table if not exists CFTable").append(id).append(" (");
        //traverse partitionKeys clusteringKeys values
        //1.add column
        for (Property scalarProperty : partitionKeys) {
            sb.append(scalarProperty.getName()).append(" ");
            sb.append(getCassandraType(scalarProperty.getType())).append(",");
        }
        //
        for (Property scalarProperty : clusteringKeys) {
            String[] arr = scalarProperty.getName().split("\\.");
            sb.append(arr[0]).append(" ");
            sb.append(getCassandraType(scalarProperty.getType())).append(",");
        }
        for (Property scalarProperty : values) {
            sb.append(scalarProperty.getName()).append(" ");
            sb.append(getCassandraType(scalarProperty.getType())).append(",");
        }
        //2.Add primary key Note that the cluster key must be added within the primary key
        sb.append("PRIMARY KEY (");
        sb.append("(");
        for (Property scalarProperty : partitionKeys) {
            sb.append(scalarProperty.getName()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        for (Property scalarProperty : clusteringKeys) {
            String[] arr = scalarProperty.getName().split("\\.");
            sb.append(",").append(arr[0]);
        }
        sb.append(")");

        sb.append(")");
        //3.add CLUSTERING ORDER
        if (!clusteringKeys.isEmpty()) {
            sb.append(" WITH CLUSTERING ORDER BY (");
            for (Property scalarProperty : clusteringKeys) {
                String[] arr = scalarProperty.getName().split("\\.");
                sb.append(arr[0]).append(" ").append(arr[1]).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
        }
        sb.append(";");
        return sb.toString();
    }

    //Converts the given type to the type corresponding to the Cassandra database
    private String getCassandraType(String str) {
        if (str.toUpperCase().equals("STRING")) {
            return "string";
        } else if (str.toUpperCase().equals("INT")) {
            return "int";
        } else if (str.toUpperCase().equals("STRING[]")) {
            return "list<text>";
        }else if (List.class.getName().equals(str)) {
            return "list<text>";
        } else if (Set.class.getName().equals(str)) {
            return "set<text>";
        }
        return null; //
    }

    public void addPartitionKey(Property partitionKey) {
        partitionKeys.add(partitionKey);
    }

    public void addClusteringKey(Property clusteringKey) {
        clusteringKeys.add(clusteringKey);
    }

    //When there are more than one Order key, the order is sorted
    private int orderCount = 0;

    public void addClusteringKeyToHead(Property clusteringKey) {
        clusteringKeys.add(orderCount++, clusteringKey);
    }

    public void addValues(Property value) {
        values.add(value);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Property> getPartitionKeys() {
        return partitionKeys;
    }

    public void setPartitionKeys(List<Property> partitionKeys) {
        this.partitionKeys = partitionKeys;
    }

    public List<Property> getClusteringKeys() {
        return clusteringKeys;
    }

    public void setClusteringKeys(List<Property> clusteringKeys) {
        this.clusteringKeys = clusteringKeys;
    }

    public List<Property> getValues() {
        return values;
    }

    public void setValues(List<Property> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        StringBuilder pk = new StringBuilder();
        pk.append("[");
        for (Property property : partitionKeys) {
            pk.append(property.getType()).append(" ").append(property.getName()).append(",");
        }
        if (!partitionKeys.isEmpty())
            pk.deleteCharAt(pk.length() - 1);
        pk.append("]");
        StringBuilder ck = new StringBuilder();
        ck.append("[");
        for (Property property : clusteringKeys) {
            ck.append(property.getType());
            for (String s : property.getName().split("\\.")) {
                ck.append(" ").append(s);
            }
            ck.append(",");
        }
        if (!clusteringKeys.isEmpty())
            ck.deleteCharAt(ck.length() - 1);
        ck.append("]");
        return "ColumnFamilyScalarProperty{" +
                "id=" + id +
                ", partitionKeys=" + pk.toString() +
                ", clusteringKeys=" + ck.toString() +
                ", values=" + values +
                '}';
    }
}
