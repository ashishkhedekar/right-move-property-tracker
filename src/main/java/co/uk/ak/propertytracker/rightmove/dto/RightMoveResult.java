
package co.uk.ak.propertytracker.rightmove.dto;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "properties",
    "resultCount"
})
public class RightMoveResult {

    @JsonProperty("properties")
    private List<RightMoveProperty> properties = null;
    @JsonProperty("resultCount")
    private String resultCount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("properties")
    public List<RightMoveProperty> getProperties() {
        return properties;
    }

    @JsonProperty("properties")
    public void setProperties(List<RightMoveProperty> properties) {
        this.properties = properties;
    }

    @JsonProperty("resultCount")
    public String getResultCount() {
        return resultCount;
    }

    @JsonProperty("resultCount")
    public void setResultCount(String resultCount) {
        this.resultCount = resultCount;
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
