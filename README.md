# Dropwizard JGroups Integration

This project gives a basic example of clustering Dropwizard applications using JGroups.  The first iteration
simply builds a cluster and provides details about membership, with no real functionality.

# Building and Running the Example

This project builds with Java8 and Maven 3.  Simply clone the repo and run

```
mvn clean install
```

this will create the dropwizard application at `target/dropwizard-jgroups`.  You can start up the example nodes
by opening two terminals and executing

```
./target/dropwizard-jgroups server conf/local-node1.yml
```
 
in the first terminal and
 
```
./target/dropwizard-jgroups server conf/local-node2.yml
```
  
then you can see the membership by curling the `/channel` endpoint

```
curl http://localhost:8080/channel
```

which will return cluster information like

```
{
  "state" : "CONNECTED",
  "name" : "ctrimble-2-35553",
  "view" : {
    "members" : [ "ctrimble-2-35553", "ctrimble-2-57467" ],
    "viewId" : { }
  },
  "clusterName" : "test",
  "discardOwnMessages" : false
}
```
