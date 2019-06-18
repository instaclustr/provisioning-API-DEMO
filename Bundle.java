
// -----------------------------------com.instaclustr.provisioningapi.Bundle.java----------------------------------- 
// https://github.com/FasterXML/jackson

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
"bundle",
"version",
"options"
})
public class Bundle {

@JsonProperty("bundle")
private String bundle;
@JsonProperty("version")
private String version;
@JsonProperty("options")
private Options options;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("bundle")
public String getBundle() {
return bundle;
}

@JsonProperty("bundle")
public void setBundle(String bundle) {
this.bundle = bundle;
}

@JsonProperty("version")
public String getVersion() {
return version;
}

@JsonProperty("version")
public void setVersion(String version) {
this.version = version;
}

@JsonProperty("options")
public Options getOptions() {
return options;
}

@JsonProperty("options")
public void setOptions(Options options) {
this.options = options;
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
