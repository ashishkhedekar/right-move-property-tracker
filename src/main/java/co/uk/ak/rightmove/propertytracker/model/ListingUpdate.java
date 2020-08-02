
package co.uk.ak.rightmove.propertytracker.model;

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
    "listingUpdateReason",
    "listingUpdateDate"
})
public class ListingUpdate {

    @JsonProperty("listingUpdateReason")
    private String listingUpdateReason;
    @JsonProperty("listingUpdateDate")
    private String listingUpdateDate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("listingUpdateReason")
    public String getListingUpdateReason() {
        return listingUpdateReason;
    }

    @JsonProperty("listingUpdateReason")
    public void setListingUpdateReason(String listingUpdateReason) {
        this.listingUpdateReason = listingUpdateReason;
    }

    @JsonProperty("listingUpdateDate")
    public String getListingUpdateDate() {
        return listingUpdateDate;
    }

    @JsonProperty("listingUpdateDate")
    public void setListingUpdateDate(String listingUpdateDate) {
        this.listingUpdateDate = listingUpdateDate;
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
