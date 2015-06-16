# example-spring-jdbc

This example code shows how to use spring jdbc with VoltDB. The sample only shows CRUD operations on a single table whose schema is as below:

```sql
create TABLE CONTESTANTS (
firstname VARCHAR(200)
, lastname VARCHAR(200)
, code VARCHAR(20) NOT NULL
);
```

To build and run the sample:

1. Start a VoltDB instance and create schema with above sql.
2. Install gradle
3. run gradle clean run which will run the sample client using spring jdbc 

The Main Class is SpringJDBCTemplateTest.java

Last but not least feel free to send us feedback.

