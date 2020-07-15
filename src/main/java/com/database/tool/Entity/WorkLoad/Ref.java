package com.database.tool.Entity.WorkLoad;

import java.io.Serializable;

public class Ref implements Serializable {
    private static final long serialVersionUID = 2423334198020096586L;
    String name;
    char cardinality;
    String relationShip;

    public Ref(String name, char cardinality, String relationShip) {
        this.name = name;
        this.cardinality = cardinality;
        this.relationShip = relationShip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getCardinality() {
        return cardinality;
    }

    public void setCardinality(char cardinality) {
        this.cardinality = cardinality;
    }

    public String getRelationShip() {
        return relationShip;
    }

    public void setRelationShip(String relationShip) {
        this.relationShip = relationShip;
    }

    @Override
    public String toString() {
        return "ref " + name + "[" + cardinality + "]" +
                " " + relationShip;
    }

}
