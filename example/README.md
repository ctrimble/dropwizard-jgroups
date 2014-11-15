# Dropwizard JGroups Example

This is a very simple example of using this package to create a cluster.

## Before You Begin

- Make sure you have build the project using the instructions in the [parent project](../).  
- These instructions assume that the example project is your current working directory.
- This example requires ports 7801, 8080, 8081, 8090, and 8091.  You can change these if needed in the configuration files.

## Running the Example

You can start up the example nodes by opening two terminals and executing

```
./target/dropwizard-jgroups server conf/local-node1.yml
```
 
in the first terminal and
 
```
./target/dropwizard-jgroups server conf/local-node2.yml
```
  
in the second terminal.  You can then get channel information from the `/channel` endpoint

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
