package com.evry.bdd.utils;

public class SqlQueries {
    public static final String INSERT_PERSON = 
        "INSERT INTO Person.Person (BusinessEntityID, PersonType, FirstName, LastName) VALUES (?, 'EM', ?, ?)";
    public static final String SELECT_PERSON = 
        "SELECT FirstName, LastName FROM Person.Person WHERE BusinessEntityID = ?";
    public static final String UPDATE_PERSON = 
        "UPDATE Person.Person SET FirstName = ? WHERE BusinessEntityID = ?";
    public static final String DELETE_PERSON = 
        "DELETE FROM Person.Person WHERE BusinessEntityID = ?";
}
