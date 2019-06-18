package com.instaclustr.provisioningapi;

import java.util.ArrayList;

// Demo of ProvisioningAPI, uses ProvisioningAPI for main operations, plus helper methods in this class.

import java.util.List;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.datastax.driver.core.*;

public class ProvisioningAPIDemo {
	
	 
public static void main(String[] args) {
		
		// Demo of a subset of the Instaclustr Provisioning API methods
		// change properties in Properties.java, and call setup() first
		// Designed to show cluster creation, and resizing from smallest resizeable instance to next size up
		
		ProvisioningAPI.setup();
		
		long startTime = System.currentTimeMillis();
		
		System.out.println("Welcome to the automated Instaclustr Provisioning API Demonstration");
		System.out.println("We're going to Create a cluster, check it, add a firewall rule, resize the cluster, then delete it");

		// Create Cluster
		// args: name, provider name, node size, data centre, cluster network, number of racks, nodes per rack, cassandra Version
		ClusterID createdClusterID = ProvisioningAPI.createCassandraCluster("DemoCluster1", "AWS_VPC", "resizeable-small(r4-l)", "US_EAST_1", "10.224.0.0/12", "3", "1", "apache-cassandra-3.11.4.ic1");
	    String id = createdClusterID.getId();
	    System.out.println("STEP 1: Create cluster ID = " + id);	 
	    
	    // Wait until the cluster is RUNNING
	    System.out.println("Wait until cluster is running...");
	    ProvisioningAPI.waitUntilRUNNING(id);
	    
	    // How long did cluster creation take?
		long now = System.currentTimeMillis();
		long time = (now-startTime)/1000;
	    System.out.println("\nFinished cluster creation in time = " + time + "s");
	    
	    // add an IP address to firewall rules
	    // What IP address? Set the address to use in Properties. This code used local host address but this doesn't work as not public ip address.
	    
	    /*
        InetAddress ipAddress;
		try {
			// ipAddress = InetAddress.getLocalHost();
			// String ip = ipAddress.getHostAddress();
			
			String CIDR = myPublicIP + "/32";
			createFirewallRuleDemo(id, CIDR);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		*/
	    
	    String CIDR = Properties.ipAddressToAdd + "/32";
	    System.out.println("STEP 2: Create firewall rule");
		createFirewallRuleDemo(id, CIDR);
		
		// Print out public IP addresses of the nodes (assuming only a single cluster was created)
		ArrayList<String> ips = ProvisioningAPI.getIPs(id);
		System.out.print("STEP 3 (Info): get IP addresses of cluster: ");
		for (String ip : ips)
			System.out.print(ip + " ");
		
		// Check by trying to connect to the cluster via public IPs, optional
		if (Properties.runClusterCheck)
		{
			System.out.print("\nSTEP 4 (Info): Check connecting to cluster...");
			boolean ok = checkClusterIPs(ips);
			System.out.println("Cluster check via public IPs = " + ok);
		}
		else System.out.println("Skipping optional cluster check step");
	    
	    // resize and wait until done
		System.out.println("STEP 5: Resize cluster...");
	    resizeClusterDemo(id);
	    
	    System.out.println("STEP 6: Delete cluster...");
	    System.out.println("Deleting cluster " + id);
	    String result = ProvisioningAPI.deleteCluster(id);
	    System.out.println("Delete Cluster result = " + result);
	                  
	    
	    now = System.currentTimeMillis();
		time = (now-startTime)/1000;
	    System.out.println("*** Instaclustr Provisioning API DEMO completed in " + time + "s, Goodbye!");
	}

static void createFirewallRuleDemo(String id, String ip)
{
	System.out.println("create Filrewall Rule " + id);
	
	String createFirewallRuleResult = ProvisioningAPI.createFirewallRule(id, ip);
	
	// Result is null
	//System.out.println("Create Firewall Rule result = " + createFirewallRuleResult);
    
	// FirewallRulesStatus status = listFirewallRules(id);
	
	long startTime = System.currentTimeMillis();

	ProvisioningAPI.waitUntilFirewallRulesRUNNING(id);
	
	// String net = status.getNetwork();
	// System.out.println("net = " + net);

	// returns [{"network":"0.0.0.0/0","rules":[{"type":"CASSANDRA","status":"RUNNING"}]}]
	// so need to find each ip address to check if RUNNING
	long now = System.currentTimeMillis();
	long time = (now-startTime)/1000;
    System.out.println("Finished firewall rule create in time = " + time + "s");
}

	// given a resizeable cluster of resizeable-small(r4-l), resize to next size up, resizeable-small(r4-xl)
	// takes id of the cluster
	static void resizeClusterDemo(String id)
	{
     // docs https://www.instaclustr.com/instaclustr-dynamic-resizing-for-apache-cassandra/
     // What's concurrent setting do?
     // Nodes can be resized one at a time, or concurrently.  Concurrent resizing allows up to one rack at a time to be replaced for faster overall resizing.
     // with only 3 racks and 3 nodes this won't speed things up using higher value?
     // 
     // resize (not documented) can only resize from small to small or large to large resizeable types
     // small: resizeable-small(r4-l), resizeable-small(r4-xl), resizeable-small(r4-2xl)	
     // large: resizearesizeClusterDemoble-large(r4-l), resizeable-large(r4-xl)	, resizeable-large(r4-2xl), resizeable-large(r4-4xl)	
     /*
      * POST /provisioning/v1/{clusterId}/{clusterDatacentreId}/resize
      *
	{
	"newNodeSize": "resizeable-small(r4-xl)",
	"concurrentResizes" : "1",
	"notifySupportContacts" : "false" 
	}
      */
     
	 ClusterStatus createdClusterStatus = ProvisioningAPI.getClusterStatus(id);
     List<DataCentre> dataCentres = createdClusterStatus.getDataCentres();
                  
     String targetSize = "resizeable-small(r4-xl)";
     
     long startTime = System.currentTimeMillis();
     
	 // resize each data centre
     for (DataCentre dataCentre : dataCentres)
     {
    	 		double oldProgress = -1.0;
     		String resizeResult = ProvisioningAPI.resizeDataCentre(id, dataCentre.getId(), "1", targetSize);
   
     		// sleep 10 s and wait until all nodes are resized and have nodeStatus RUNNING
     		while (true)
     		{
     			try {
						Thread.sleep(ProvisioningAPI.sleep);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
     			createdClusterStatus = ProvisioningAPI.getClusterStatus(id);
				List<DataCentre> dataCentres2 = createdClusterStatus.getDataCentres();
				DataCentre dataCentre2 = null;
				for (DataCentre dc : dataCentres2)
				{
					if (dc.getId().equalsIgnoreCase(dataCentre.getId()))
					{
						dataCentre2 = dc;
						break;
					}
				}
				
				// check progress of the discovered datacentre
				double progress = ProvisioningAPI.checkDCResizeProgress(dataCentre2, targetSize);
				if (progress != oldProgress)
					System.out.print("progress = " + progress + "%");
				else System.out.print(".");
				oldProgress = progress;
				
				if (progress >= 100.0)
				{
					// System.out.println("Data centre + " + dataCentre.getId() + " resized.");
					break;
				}
     		}
     		System.out.println("\nResized data centre Id = " + dataCentre.getId() + " to " + targetSize);
     }
     long now = System.currentTimeMillis();
     long time = (now-startTime)/1000;
     
	 System.out.println("Total resizing time = " + time + "s");
}

// Check that we can connect to the cluster with the public IPs, returns true or false
static boolean checkClusterIPs(ArrayList<String> ips)
{	
	boolean ok = false;
	
	Cluster cluster = null;
	if (ProvisioningAPI.debug) System.out.println("TESTING Cluster using public IP = " + ips.get(0));
	
	try {
	    cluster = Cluster.builder()                                                    
	            .addContactPoint(ips.get(0))
	            .build();
	    
	    Metadata metadata = cluster.getMetadata();
	    System.out.println("\nTESTING Cluster via public IP: Got metadata, cluster name = " + metadata.getClusterName());
	    Session session = cluster.connect();                                           
	    ResultSet rs = session.execute("select release_version from system.local");
	    Row row = rs.one();
	    System.out.println("TESTING Cluster via public IP: Connected, got release version = " + row.getString("release_version"));   
	    ok = true;
	} catch (Exception e) {}
	finally {
	    if (cluster != null) cluster.close();                                          
	}
	
	return ok;
}

}
