package com.bajaj.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sql_results")
public class SqlResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String regNo;
    private String question;
    private String solution;
    
    public SqlResult() {}
    
    public SqlResult(String regNo, String question, String solution) {
        this.regNo = regNo;
        this.question = question;
        this.solution = solution;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
} 