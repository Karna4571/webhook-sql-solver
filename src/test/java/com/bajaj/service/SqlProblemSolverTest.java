package com.bajaj.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class SqlProblemSolverTest {

    private SqlProblemSolver sqlProblemSolver;

    @BeforeEach
    void setUp() {
        sqlProblemSolver = new SqlProblemSolver();
    }

    @Test
    void testDetermineQuestion_OddRegistrationNumber() {
        String regNo = "REG12347";
        String question = sqlProblemSolver.determineQuestion(regNo);
        assertEquals("Question 1 - Find the second highest salary from the employees table", question);
    }

    @Test
    void testDetermineQuestion_EvenRegistrationNumber() {
        String regNo = "REG12348";
        String question = sqlProblemSolver.determineQuestion(regNo);
        assertEquals("Question 2 - Find the department with the highest average salary", question);
    }

    @Test
    void testSolveSqlProblem_OddRegistrationNumber() {
        String regNo = "REG12347";
        String solution = sqlProblemSolver.solveSqlProblem(regNo);
        assertEquals("SELECT MAX(salary) FROM employees WHERE salary < (SELECT MAX(salary) FROM employees)", solution);
    }

    @Test
    void testSolveSqlProblem_EvenRegistrationNumber() {
        String regNo = "REG12348";
        String solution = sqlProblemSolver.solveSqlProblem(regNo);
        assertEquals("SELECT department_id, AVG(salary) as avg_salary FROM employees GROUP BY department_id ORDER BY avg_salary DESC LIMIT 1", solution);
    }

    @Test
    void testEdgeCases() {
        // Test with single digit
        assertEquals("Question 1 - Find the second highest salary from the employees table", 
            sqlProblemSolver.determineQuestion("REG12341"));
        
        // Test with zero
        assertEquals("Question 2 - Find the department with the highest average salary", 
            sqlProblemSolver.determineQuestion("REG12340"));
    }
} 