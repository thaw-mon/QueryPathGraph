package com.database.tool.Entity.WorkLoad;

import java.io.Serializable;

public class Property implements Serializable {
    private static final long serialVersionUID = -6871474678599924928L;
    String name;
    String type;
    boolean isPrimaryKey;

    public Property(String name, String type, boolean isPrimaryKey) {
        this.name = name;
        this.type = type;
        this.isPrimaryKey = isPrimaryKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Property property = (Property) o;

        if (isPrimaryKey != property.isPrimaryKey) return false;
        if (name != null ? !name.equals(property.name) : property.name != null) return false;
        return type != null ? type.equals(property.type) : property.type == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (isPrimaryKey ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder(type + " " + name);
        if(isPrimaryKey){
            ret.append(" [K]");
        }
        return ret.toString();
    }

    public String toNodeString() {
        return name + "/" + type;
    }
}
