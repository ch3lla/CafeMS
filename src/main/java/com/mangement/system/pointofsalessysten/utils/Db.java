package com.mangement.system.pointofsalessysten.utils;

import com.mangement.system.pointofsalessysten.dto.EmployeeDto;
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

public class Db {

    private MongoClient mongoClient;
    private MongoDatabase database;

    public void connectDB() {
        String connectionString = "mongodb://127.0.0.1:27017";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("pointOfSales");

        try {
            // Send a ping to confirm a successful connection
            Bson command = new BsonDocument("ping", new BsonInt64(1));
            Document commandResult = database.runCommand(command);
            System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
            database.createCollection("employees");
        } catch (MongoException me) {
            System.err.println(me);
        }
    }

    public void insertEmployee(EmployeeDto employee) {
        if (database == null) {
            throw new IllegalStateException("Database not connected. Call connectDB() first.");
        }

        MongoCollection<Document> collection = database.getCollection("employees");

        if (getEmployee(employee.getUsername()).containsValue(employee.getUsername())){
            throw new IllegalStateException("Employee with username already exists.");
        }

        Document doc = new Document("username", employee.getUsername())
                .append("password", employee.getPassword())
                .append("securityQuestion", employee.getSecurityQuestion())
                .append("answer", employee.getAnswer());

        collection.insertOne(doc);
        System.out.println("Employee inserted successfully");
    }

    public Document getEmployee(String username) {
        if (database == null) {
            throw new IllegalStateException("Database not connected. Call connectDB() first.");
        }

        MongoCollection<Document> collection = database.getCollection("employees");

        // Find the employee document by username
        Document query = new Document("username", username);
        Document employeeDoc = collection.find(query).first();

        if (employeeDoc != null) {
            // Print the employee details
            System.out.println("Employee found:");
            return employeeDoc;
//            System.out.println("Username: " + employeeDoc.getString("username"));
//            System.out.println("Password: " + employeeDoc.getString("password"));
//            System.out.println("Security Question: " + employeeDoc.getString("securityQuestion"));
//            System.out.println("Answer: " + employeeDoc.getString("answer"));
        } else {
            System.out.println("Employee with username " + username + " not found.");
            return null;
        }
    }
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
