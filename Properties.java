package com.instaclustr.provisioningapi;

public class Properties {
	
	// ip address that will be added to cluster firewall rules, needs to be public IP address of the machine this code is running on for cassandra connect test to work
	static String ipAddressToAdd = "220.245.32.246"; // office public ip address
	
	// if true try to connect to the cluster, else omit this step (there may be potential problems getting the correct public ip address to add to firewall rules, and connecting from a remove machine)
	// all the other steps use the provisioning API which is HTTP, this steps uses the Cassandra Java client
	static boolean runClusterCheck = true; 
	
	static boolean debug = false;  // low level call/result debugging
	static boolean trace = true;  // higher level call only tracing
	
    static long sleep = 10000; // SLA for provisioning API is 1 call per 2.5s, else 429 error, sleeping 10s should be safe
    
	static boolean devEnvironment = false;	// if false then production environment (production is best for demo)
	
	// credentials for Instaclustr Provisioning API, only need the credentials corresponding to the environment used (dev/prod)
	// dev
	static String usernameDev = "username";
	static String passwordDev = "key";
		
	// production
	static String usernameProd = "username";
	static String passwordProd = "key";
}
