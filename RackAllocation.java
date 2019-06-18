// -----------------------------------com.instaclustr.provisioningapi.RackAllocation.java-----------------------------------

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
"numberOfRacks",
"nodesPerRack"
})
public class RackAllocation {

@JsonProperty("numberOfRacks")
private String numberOfRacks;
@JsonProperty("nodesPerRack")
private String nodesPerRack;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("numberOfRacks")
public String getNumberOfRacks() {
return numberOfRacks;
}

@JsonProperty("numberOfRacks")
public void setNumberOfRacks(String numberOfRacks) {
this.numberOfRacks = numberOfRacks;
}

@JsonProperty("nodesPerRack")
public String getNodesPerRack() {
return nodesPerRack;
}

@JsonProperty("nodesPerRack")
public void setNodesPerRack(String nodesPerRack) {
this.nodesPerRack = nodesPerRack;
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