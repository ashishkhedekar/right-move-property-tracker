
package co.uk.ak.rightmove.propertytracker.dto;

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
    "displayPrice",
    "displayPriceQualifier"
})
public class DisplayPrice {

    @JsonProperty("displayPrice")
    private String displayPrice;
    @JsonProperty("displayPriceQualifier")
    private String displayPriceQualifier;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("displayPrice")
    public String getDisplayPrice() {
        return displayPrice;
    }

    @JsonProperty("displayPrice")
    public void setDisplayPrice(String displayPrice) {
        this.displayPrice = displayPrice;
    }

    @JsonProperty("displayPriceQualifier")
    public String getDisplayPriceQualifier() {
        return displayPriceQualifier;
    }

    @JsonProperty("displayPriceQualifier")
    public void setDisplayPriceQualifier(String displayPriceQualifier) {
        this.displayPriceQualifier = displayPriceQualifier;
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
