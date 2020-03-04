Project Overview:
Warehouse is spring boot application with embedded tomcat and in-memory H2 Database using maven as build tool.
Tables and Scehmas are created on application start-up.
All the required commands to store, sell and find products in warehouse are implemented as REST API's and API's are called using RESTTemplate as RESTClient
Data Stored in tables while application is running can be viewed through H2-console with details given below:

username=su
password=password
spring.h2.console.path=localhost:8080/h2-console


To Launch the application execute the script warehouse.sh :
Script will build the project, run the test cases and execute the program
User can interact with prompt using commands mentioned as per requirements
User can contiue to type commands untill command named "exit" to stop further commands.
