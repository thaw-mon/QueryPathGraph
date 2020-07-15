package com.database.tool.Service.Schemata.KeyValueModel;

import java.util.ArrayList;
import java.util.List;

public class KeyValueProperty {
    int id;
    List<String> majorKeys;
    List<String> minorKeys;
    List<String> values;

    public KeyValueProperty(int id) {
        this.id = id;
        majorKeys = new ArrayList<>();
        minorKeys = new ArrayList<>();
        values = new ArrayList<>();
    }

    public void addMajorKeys(String majorKey) {
        majorKeys.add(majorKey);
    }

    public void addMinorKeys(String minorKey) {
        minorKeys.add(minorKey);
    }

    public void addValues(String value) {
        values.add(value);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getMajorKeys() {
        return majorKeys;
    }

    public void setMajorKeys(List<String> majorKeys) {
        this.majorKeys = majorKeys;
    }

    public List<String> getMinorKeys() {
        return minorKeys;
    }

    public void setMinorKeys(List<String> minorKeys) {
        this.minorKeys = minorKeys;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "KeyValueProperty{" +
                "majorKeys=" + majorKeys +
                ", minorKeys=" + minorKeys +
                ", values=" + values +
                '}';
    }
}
