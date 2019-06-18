// -----------------------------------com.instaclustr.provisioningapi.Node.java-----------------------------------

package com.instaclustr.provisioningapi;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"id",
"size",
"rack",
"publicAddress",
"privateAddress",
"nodeStatus",
"sparkMaster",
"sparkJobserver",
"zeppelin"
})
public class Node {

@JsonProperty("id")
private String id;
@JsonProperty("size")
private String size;
@JsonProperty("rack")
private String rack;
@JsonProperty("publicAddress")
private String publicAddress;
@JsonProperty("privateAddress")
private String privateAddress;
@JsonProperty("nodeStatus")
private String nodeStatus;
@JsonProperty("sparkMaster")
private Boolean sparkMaster;
@JsonProperty("sparkJobserver")
private Boolean sparkJobserver;
@JsonProperty("zeppelin")
private Boolean zeppelin;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("id")
public String getId() {
return id;
}

@JsonProperty("id")
public void setId(String id) {
this.id = id;
}

@JsonProperty("size")
public String getSize() {
return size;
}

@JsonProperty("size")
public void setSize(String size) {
this.size = size;
}

@JsonProperty("rack")
public String getRack() {
return rack;
}

@JsonProperty("rack")
public void setRack(String rack) {
this.rack = rack;
}

@JsonProperty("publicAddress")
public String getPublicAddress() {
return publicAddress;
}

@JsonProperty("publicAddress")
public void setPublicAddress(String publicAddress) {
this.publicAddress = publicAddress;
}

@JsonProperty("privateAddress")
public String getPrivateAddress() {
return privateAddress;
}

@JsonProperty("privateAddress")
public void setPrivateAddress(String privateAddress) {
this.privateAddress = privateAddress;
}

@JsonProperty("nodeStatus")
public String getNodeStatus() {
return nodeStatus;
}

@JsonProperty("nodeStatus")
public void setNodeStatus(String nodeStatus) {
this.nodeStatus = nodeStatus;
}

@JsonProperty("sparkMaster")
public Boolean getSparkMaster() {
return sparkMaster;
}

@JsonProperty("sparkMaster")
public void setSparkMaster(Boolean sparkMaster) {
this.sparkMaster = sparkMaster;
}

@JsonProperty("sparkJobserver")
public Boolean getSparkJobserver() {
return sparkJobserver;
}

@JsonProperty("sparkJobserver")
public void setSparkJobserver(Boolean sparkJobserver) {
this.sparkJobserver = sparkJobserver;
}

@JsonProperty("zeppelin")
public Boolean getZeppelin() {
return zeppelin;
}

@JsonProperty("zeppelin")
public void setZeppelin(Boolean zeppelin) {
this.zeppelin = zeppelin;
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