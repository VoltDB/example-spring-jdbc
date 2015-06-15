# example-spring-jdbc

This example code shows how to use spring jdbc with VoltDB. The sample only shows CRUD operations on a single table whose schema is as below:

``create TABLE CONTESTANTS (
firstname VARCHAR(200)
, lastname VARCHAR(200)
, code VARCHAR(20) NOT NULL
);``
