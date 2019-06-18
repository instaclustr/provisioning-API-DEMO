
// -----------------------------------com.instaclustr.provisioningapi.ProvisioningAPI.java-----------------------------------

package com.instaclustr.provisioningapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"clusterName",
"bundles",
"provider",
"slaTier",
"nodeSize",
"dataCentre",
"clusterNetwork",
"rackAllocation",
"firewallRules"
})
public class CreateCluster {

@JsonProperty("clusterName")
private String clusterName;
@JsonProperty("bundles")
private List<Bundle> bundles = null;
@JsonProperty("provider")
private Provider provider;
@JsonProperty("slaTier")
private String slaTier;
@JsonProperty("nodeSize")
private String nodeSize;
@JsonProperty("dataCentre")
private String dataCentre;
@JsonProperty("clusterNetwork")
private String clusterNetwork;
@JsonProperty("rackAllocation")
private RackAllocation rackAllocation;
@JsonProperty("firewallRules")
private List<FirewallRule> firewallRules = null;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("clusterName")
public String getClusterName() {
return clusterName;
}

@JsonProperty("clusterName")
public void setClusterName(String clusterName) {
this.clusterName = clusterName;
}

@JsonProperty("bundles")
public List<Bundle> getBundles() {
return bundles;
}

@JsonProperty("bundles")
public void setBundles(List<Bundle> bundles) {
this.bundles = bundles;
}

@JsonProperty("provider")
public Provider getProvider() {
return provider;
}

@JsonProperty("provider")
public void setProvider(Provider provider) {
this.provider = provider;
}

@JsonProperty("slaTier")
public String getSlaTier() {
return slaTier;
}

@JsonProperty("slaTier")
public void setSlaTier(String slaTier) {
this.slaTier = slaTier;
}

@JsonProperty("nodeSize")
public String getNodeSize() {
return nodeSize;
}

@JsonProperty("nodeSize")
public void setNodeSize(String nodeSize) {
this.nodeSize = nodeSize;
}

@JsonProperty("dataCentre")
public String getDataCentre() {
return dataCentre;
}

@JsonProperty("dataCentre")
public void setDataCentre(String dataCentre) {
this.dataCentre = dataCentre;
}

@JsonProperty("clusterNetwork")
public String getClusterNetwork() {
return clusterNetwork;
}

@JsonProperty("clusterNetwork")
public void setClusterNetwork(String clusterNetwork) {
this.clusterNetwork = clusterNetwork;
}

@JsonProperty("rackAllocation")
public RackAllocation getRackAllocation() {
return rackAllocation;
}

@JsonProperty("rackAllocation")
public void setRackAllocation(RackAllocation rackAllocation) {
this.rackAllocation = rackAllocation;
}

@JsonProperty("firewallRules")
public List<FirewallRule> getFirewallRules() {
return firewallRules;
}

@JsonProperty("firewallRules")
public void setFirewallRules(List<FirewallRule> firewallRules) {
this.firewallRules = firewallRules;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}