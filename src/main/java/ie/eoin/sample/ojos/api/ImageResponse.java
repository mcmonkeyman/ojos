package ie.eoin.sample.ojos.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageResponse {
    private String imageLocation;

    public ImageResponse() {
        // Jackson deserialization
    }

    public ImageResponse(String imageLocation){
        this.imageLocation = imageLocation;
    }

    @JsonProperty
    public String getImageLocation() { return imageLocation; }

    @Override
    public String toString()
    {
        return "ImageResponse{" + "imageLocation='" + imageLocation + "'}";
    }
}
