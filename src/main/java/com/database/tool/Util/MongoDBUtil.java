package com.database.tool.Util;

import com.mongodb.client.*;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ValidationOptions;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.BasicBSONList;

import java.util.ArrayList;
import java.util.List;


//Mongodb connects to the database tool class
public class MongoDBUtil {
    public MongoClient mongoClient;

    //1.Connect the mongo
    public MongoClient getConnectClient() {
        mongoClient = MongoClients.create("mongodb://admin:admin@localhost:27017/?authSource=admin");
        return mongoClient;
    }

    //2. Connect to database
    public MongoDatabase connectDatabase(String database) {
        return mongoClient.getDatabase(database);
    }

    //3.Create document validation
    public void createTableWithDocumentValidator(MongoDatabase db, String table, Bson schema) {
        db.getCollection(table).drop(); //empty
        ValidationOptions validationOptions = new ValidationOptions().validator(Filters.jsonSchema(schema));
        db.createCollection(table,
                new CreateCollectionOptions().validationOptions(validationOptions));
    }

    public MongoCollection<Document> getCollection(MongoDatabase db, String collection) {
        MongoCollection<Document> doc = db.getCollection(collection);
        return doc;
    }

    //4.Insert data
    public InsertOneResult insertOneData(MongoCollection<Document> doc, Document document) {
        InsertOneResult result = doc.insertOne(document);
        return result;
    }

    public InsertManyResult insertManyData(MongoCollection<Document> doc, List<Document> documents) {
        InsertManyResult result = doc.insertMany(documents);
        return result;
    }

    //5.Query data
    public int findTableSize(MongoCollection<Document> doc) {
        return (int) doc.countDocuments();
    }

    public List<Document> findAllData(MongoCollection<Document> doc) {
        List<Document> documentList = new ArrayList<>();
        FindIterable<Document> documents = doc.find();
        for (Document document : documents) {
            documentList.add(document);
        }
        return documentList;
    }

    //Query the data by condition
    public List<Document> findDataByCondition(MongoCollection<Document> doc, Bson bson) {
        List<Document> documentList = new ArrayList<>();
        FindIterable<Document> documents = doc.find(bson);
        for (Document document : documents) {
            documentList.add(document);
        }
        return documentList;
    }

    //Query the data based on the criteria and add the sort criteria
    public List<Document> findDataByConditionAndSort(MongoCollection<Document> doc, Bson bson, Bson sortBson) {
        List<Document> documentList = new ArrayList<>();
        FindIterable<Document> documents = doc.find(bson).sort(sortBson);
        for (Document document : documents) {
            documentList.add(document);
        }
        return documentList;
    }


    //Query the data by condition and add a sort condition and restrict the return data
    public List<Document> findDataByCondition2AndSort(MongoCollection<Document> doc, Bson bson, Bson sortBson) {
        List<Document> documentList = new ArrayList<>();
        FindIterable<Document> documents = doc.find(bson).sort(sortBson);
        doc.find(bson, Document.class).sort(sortBson);

        for (Document document : documents) {
            documentList.add(document);
        }
        return documentList;
    }

    //addIndex
    public String addIndex(MongoCollection<Document> doc, Document document) {
        String ret = doc.createIndex(document);
        return ret;
    }

    // Create a table
    public void createTable(MongoDatabase db, String tableName, Bson schema) {
        //1.Determine if the table exists
        db.getCollection(tableName).drop();
        ValidationOptions validationOptions = new ValidationOptions().validator(Filters.jsonSchema(schema));
        db.createCollection(tableName,
                new CreateCollectionOptions().validationOptions(validationOptions));

    }

    //Gets the connection database object without authentication
    public static MongoDatabase getConnect() {
        MongoClient mongoClient = MongoClients.create("mongodb://admin:admin@localhost:27017/?authSource=admin");
        return mongoClient.getDatabase("test2");
    }

    public static MongoDatabase getConnect2() {
        MongoClient mongoClient = MongoClients.create("mongodb://admin:admin@localhost:27017/?authSource=admin");
        return mongoClient.getDatabase("test3");
    }

    public static MongoDatabase getConnect(String database) {
        MongoClient mongoClient = MongoClients.create("mongodb://admin:admin@localhost:27017/?authSource=admin");
        return mongoClient.getDatabase(database);
    }

}
