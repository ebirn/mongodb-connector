
# Mongo DB resource connector

A primitive MongoDB connector, JCA 1.6 compliant
Inspired by this tutorial by Adam Bien: http://www.adam-bien.com/roller/abien/entry/project_connectorz_jca_1_6

## Modules:
### connector
the actual RAR Java EE connector

### api
the client interfaces, applications using the connector need this

### demoapp
simple Java EE application demonstrating the use of resource injection

## Installation:

1. build the rar
2. deploy as application
3. create connection pool (add configuration: mongdb:// url)
4. create connector resouce
5. use in application via resource injection:
@Resource(name="myJndi/name")
MongoConnectionFactory factory;
