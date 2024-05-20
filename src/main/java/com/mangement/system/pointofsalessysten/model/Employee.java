package com.mangement.system.pointofsalessysten.model;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

public class Employee {
    @BsonId
    private ObjectId id;
    private String username;
    private String password;
    private String securityQuestion;
    private String answer;

    public Employee(){}

    public Employee(ObjectId id, String username, String password, String securityQuestion, String answer) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.answer = answer;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
