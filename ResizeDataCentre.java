// -----------------------------------com.instaclustr.provisioningapi.ResizeDataCentre.java-----------------------------------

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
"newNodeSize",
"concurrentResizes",
"notifySupportContacts"
})
public class ResizeDataCentre {

@JsonProperty("newNodeSize")
private String newNodeSize;
@JsonProperty("concurrentResizes")
private String concurrentResizes;
@JsonProperty("notifySupportContacts")
private String notifySupportContacts;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("newNodeSize")
public String getNewNodeSize() {
return newNodeSize;
}

@JsonProperty("newNodeSize")
public void setNewNodeSize(String newNodeSize) {
this.newNodeSize = newNodeSize;
}

@JsonProperty("concurrentResizes")
public String getConcurrentResizes() {
return concurrentResizes;
}

@JsonProperty("concurrentResizes")
public void setConcurrentResizes(String concurrentResizes) {
this.concurrentResizes = concurrentResizes;
}

@JsonProperty("notifySupportContacts")
public String getNotifySupportContacts() {
return notifySupportContacts;
}

@JsonProperty("notifySupportContacts")
public void setNotifySupportContacts(String notifySupportContacts) {
this.notifySupportContacts = notifySupportContacts;
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