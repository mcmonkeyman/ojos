package ie.eoin.sample.ojos.api;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageRequest {
    private String url;
    private String selector;

    public ImageRequest() {
        // Jackson deserialization
    }

    public ImageRequest(String url, String selector) {
        this.url = url;
        this.selector = selector;
    }

    @JsonProperty
    public String getUrl() { return url; }

    @JsonProperty
    public String getSelector() {
        return selector;
    }

    @Override
    public String toString() {
        return "ImageRequest{" + "url=" + url + ", selector='" + selector+ '\'' + '}';
    }
}
