
package co.uk.ak.rightmove.propertytracker.dto;

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
    "images",
    "mainImageSrc",
    "mainMapImageSrc"
})
public class PropertyImages {

    @JsonProperty("images")
    private List<Image> images = null;
    @JsonProperty("mainImageSrc")
    private String mainImageSrc;
    @JsonProperty("mainMapImageSrc")
    private String mainMapImageSrc;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("images")
    public List<Image> getImages() {
        return images;
    }

    @JsonProperty("images")
    public void setImages(List<Image> images) {
        this.images = images;
    }

    @JsonProperty("mainImageSrc")
    public String getMainImageSrc() {
        return mainImageSrc;
    }

    @JsonProperty("mainImageSrc")
    public void setMainImageSrc(String mainImageSrc) {
        this.mainImageSrc = mainImageSrc;
    }

    @JsonProperty("mainMapImageSrc")
    public String getMainMapImageSrc() {
        return mainMapImageSrc;
    }

    @JsonProperty("mainMapImageSrc")
    public void setMainMapImageSrc(String mainMapImageSrc) {
        this.mainMapImageSrc = mainMapImageSrc;
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
