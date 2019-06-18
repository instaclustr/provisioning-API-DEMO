# provisioning-API-DEMO
Demo of Instaclustrs Provisioning API

Code for Demonstration of subset of steps using Instaclustr Provisioning API

Created 18 June 2019, Paul Brebner

The demonstration code is in main() in ProvisioningAPIDemo.java. This has the steps for the demo, and some helper methods.

User specific properties are in Properties.java and need to be set before running the demo.

ipAddressToAdd is a public IP address of the server where the demo will be running to add to the cluster firewall rules to enable connection from the Java Cassanandra client. 

If you don't want to run this check set runClusterCheck to false (an IP address will be added to the firewall but not checked).

set debug to true for verbose information.

sleep time is 10s by default between polls for cluster status (2.5s is minimum allowed).

devEnvironment set to false for production (best for demos)

set usernameProd and passwordProd to Instaclustr Provisioning API credentials.

Other demo specifc properties can be changed in the ProvisioningAPIDemo.java file.  The demo is currently hard coded for resizing from smallest small resizeabl instances to next size up.

The actual wrapper methods for the provisioning API are in the file ProvisioningAPI.java. They take compulsory arguments, turn them into a JSON object, call the API using low level http methods, get the result and turn it back into a Java object (most of the time, sometimes they are so simple I didn't bother, or had problems converting the JSON). 

Note that it takes to run (e.g. 1403s), and not all possible exceptions are currently handled (I saw some in dev that weren't documented). 

Here are the steps:
STEP 1: Create cluster, wait for cluster to be running
STEP 2: Create firewall rule, wait for the rule to be running
STEP 3 (Info): get IP addresses of cluster
STEP 4 (Info): Check connecting to cluster (get metadata and connect and read data)
STEP 5: Resize cluster, wait until cluster resized
STEP 6: Delete cluster

Note that the wait substeps poll the method to get the cluster status and analyse the results depending on the context (the previous step) until everything is complete and then return.

If at any point a node has DEFERRED status we give up and exit (as human intervention is required).




Here's an example output:

Welcome to the automated Instaclustr Provisioning API Demonstration
We're going to Create a cluster, check it, add a firewall rule, resize the cluster, then delete it
STEP 1: Create cluster ID = bbe52fd0-14b0-4582-a5a0-f872fb9d4d34
Wait until cluster is running...
progress = 0.0%......................progress = 33.33333333333333%.....progress = 66.66666666666666%.....progress = 100.0%
Finished cluster creation in time = 432s
STEP 2: Create firewall rule
create Filrewall Rule bbe52fd0-14b0-4582-a5a0-f872fb9d4d34
Finished firewall rule create in time = 1s
STEP 3 (Info): get IP addresses of cluster: 3.212.114.170 100.24.228.82 52.205.46.135 
STEP 4 (Info): Check connecting to cluster...
TESTING Cluster via public IP: Got metadata, cluster name = DemoCluster1
TESTING Cluster via public IP: Connected, got release version = 3.11.4
Cluster check via public IPs = true
STEP 5: Resize cluster...
Resize Data Centre a8b14c73-e234-4d28-8e68-ce405ccd6990 to resizeable-small(r4-xl)
progress = 0.0%.................................progress = 33.33333333333333%.......................progress = 66.66666666666666%....................progress = 100.0%
Resized data centre Id = a8b14c73-e234-4d28-8e68-ce405ccd6990 to resizeable-small(r4-xl)
Total resizing time = 954s
STEP 6: Delete cluster...
Deleting cluster bbe52fd0-14b0-4582-a5a0-f872fb9d4d34
Delete Cluster result = {"message":"Cluster has been marked for deletion."}
*** Instaclustr Provisioning API DEMO completed in 1403s, Goodbye!
