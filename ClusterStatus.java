// -----------------------------------com.instaclustr.provisioningapi.ClusterStatus.java-----------------------------------

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
"clusterName",
"clusterStatus",
"cassandraVersion",
"username",
"instaclustrUserPassword",
"slaTier",
"clusterCertificateDownload",
"dataCentres"
})
public class ClusterStatus {

@JsonProperty("id")
private String id;
@JsonProperty("clusterName")
private String clusterName;
@JsonProperty("clusterStatus")
private String clusterStatus;
@JsonProperty("cassandraVersion")
private String cassandraVersion;
@JsonProperty("username")
private String username;
@JsonProperty("instaclustrUserPassword")
private String instaclustrUserPassword;
@JsonProperty("slaTier")
private String slaTier;
@JsonProperty("clusterCertificateDownload")
private String clusterCertificateDownload;
@JsonProperty("dataCentres")
private List<DataCentre> dataCentres = null;
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

@JsonProperty("clusterName")
public String getClusterName() {
return clusterName;
}

@JsonProperty("clusterName")
public void setClusterName(String clusterName) {
this.clusterName = clusterName;
}

@JsonProperty("clusterStatus")
public String getClusterStatus() {
return clusterStatus;
}

@JsonProperty("clusterStatus")
public void setClusterStatus(String clusterStatus) {
this.clusterStatus = clusterStatus;
}

@JsonProperty("cassandraVersion")
public String getCassandraVersion() {
return cassandraVersion;
}

@JsonProperty("cassandraVersion")
public void setCassandraVersion(String cassandraVersion) {
this.cassandraVersion = cassandraVersion;
}

@JsonProperty("username")
public String getUsername() {
return username;
}

@JsonProperty("username")
public void setUsername(String username) {
this.username = username;
}

@JsonProperty("instaclustrUserPassword")
public String getInstaclustrUserPassword() {
return instaclustrUserPassword;
}

@JsonProperty("instaclustrUserPassword")
public void setInstaclustrUserPassword(String instaclustrUserPassword) {
this.instaclustrUserPassword = instaclustrUserPassword;
}

@JsonProperty("slaTier")
public String getSlaTier() {
return slaTier;
}

@JsonProperty("slaTier")
public void setSlaTier(String slaTier) {
this.slaTier = slaTier;
}

@JsonProperty("clusterCertificateDownload")
public String getClusterCertificateDownload() {
return clusterCertificateDownload;
}

@JsonProperty("clusterCertificateDownload")
public void setClusterCertificateDownload(String clusterCertificateDownload) {
this.clusterCertificateDownload = clusterCertificateDownload;
}

@JsonProperty("dataCentres")
public List<DataCentre> getDataCentres() {
return dataCentres;
}

@JsonProperty("dataCentres")
public void setDataCentres(List<DataCentre> dataCentres) {
this.dataCentres = dataCentres;
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