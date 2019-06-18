// -----------------------------------com.instaclustr.provisioningapi.DataCentre.java-----------------------------------

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
"id",
"name",
"provider",
"cdcNetwork",
"bundles",
"clientEncryption",
"passwordAuthentication",
"userAuthorization",
"usePrivateBroadcastRPCAddress",
"privateIPOnly",
"nodes",
"nodeCount",
"encryptionKeyId",
"resizeTargetNodeSize"
})
public class DataCentre {

@JsonProperty("id")
private String id;
@JsonProperty("name")
private String name;
@JsonProperty("provider")
private String provider;
@JsonProperty("cdcNetwork")
private String cdcNetwork;
@JsonProperty("bundles")
private Object bundles;
@JsonProperty("clientEncryption")
private Boolean clientEncryption;
@JsonProperty("passwordAuthentication")
private Boolean passwordAuthentication;
@JsonProperty("userAuthorization")
private Boolean userAuthorization;
@JsonProperty("usePrivateBroadcastRPCAddress")
private Boolean usePrivateBroadcastRPCAddress;
@JsonProperty("privateIPOnly")
private Boolean privateIPOnly;
@JsonProperty("nodes")
private List<Node> nodes = null;
@JsonProperty("nodeCount")
private Integer nodeCount;
@JsonProperty("encryptionKeyId")
private Object encryptionKeyId;
@JsonProperty("resizeTargetNodeSize")
private Object resizeTargetNodeSize;
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

@JsonProperty("name")
public String getName() {
return name;
}

@JsonProperty("name")
public void setName(String name) {
this.name = name;
}

@JsonProperty("provider")
public String getProvider() {
return provider;
}

@JsonProperty("provider")
public void setProvider(String provider) {
this.provider = provider;
}

@JsonProperty("cdcNetwork")
public String getCdcNetwork() {
return cdcNetwork;
}

@JsonProperty("cdcNetwork")
public void setCdcNetwork(String cdcNetwork) {
this.cdcNetwork = cdcNetwork;
}

@JsonProperty("bundles")
public Object getBundles() {
return bundles;
}

@JsonProperty("bundles")
public void setBundles(Object bundles) {
this.bundles = bundles;
}

@JsonProperty("clientEncryption")
public Boolean getClientEncryption() {
return clientEncryption;
}

@JsonProperty("clientEncryption")
public void setClientEncryption(Boolean clientEncryption) {
this.clientEncryption = clientEncryption;
}

@JsonProperty("passwordAuthentication")
public Boolean getPasswordAuthentication() {
return passwordAuthentication;
}

@JsonProperty("passwordAuthentication")
public void setPasswordAuthentication(Boolean passwordAuthentication) {
this.passwordAuthentication = passwordAuthentication;
}

@JsonProperty("userAuthorization")
public Boolean getUserAuthorization() {
return userAuthorization;
}

@JsonProperty("userAuthorization")
public void setUserAuthorization(Boolean userAuthorization) {
this.userAuthorization = userAuthorization;
}

@JsonProperty("usePrivateBroadcastRPCAddress")
public Boolean getUsePrivateBroadcastRPCAddress() {
return usePrivateBroadcastRPCAddress;
}

@JsonProperty("usePrivateBroadcastRPCAddress")
public void setUsePrivateBroadcastRPCAddress(Boolean usePrivateBroadcastRPCAddress) {
this.usePrivateBroadcastRPCAddress = usePrivateBroadcastRPCAddress;
}

@JsonProperty("privateIPOnly")
public Boolean getPrivateIPOnly() {
return privateIPOnly;
}

@JsonProperty("privateIPOnly")
public void setPrivateIPOnly(Boolean privateIPOnly) {
this.privateIPOnly = privateIPOnly;
}

@JsonProperty("nodes")
public List<Node> getNodes() {
return nodes;
}

@JsonProperty("nodes")
public void setNodes(List<Node> nodes) {
this.nodes = nodes;
}

@JsonProperty("nodeCount")
public Integer getNodeCount() {
return nodeCount;
}

@JsonProperty("nodeCount")
public void setNodeCount(Integer nodeCount) {
this.nodeCount = nodeCount;
}

@JsonProperty("encryptionKeyId")
public Object getEncryptionKeyId() {
return encryptionKeyId;
}

@JsonProperty("encryptionKeyId")
public void setEncryptionKeyId(Object encryptionKeyId) {
this.encryptionKeyId = encryptionKeyId;
}

@JsonProperty("resizeTargetNodeSize")
public Object getResizeTargetNodeSize() {
return resizeTargetNodeSize;
}

@JsonProperty("resizeTargetNodeSize")
public void setResizeTargetNodeSize(Object resizeTargetNodeSize) {
this.resizeTargetNodeSize = resizeTargetNodeSize;
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