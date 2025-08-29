package com.bajaj.service;

import org.springframework.stereotype.Service;

@Service
public class SqlProblemSolver {
    
    public String determineQuestion(String regNo) {
        // Extract last two digits from regNo
        String lastTwoDigits = regNo.substring(regNo.length() - 2);
        int lastTwoDigitsInt = Integer.parseInt(lastTwoDigits);
        
        if (lastTwoDigitsInt % 2 == 1) {
            return "Question 1 - Find the second highest salary from the employees table";
        } else {
            return "Question 2 - Find the department with the highest average salary";
        }
    }
    
    public String solveSqlProblem(String regNo) {
        String lastTwoDigits = regNo.substring(regNo.length() - 2);
        int lastTwoDigitsInt = Integer.parseInt(lastTwoDigits);
        
        if (lastTwoDigitsInt % 2 == 1) {
            // Question 1: Find the second highest salary
            return "SELECT MAX(salary) FROM employees WHERE salary < (SELECT MAX(salary) FROM employees)";
        } else {
            // Question 2: Find the department with the highest average salary
            return "SELECT department_id, AVG(salary) as avg_salary FROM employees GROUP BY department_id ORDER BY avg_salary DESC LIMIT 1";
        }
    }
} 