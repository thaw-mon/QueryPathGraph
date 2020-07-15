package com.database.tool.Service.WorkLoad;

import com.database.tool.Service.DatabaseGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryPathInfoTest {
    String path = "src/main/resources/experiments";
    DatabaseGenerator databaseGenerator;

    //1. Convert the text data to the Input class data Test
    @Test
    public void getInputInfoByDL() { 
        String fileName = "DL.info";
        databaseGenerator = new DatabaseGenerator(path, fileName);
        databaseGenerator.initInputInfo();
        databaseGenerator.printInputInfo();
    }

    @Test
    public void getInputInfoByEAC() { 
        String fileName = "EAC.info";
        databaseGenerator = new DatabaseGenerator(path, fileName);
        databaseGenerator.initInputInfo();
        databaseGenerator.printInputInfo();
    }

    @Test
    public void getInputInfoByOnlineStore() { 
        String fileName = "onlineStore.info";
        databaseGenerator = new DatabaseGenerator(path, fileName);
        databaseGenerator.initInputInfo();
        databaseGenerator.printInputInfo();
    }

    //2.Convert Input class data to Query Path Info data
    @Test
    public void getQueryPathInfoByOnlineStore(){
        String fileName = "onlineStore.info";
        databaseGenerator = new DatabaseGenerator(path, fileName);
        databaseGenerator.convertToQPGInfo();
        databaseGenerator.printQPGInfo();
    }

    @Test
    public void getQueryPathInfoByEAC(){
        String fileName = "EAC.info";
        databaseGenerator = new DatabaseGenerator(path, fileName);
        databaseGenerator.convertToQPGInfo();
        databaseGenerator.printQPGInfo();
    }

    @Test
    public void getQueryPathInfoByDL() {
        String fileName = "DL.info";
        databaseGenerator = new DatabaseGenerator(path, fileName);
        databaseGenerator.convertToQPGInfo();
        databaseGenerator.printQPGInfo();
    }

    //Query Path Info converts to Graph mode
    @Test
    public void getQueryPathGraphByDL() {
        String fileName = "DL.info";
        databaseGenerator = new DatabaseGenerator(path, fileName);
        databaseGenerator.convertToQueryPathGraph();
    }
    @Test
    public void getQueryPathGraphByEAC(){
        String fileName = "EAC.info";
        databaseGenerator = new DatabaseGenerator(path, fileName);
        databaseGenerator.convertToQueryPathGraph();
    }

    @Test
    public void getQueryPathGraphByOnlineStore(){
        String fileName = "onlineStore.info";
        databaseGenerator = new DatabaseGenerator(path, fileName);
        databaseGenerator.convertToQueryPathGraph();
    }
}