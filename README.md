# Azure Application Insights with Spring Boot 2 using Micrometer Registry Azure

You can see more about this case (step by step) 
in https://dev.to/silviobuss/

This project uses a database, if you already have mysql installed on the machine,
you can use it by changing the settings in the `application.properties` file.

## Start Mysql with Docker (OPTIONAL)
To initialize a docker container with mysql, use the command below:

docker run --name mysql57 -p 3306: 3306 -e MYSQL_ROOT_PASSWORD = root -e MYSQL_USER = user -e MYSQL_PASSWORD = user1234 -e MYSQL_DATABASE = demo_app -d mysql / mysql-server: 5.7

If you want to access the container to make any query:

docker exec -it mysql57 bash

and login to the mysql instance:

mysql -h localhost -u root -p

## Getting Started
 
Just update the database properties in `application.properties` and run the DemoApplication.java in your IDE.


