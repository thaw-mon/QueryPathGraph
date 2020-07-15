package com.database.tool.Entity.WorkLoad;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * QPG QueryKeysEntity
 */
public class QueryKeysEntity {
    int queryNum;
    List<String> EqualitySearchKeys;
    List<String> InequalitySearchKeys;
    List<String> OrderingKeys;
    List<String> SelectedKeys;  //other name = Projection/aggregation  properties


    public QueryKeysEntity() {
        EqualitySearchKeys = new ArrayList<>();
        InequalitySearchKeys = new ArrayList<>();
        OrderingKeys = new ArrayList<>();
        SelectedKeys = new ArrayList<>();
    }

    public static void writeFile(String fileName, List<QueryKeysEntity> queryKeysEntities) {
        try {
            FileWriter fw = new FileWriter(fileName);
            for (QueryKeysEntity queryKeysEntity : queryKeysEntities) {
                fw.write(queryKeysEntity.toString());
                fw.write("\r\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addEqualitySearchKey(String key) {
        if (EqualitySearchKeys == null) {
            EqualitySearchKeys = new ArrayList<>();
        }
        if (EqualitySearchKeys.contains(key)) {
            return false;
        }
        EqualitySearchKeys.add(key);
        return true;
    }

    public boolean addInequalitySearchKeys(String key) {
        if (InequalitySearchKeys == null) {
            InequalitySearchKeys = new ArrayList<>();
        }
        if (InequalitySearchKeys.contains(key)) {
            return false;
        }
        InequalitySearchKeys.add(key);
        return true;
    }

    public boolean addOrderingKeys(String key) {
        if (OrderingKeys == null) {
            OrderingKeys = new ArrayList<>();
        }
        if (OrderingKeys.contains(key)) {
            return false;
        }
        OrderingKeys.add(key);
        return true;
    }

    public int getQueryNum() {
        return queryNum;
    }

    public void setQueryNum(int queryNum) {
        this.queryNum = queryNum;
    }

    public List<String> getEqualitySearchKeys() {
        return EqualitySearchKeys;
    }

    public void setEqualitySearchKeys(List<String> equalitySearchKeys) {
        EqualitySearchKeys = equalitySearchKeys;
    }

    public List<String> getInequalitySearchKeys() {
        return InequalitySearchKeys;
    }

    public void setInequalitySearchKeys(List<String> inequalitySearchKeys) {
        InequalitySearchKeys = inequalitySearchKeys;
    }

    public List<String> getOrderingKeys() {
        return OrderingKeys;
    }

    public void setOrderingKeys(List<String> orderingKeys) {
        OrderingKeys = orderingKeys;
    }

    public List<String> getSelectedKeys() {
        return SelectedKeys;
    }

    public void setSelectedKeys(List<String> selectedKeys) {
        SelectedKeys = selectedKeys;
    }

    @Override
    public String toString() {
        return "QueryKeysEntity{" +
                "queryNum=" + queryNum +
                ", EqualitySearchKeys=" + EqualitySearchKeys +
                ", InequalitySearchKeys=" + InequalitySearchKeys +
                ", OrderingKeys=" + OrderingKeys +
                ", SelectedKeys=" + SelectedKeys +
                '}';
    }
}
