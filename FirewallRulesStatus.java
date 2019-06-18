// -----------------------------------com.instaclustr.provisioningapi.FirewallRuleStatus.java-----------------------------------

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
"network",
"rules"
})
public class FirewallRulesStatus {

@JsonProperty("network")
private String network;
@JsonProperty("rules")
private List<RuleStatus> rules = null;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("network")
public String getNetwork() {
return network;
}

@JsonProperty("network")
public void setNetwork(String network) {
this.network = network;
}

@JsonProperty("rules")
public List<RuleStatus> getRules() {
return rules;
}

@JsonProperty("rules")
public void setRules(List<RuleStatus> rules) {
this.rules = rules;
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
