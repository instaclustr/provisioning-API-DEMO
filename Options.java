// -----------------------------------com.instaclustr.provisioningapi.Options.java-----------------------------------

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
"authnAuthz",
"clientEncryption",
"usePrivateBroadcastRPCAddress"
})
public class Options {

@JsonProperty("authnAuthz")
private String authnAuthz;
@JsonProperty("clientEncryption")
private String clientEncryption;
@JsonProperty("usePrivateBroadcastRPCAddress")
private String usePrivateBroadcastRPCAddress;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("authnAuthz")
public String getAuthnAuthz() {
return authnAuthz;
}

@JsonProperty("authnAuthz")
public void setAuthnAuthz(String authnAuthz) {
this.authnAuthz = authnAuthz;
}

@JsonProperty("clientEncryption")
public String getClientEncryption() {
return clientEncryption;
}

@JsonProperty("clientEncryption")
public void setClientEncryption(String clientEncryption) {
this.clientEncryption = clientEncryption;
}

@JsonProperty("usePrivateBroadcastRPCAddress")
public String getUsePrivateBroadcastRPCAddress() {
return usePrivateBroadcastRPCAddress;
}

@JsonProperty("usePrivateBroadcastRPCAddress")
public void setUsePrivateBroadcastRPCAddress(String usePrivateBroadcastRPCAddress) {
this.usePrivateBroadcastRPCAddress = usePrivateBroadcastRPCAddress;
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
