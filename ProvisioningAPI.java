package com.instaclustr.provisioningapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ProvisioningAPI {
	
	// These are now set from Properties in setup()
	static boolean debug;
	static boolean trace;
    static long sleep;
	
	static String username = "";
	static String password = "";
	
	static String baseURL = "";
	
	static ObjectMapper mapper = null;

static void setup()
{
	
	debug = Properties.debug;
	trace = Properties.trace;
	sleep = Properties.sleep;
	
	// set up dev or prod environments
	if (Properties.devEnvironment)
	{
		username = Properties.usernameDev;
		password = Properties.passwordDev;
	}
	else
	{
		username = Properties.usernameProd;
		password = Properties.passwordProd;
	}
			
	// url
	baseURL = (Properties.devEnvironment ? "https://api.dev.instaclustr.com" : "https://api.instaclustr.com");
			
	mapper = new ObjectMapper();
}

// Creates a single Cassandra Cluster and returns ClusterID
static ClusterID createCassandraCluster(String name, String providerName, String size, String dc, String net, String racks, String nodes, String version)
{
	
	CreateCluster createClusterObject = new CreateCluster();
	createClusterObject.setClusterName(name);
	Provider provider = new Provider();
	provider.setName(providerName);
	createClusterObject.setProvider(provider);
	createClusterObject.setNodeSize(size);
	createClusterObject.setDataCentre(dc);
	createClusterObject.setClusterNetwork(net);
	RackAllocation rackAllocation = new RackAllocation();
	rackAllocation.setNumberOfRacks(racks);
	rackAllocation.setNodesPerRack(nodes);
	createClusterObject.setRackAllocation(rackAllocation);
	ArrayList<Bundle> bundles = new ArrayList<Bundle>();
	Bundle cassandraBundle = new Bundle();
	cassandraBundle.setBundle("APACHE_CASSANDRA");
	cassandraBundle.setVersion(version);
	bundles.add(cassandraBundle);
	createClusterObject.setBundles(bundles);


    String jsonString;
    ClusterID createdClusterID = null;
    
	try {
		jsonString = mapper.writeValueAsString(createClusterObject);

		if (debug) System.out.println("createCluster JSON = " + jsonString);
            
		String result = "";
		// extended is for cluster creation, all other operations don't have it.
		result = http(baseURL + "/provisioning/v1/extended/", jsonString);
            
		// If success, returns the Cluster Id:  Result={"id":"b692e7fc-9936-41a6-85bf-bf39ba3962c1"}
		if (debug) System.out.println("createCluster Result = " + result);
        createdClusterID = mapper.readValue(result, ClusterID.class);    
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	return createdClusterID;
}

// get the cluster status, returns ClusterStatus object, if any node DEFERRED exits as there nothing that can be done from the API (manual intervention is required)
static ClusterStatus getClusterStatus(String id)
{
    if (debug) System.out.println("getClusterStatus " + id);
	String clusterStatusResult = http(baseURL + "/provisioning/v1/" + id);
    if (debug) System.out.println("getClusterStatus Result = " + clusterStatusResult);
    if (clusterStatusResult.contains("DEFERRED"))
    {
    		System.out.println("getClusterStatus(): Cluster " + id + " has DEFERRED node(s), giving up");
    		System.exit(1);
    }
	ClusterStatus createdClusterStatus = null;
	try {
		createdClusterStatus = mapper.readValue(clusterStatusResult, ClusterStatus.class);
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	return createdClusterStatus;
}

// DEMO ONLY get all public IP addresses for all nodes, TODO Should only return RUNNING ips? Ok if called after cluster status has been checked with all nodes running for demo
// for production this should return a subset of IPs that match some criteria, e.g. Cassandra cluster with given name
static ArrayList<String> getIPs(String id)
{
	ArrayList<String> ips = new ArrayList<String>();
	ClusterStatus status = getClusterStatus(id);
	List<DataCentre> dataCentres = status.getDataCentres();
	for (DataCentre dc : dataCentres)
	{
		for (Node node : dc.getNodes())
			ips.add(node.getPublicAddress());
	}
	return ips;
}


static String deleteCluster(String id)
{
	if (debug) System.out.println("deleteCluster " + id);
	String result = httpDelete(baseURL + "/provisioning/v1/" + id);
    if (debug) System.out.println("deleteCluster Result = " + result);
    return result;
}

// add IP address to Cassandra cluster firewall rules, returns nothing
static String createFirewallRule(String id, String ip)
{
	String result = null;
	
	FirewallRule fireWallRule = new FirewallRule();
	fireWallRule.setNetwork(ip);
	Rule rule = new Rule();
	rule.setType("CASSANDRA");
	ArrayList<Rule> rules = new ArrayList<Rule>();
	rules.add(rule);
	fireWallRule.setRules(rules);
	
	try {
		String jsonString = mapper.writeValueAsString(fireWallRule);
		if (debug) System.out.println("create Firewall rule JSON = " + jsonString);
		result = http(baseURL + "/provisioning/v1/" + id + "/firewallRules", jsonString);
		if (debug) System.out.println("create Firewall rule result = " + result);
		
	} catch (JsonProcessingException e) {
		e.printStackTrace();
	}
	return result;
}

//add IP address to Cassandra cluster firewall rules
static FirewallRulesStatus listFirewallRules(String id)
{
	String result = null;
	FirewallRulesStatus status = null;
	
	if (debug) System.out.println("list Firewall rules " + id);
	result = http(baseURL + "/provisioning/v1/" + id + "/firewallRules");
	if (debug) System.out.println("list Firewall rules result = " + result);
	
    try {
    		status = mapper.readValue(result, FirewallRulesStatus.class);
	} catch (IOException e) {
		e.printStackTrace();
	}    

	return status;
}

// simpler version which just returns a String
static String stringListFirewallRules(String id)
{
	String result = null;
	FirewallRulesStatus status = null;
	
	if (debug) System.out.println("list Firewall rules " + id);
	result = http(baseURL + "/provisioning/v1/" + id + "/firewallRules");
	if (debug) System.out.println("list Firewall rules result = " + result);
	return result;
}

// after Cluster creation request, wait until all cluster is RUNNING
// prints progress %, this version incremental, only prints if different to previous
static void waitUntilRUNNING(String id)
{
	 ClusterStatus createdClusterStatus = null;
	 double oldProgress = -1.0;
     while (true)
     {
     		try {
					Thread.sleep(sleep);		            
					createdClusterStatus = getClusterStatus(id);
					double newProgress = checkProgress(createdClusterStatus);
					if (newProgress != oldProgress)
						System.out.print("progress = " + newProgress + "%");
					else System.out.print(".");
					oldProgress = newProgress;
		            if (debug) System.out.println("Created Cluster Status = " + createdClusterStatus.getClusterStatus());
					if (createdClusterStatus.getClusterStatus().equalsIgnoreCase("RUNNING"))
						return;
				} catch (InterruptedException e) {
					e.printStackTrace();
				};
     }
}

// Same as above but prints progress value each poll
static void waitUntilRUNNINGNonIncremental(String id)
{
	 ClusterStatus createdClusterStatus = null;
     while (true)
     {
     		try {
					Thread.sleep(sleep);		            
					createdClusterStatus = getClusterStatus(id);
					System.out.println("progress = " + checkProgress(createdClusterStatus) + "%");
		            if (debug) System.out.println("Created Cluster Status = " + createdClusterStatus.getClusterStatus());
					if (createdClusterStatus.getClusterStatus().equalsIgnoreCase("RUNNING"))
						return;
				} catch (InterruptedException e) {
					e.printStackTrace();
				};
     }
}

// wait until all firewall rules are running
// TODO This version just checks for RUNNING String, should parse correctly but bug, or check if any are not RUNNING (what is the value?)
// Had issues with JSON to java object mapping, need to check/generate FirewallRulesStatus.java and FirewallRule.java (was possibly using version without status).
static void waitUntilFirewallRulesRUNNING(String id)
{
	while (true)
	{
		try {
			String result = stringListFirewallRules(id);
            if (debug) System.out.println("list fire wall result  = " + result);
            if (result.contains("RUNNING"))
    				return;
            Thread.sleep(sleep);		 
		} catch (InterruptedException e) {
			e.printStackTrace();
		};
	}
}
	
	// method to check progress, returns percentage of nodes in RUNNING state
	static double checkProgressCall(String id)
	{
		int totalNodes = 0;
		int runningNodes = 0;
		
		String clusterStatusResult = http(baseURL + "/provisioning/v1/" + id);
		ClusterStatus createdClusterStatus;
		try {
			createdClusterStatus = mapper.readValue(clusterStatusResult, ClusterStatus.class);
			List<DataCentre> dataCentres = createdClusterStatus.getDataCentres();
			for (DataCentre dc : dataCentres)
			{
				// now get all nodes, count total and number RUNNING
				for (Node node : dc.getNodes())
				{
					// A node is resized if size == requested size and nodeStatus == RUNNING
					if (node.getNodeStatus().equalsIgnoreCase("RUNNING")) runningNodes++;
					totalNodes++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (double)(runningNodes/totalNodes)*100.0;
	}
	
	static String resizeDataCentre(String id, String dc, String concurrency, String newSize)
	{
		 // concurrentResizes is the number of racks that can be resized concurrently
		ResizeDataCentre resizeObject = new ResizeDataCentre();
        resizeObject.setConcurrentResizes(concurrency);
		resizeObject.setNewNodeSize(newSize);
		resizeObject.setNotifySupportContacts("false");
		
		String result = null;
	    String jsonString;
		try {
			jsonString = mapper.writeValueAsString(resizeObject);
			
			if (trace) System.out.println("Resize Data Centre " + dc + " to " + newSize);
	        if (debug) System.out.println("resizeDataCentre " + id + ", " + dc + " JSON = " + jsonString);
        		result = http(baseURL + "/provisioning/v1/" + id + "/" + dc  + "/resize", jsonString);
    	        if (debug) System.out.println("resizeDataCentre result = " + result);
		} catch (JsonProcessingException e) {
				e.printStackTrace();
		}
    	        
    	    return result;
	}
	
	// version which just takes JSON and doesn't make a call
	static double checkProgress(ClusterStatus createdClusterStatus)
	{
		double totalNodes = 0;
		double runningNodes = 0;
		
		List<DataCentre> dataCentres = createdClusterStatus.getDataCentres();
		for (DataCentre dc : dataCentres)
		{
			// now get all nodes, count total and number RUNNING
			for (Node node : dc.getNodes())
			{
				// A node is resized if size == requested size and nodeStatus == RUNNING
				if (node.getNodeStatus().equalsIgnoreCase("RUNNING")) runningNodes++;
				totalNodes++;
				if (debug) System.out.println("DEBUG checkProgress " + runningNodes + ", " + totalNodes);
			}
		}
		return (runningNodes/totalNodes)*100.0;
	}
	
	static double checkResizeProgress(ClusterStatus createdClusterStatus, String requestedNodeSize)
	{
		double totalNodes = 0;
		double runningNodes = 0;
		
		List<DataCentre> dataCentres = createdClusterStatus.getDataCentres();
		for (DataCentre dc : dataCentres)
		{
			// now get all nodes, count total and number RUNNING
			for (Node node : dc.getNodes())
			{				
				// A node is resized if size == requested size and nodeStatus == RUNNING
				if (node.getSize().equalsIgnoreCase(requestedNodeSize) &&
					node.getNodeStatus().equalsIgnoreCase("RUNNING")
					) runningNodes++;
				totalNodes++;
				if (debug) System.out.println("DEBUG checkProgress " + runningNodes + ", " + totalNodes);
			}
		}
		return (runningNodes/totalNodes)*100.0;
	}
	
	// check single DC resize progress
	static double checkDCResizeProgress(DataCentre dc, String requestedNodeSize)
	{
		double totalNodes = 0;
		double runningNodes = 0;
		
		// now get all nodes, count total and number RUNNING
		for (Node node : dc.getNodes())
		{				
			// A node is resized if size == requested size and nodeStatus == RUNNING
			if (node.getSize().equalsIgnoreCase(requestedNodeSize) &&
				node.getNodeStatus().equalsIgnoreCase("RUNNING")
				) runningNodes++;
			totalNodes++;
			if (debug) System.out.println("DEBUG checkProgress " + runningNodes + ", " + totalNodes);
		}
		
		return (runningNodes/totalNodes)*100.0;
	}

	// Low level methods for HTTP: http POST request
	public static String http(String url, String body)
	{
    	
    		CredentialsProvider provider = new BasicCredentialsProvider();
    		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
    		provider.setCredentials(AuthScope.ANY, credentials);

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build()) {
        
            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity(body);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);

            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            return json;
          
            } catch (Exception e) {
            }
		return null;
    }
	
	
	// http GET request
	public static String http(String url)
	{
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
		provider.setCredentials(AuthScope.ANY, credentials);
	    	
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build()) {
	        	
	         	HttpResponse response = httpClient.execute(new HttpGet(url));
		    		int statusCode = response.getStatusLine().getStatusCode();
		    		if (debug) System.out.println("status = " + statusCode);
		    		// TODO 202 status means JSON is valid, throw exception if not 202?
		    		String json = EntityUtils.toString(response.getEntity(), "UTF-8");
		    		return json;       	
	          
	            } catch (Exception e) {
	            }
		return null;
	}
	
	// http DELETE request
	public static String httpDelete(String url)
	{    	
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
		provider.setCredentials(AuthScope.ANY, credentials);
		    	
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build()) {
		        	
		         	HttpResponse response = httpClient.execute(new HttpDelete(url));
			    		int statusCode = response.getStatusLine().getStatusCode();
			    		if (debug) System.out.println("status = " + statusCode);
			    		// TODO 202 status means JSON is valid, throw exception if not 202?
			    		String json = EntityUtils.toString(response.getEntity(), "UTF-8");
			    		return json;       	
		          
		            } catch (Exception e) {
		                // TODO: handle exception
		            }
		return null;
	}
}
