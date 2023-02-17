# ec_readyapi_poc
## Getting started:
Running docker-compose up -d will spin up all necessary containers for this POC to run locally.

##### MS SQL setup
In the container bash interface, enter the following commands line by line: 

````/opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P "Passw0rd123"````

````CREATE DATABASE TestDB;````

````GO````

````USE TestDB;````

````CREATE TABLE Inventory (id INT PRIMARY KEY, name NVARCHAR(50), quantity INT);````


#### Containers used:
* MSSQL 2022
* IBM MQ 9.3
* Kafka server (and ZooKeeper)
* SFTP Server
* Castle Mock for mocking REST and SOAP endpoints.

### POC Requirements: 
* SOAP requests
* Kafka integration
* MQ integration
  * JNDI
  * JMS JAR
* MS SQL integration
* Reporting
