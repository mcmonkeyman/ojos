package ie.eoin.sample.ojos;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.serviceenabled.dropwizardrequesttracker.RequestTrackerConfiguration;

import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;

public class OjosConfiguration extends Configuration {

  @NotEmpty private String capturaHost;
  @NotEmpty private String capturaPath;
  private RequestTrackerConfiguration requestTrackerConfiguration =
      new RequestTrackerConfiguration();

  @JsonProperty
  public String getCapturaHost() {
    return capturaHost;
  }

  @JsonProperty
  public void setCapturaHost(String capturaHost) {
    this.capturaHost = capturaHost;
  }

  @JsonProperty
  public String getCapturaPath() {
    return capturaPath;
  }

  @JsonProperty
  public void setCapturaPath(String capturaPath) {
    this.capturaPath = capturaPath;
  }

  @Valid @NotNull @JsonProperty
  private HttpClientConfiguration httpClient = new HttpClientConfiguration();

  public HttpClientConfiguration getHttpClientConfiguration() {
    return httpClient;
  }

  public RequestTrackerConfiguration getRequestTrackerConfiguration() {
    return requestTrackerConfiguration;
  }

  public void setRequestTrackerConfiguration(RequestTrackerConfiguration configuration) {
    this.requestTrackerConfiguration = configuration;
  }
}
