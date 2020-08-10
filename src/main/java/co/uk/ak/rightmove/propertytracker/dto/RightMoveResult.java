
package co.uk.ak.rightmove.propertytracker.dto;

import co.uk.ak.rightmove.propertytracker.rightmove.dto.Property;
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
    private List<Property> properties = null;
    @JsonProperty("resultCount")
    private String resultCount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("properties")
    public List<Property> getProperties() {
        return properties;
    }

    @JsonProperty("properties")
    public void setProperties(List<Property> properties) {
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
