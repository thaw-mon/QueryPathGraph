package com.database.tool.Entity.WorkLoad;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Input1: The textual conceptual model
 */
public class ModelEntity implements Serializable {

    String name;
    List<Property> properties;
    List<Ref> refList;

    public Character getCardinality(String refName, String relationShip) {
        for (Ref ref : refList) {
            if (ref.getName().equals(refName) && ref.getRelationShip().equals(relationShip)) {
                return ref.getCardinality();
            }
        }
        return null;
    }

    public static void writeFile(String fileName, List<ModelEntity> modelEntities) {
        try {
            FileWriter fw = new FileWriter(fileName);
            for (ModelEntity modelEntity : modelEntities) {
                fw.write(modelEntity.toString());
                fw.write("\r\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<ModelEntity> readFile(String fileName) {
        List<ModelEntity> modelEntities = new ArrayList<>();
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // Read the string by line
            while ((str = bf.readLine()) != null) {
                ModelEntity modelEntity = StringToClass(str);
                modelEntities.add(modelEntity);
            }
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return modelEntities;
    }

    public static ModelEntity StringToClass(String str) {
        ModelEntity modelEntity = new ModelEntity();
        int c1 = str.indexOf('{');
        int c2 = str.indexOf('}');
        //1.Get entity name
        String[] fistPart = str.substring(0, c1).split(" ");
        modelEntity.setName(fistPart[1]);
        //2.Gets properties and references
        List<Property> properties = new ArrayList<>();
        List<Ref> refList = new ArrayList<>();
        String[] secondPart = str.substring(c1 + 1, c2).split(",");
        for (String v : secondPart) {
            String[] arr = v.trim().split(" ");
            //Reference types
            if (arr[0].equals("ref")) {
                int c = arr[1].indexOf('[');
                String name = arr[1].substring(0, c);
                char cardinality = arr[1].charAt(c + 1);
                String relationShip = arr[2];
                Ref ref = new Ref(name, cardinality, relationShip);
                refList.add(ref);
            } else {
                //Property
                String name = arr[1];
                String type = arr[0];
                boolean isPrimaryKey = (arr.length == 3) && (arr[2].equals("[K]"));
                Property property = new Property(name, type, isPrimaryKey);
                properties.add(property);
            }
        }
        modelEntity.setProperties(properties);
        modelEntity.setRefList(refList);
        return modelEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public List<Ref> getRefList() {
        return refList;
    }

    public void setRefList(List<Ref> refList) {
        this.refList = refList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("entity ");
        sb.append(name).append(" {");
        for (Property property : properties) {
            sb.append((property.toString())).append(",");
        }
        for (Ref ref : refList) {
            sb.append(ref.toString()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}");
        return sb.toString();
    }
}
