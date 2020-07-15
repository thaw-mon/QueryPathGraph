package com.database.tool.Entity.WorkLoad;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the definition of the input text
 */
public class Input {

    private String path;
    private String fileName;

    public Input() {
    }

    public Input(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
    }

    private List<ModelEntity> modelEntities;
    private List<QueryWorkload> queryWorkloads;

    //Get the accessPoint information on workLoads From part
    public List<String> getAccessPoints() {
        List<String> accessPoints = new ArrayList<>();
        for (QueryWorkload queryWorkload : queryWorkloads) {
            String property = queryWorkload.getFromProperties().get(0);
            //Gets the first node of the QPG path
            String[] arr = property.split("\\.");
            if (!accessPoints.contains(arr[0])) {
                accessPoints.add(arr[0]);
            }
        }
        return accessPoints;
    }

    //Gets the QueryNumber of a node currently only if the current node is the accessPoint of the query path
    public List<Integer> getQueryNumber(String nodeName) {
        List<Integer> queryNumbers = new ArrayList<>();
        for (QueryWorkload queryWorkload : queryWorkloads) {
            String property = queryWorkload.getFromProperties().get(0);
            //Gets the first node of the QPG path
            String[] arr = property.split("\\.");
            if (nodeName.equals(arr[0])) {
                queryNumbers.add(queryWorkload.getqNum());
            }

        }
        return queryNumbers;
    }

    //Obtain outNeighbors based on QPG Path
    public List<String> getOutNeighbors(String nodeName) {
        List<String> outNeighbors = new ArrayList<>();
        for (QueryWorkload queryWorkload : queryWorkloads) {
            for (String property : queryWorkload.getFromProperties()) {
                //Gets the first node of the QPG path
                String[] arr = property.split("\\.");
                for (int i = 0; i + 2 < arr.length; i += 2) {
                    if (nodeName.equals(arr[i])) {
                        if (!outNeighbors.contains(arr[i + 2]))
                            outNeighbors.add(arr[i + 2]);
                    }
                }
            }
        }
        return outNeighbors;
    }

    public ModelEntity getModelEntityByName(String name) {
        for (ModelEntity modelEntity : modelEntities) {
            if (modelEntity.getName().equals(name)) {
                return modelEntity;
            }
        }
        return null;
    }

    public void transferFileToClass() {
        if (modelEntities == null) modelEntities = new ArrayList<>();
        if (queryWorkloads == null) queryWorkloads = new ArrayList<>();

        File file = new File(path, fileName);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // Read the string by line
            int flag = 0; //
            StringBuilder sb = new StringBuilder();
            while ((str = bf.readLine()) != null) {
                sb.append(str);
                //Determines which object the current read file belongs to
                if (flag == 0) {
                    if (str.contains("entity")) {
                        flag = 1;
                    } else if (str.contains("query")) {
                        flag = 2;
                    }
                }
                //TODO To be modified, you should get {} to count as a full class
                //Read blank lines --> A complete class read successfully, read a complete definition
                if (str.isEmpty()) {
                    switch (flag) {
                        case 1:
                            ModelEntity modelEntity = ModelEntity.StringToClass(sb.toString());
                            modelEntities.add(modelEntity);
                            break;
                        case 2:
                            QueryWorkload queryWorkload = QueryWorkload.StringToClass(sb.toString());
                            queryWorkloads.add(queryWorkload);
                            break;
                        default:
                            //do nothing
                    }
                    flag = 0;
                    sb.setLength(0);
                }
            }
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init(String entityFile, String queryFile) {
        setModelEntities(ModelEntity.readFile(entityFile));
        setQueryWorkloads(QueryWorkload.readFile(queryFile));
    }

    public List<ModelEntity> getModelEntities() {
        return modelEntities;
    }

    public void setModelEntities(List<ModelEntity> modelEntities) {
        this.modelEntities = modelEntities;
    }

    public List<QueryWorkload> getQueryWorkloads() {
        return queryWorkloads;
    }

    public void setQueryWorkloads(List<QueryWorkload> queryWorkloads) {
        this.queryWorkloads = queryWorkloads;
    }

    @Override
    public String toString() {
        return "Input{" +
                "modelEntities=" + modelEntities +
                ", queryWorkloads=" + queryWorkloads +
                '}';
    }
}
