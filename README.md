# tccm-hw

> homework for ataccama job application process

### Brief task description:
This task is about implementing of web-based database browser (similar to desktop application DBeaver) with basic functionality and for single database vendor only. Browser should be able to register multiple database connections and browse their data and structure.
The result should be RESTful service with its own database.
This task should be implemented in Java. Database for persistence of data is your choice.

### Solution additional information
If you want to test this application on your machine, please build the jar file (`mvn clean install`), then run `docker-compose up --build` in the root location.

Docker will run three containers: 
+ *db* is postgres database for storing connection details, 
+ *db_test* is postgres database with the generated test data table `employees`, 
+ *hw* one is this springboot application.

Under assumption that you've launched containers, documentation can be accessed on *http://localhost:8080/swagger-ui.html.*

If you want to connect to database with generated test table `employees`, please use following parameters:
 > {
 > 
 >   "databaseName": "testdb",
 >   
 >   "hostname": "db_test",
 >   
 >   "name": "testdb",
 >   
 >   "password": "passwordtest",
 >   
 >   "port": 5432,
 >   
 >   "username": "usertest"
 >   
 > }
