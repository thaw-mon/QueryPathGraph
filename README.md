
## 1. An Introduction to NoSQL Database Generator
* This is a tool for the generation of databases for different NoSQL technologies from the same conceptual model.

* This repository contains all transformations and code generation templates that are used to transform a UML class diagram and related query workload information into final implementation schemas in Oracle NoSQL, Cassandra and/or MongoDB.

## 2. Structure of the project

#### Entity Package
* **com.database.tool.Entity.Workload**

This is divided into input file conversion class entity information.
First, we convert the file (eg Xx. info) into ModelEntity class and QueryWorkload class. 
We save the two classes in one class, namely Input class.
Then we convert the Input information into QPG information, including the Edge, Node and QueryKeyEntity classes.

* **com.database.tool.Entity.QPG**

QueryPathInfo class: Includes Edge, Node and QueryKeyEntity.

Graph class: QueryPathGraph Graph, constructed by Edge, Node and QueryKeyEntity.

#### Service Package
* **com.database.tool.Service.Schemata**

There are three packages, which correspond to Document Schemata, ColumnFamily Schemata, and KeyValue Schemata

1. _com.database.tool.Service.Schemata.ColumnFamilyModel_

ColumnFamily Schemata generation algorithm is defined

2. _com.database.tool.Service.Schemata.DocumentModel_

The Document Schemata generation algorithm is defined

3. _com.database.tool.Service.Schemata.KeyValueModel_

The KeyValue Schemata generation algorithm is defined

###### DatabaseGenerator Tool

In the main function section, this class integrates all of the above Schemata from file to final Schemata, and you just need to call this class to complete the Schemata construction

## 3. Required packages

IDE : Java 1.8

Framework.org. Spring Framework.boot 2.3.1

## 4. Installation and usage

You can see a compiled Jar Package NoSQL-DB-Generator.jar  in the main directory

The results can be obtained from the following command line:

_java -jar NoSQL-DB-Generator.jar -path {filePath} -file {fileName} -type {schemataType}_

{filePath} is the directory of input files, we come with file directory for resources/experiments

{fileName} is the input fileName, including EAC.info OnlineStore.info DL.info

{schemataType} for generated Schemata types including KeyValue ColumnFamily Document
 
eg : java -jar NoSQL-DB-Generator.jar -path resources/experiments -file EAC.info -type ColumnFamily

the output :

Schemata :

ColumnFamilyScalarProperty{id=1, partitionKeys=[int Server_id,int Player_id], clusteringKeys=[int State_timestamp ASC,int Session_id ASC,int Sta
te_id ASC], values=[int State_posX, int State_posY, int State_posZ]}

ColumnFamilyScalarProperty{id=2, partitionKeys=[int Server_id], clusteringKeys=[int State_timestamp ASC,int Player_id ASC,int Session_id ASC,int
 State_id ASC], values=[int State_posX, int State_posY, int State_posZ]}

ColumnFamilyScalarProperty{id=3, partitionKeys=[int Server_id], clusteringKeys=[], values=[string Server_name, string Server_IP]}

ColumnFamilyScalarProperty{id=4, partitionKeys=[int Server_id], clusteringKeys=[], values=[string Server_name]}

ColumnFamilyScalarProperty{id=5, partitionKeys=[int Player_id], clusteringKeys=[int Session_id ASC], values=[]}


#### The following section is the Test method

*  com.database.tool.Service.DatabaseGenerator.QueryPathInfoTest.java

This section mainly tests from the file file to the QPG section

1. getInputInfoByDL() ： Run directly to get Input information for DL.info

2. getInputInfoByEAC() ：Directly run to get the Input information of EAC.info

3. getInputInfoByOnlineStore() ：Directly run to get the Input information of OnlineStore.info

4. getQueryPathInfoByOnlineStore() ：Run the Query Path information of OnlineStore.info directly, including Edge, Node and Query
 
5. getQueryPathInfoByEAC() ： Run the Query Path information of EAC.info directly, including Edge, Node and Query

6. getQueryPathInfoByDL() : The Query Path information of DL.info is directly run, including Edge, Node and Query

*  com.database.tool.Service.Schemata.QueryPathSchemata.java

This part is mainly completed from QPG to NoSQL Schemas

1. getCFSchemataByEAC() : Get ColumnFamily Schemata for EAC.info

2. getCFSchemataTableByEAC() ： Gets the Cassandra table information for EAC.info

3. getDocumentSchemataByOnlineStore() ： Gets the Document Schemata for OnlineStore.info

4. getDocumentSchemataTableByOnlineStore() : Get MongoDB table information for OnlineStore.info

5. getCFSchemataByDL() : Get the ColumnFamily Schemata of DL.info

6. getCFSchemataTableByDL() ： Gets the Cassandra table information for Dl.info
