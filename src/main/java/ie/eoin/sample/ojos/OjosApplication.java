package ie.eoin.sample.ojos;

import org.apache.http.client.HttpClient;

import com.serviceenabled.dropwizardrequesttracker.RequestTrackerBundle;
import com.serviceenabled.dropwizardrequesttracker.RequestTrackerConfiguration;

import ie.eoin.sample.ojos.client.CapturaClient;
import ie.eoin.sample.ojos.resources.ImageResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class OjosApplication extends Application<OjosConfiguration> {

  public static void main(final String[] args) throws Exception {
    new OjosApplication().run(args);
  }

  @Override
  public String getName() {
    return "ojos";
  }

  @Override
  public void initialize(final Bootstrap<OjosConfiguration> bootstrap) {
    bootstrap.addBundle(new AssetsBundle("/assets/images", "/images"));
    bootstrap.addBundle(
        new RequestTrackerBundle<OjosConfiguration>() {
          @Override
          public RequestTrackerConfiguration getRequestTrackerConfiguration(
              OjosConfiguration configuration) {
            return configuration.getRequestTrackerConfiguration();
          }
        });
  }

  @Override
  public void run(OjosConfiguration configuration, Environment environment) {

    final HttpClient httpClient =
        new HttpClientBuilder(environment)
            .using(configuration.getHttpClientConfiguration())
            .build("HttpClient");

    final ImageResource resource =
        new ImageResource(
            new CapturaClient(
                configuration.getCapturaHost(), configuration.getCapturaPath(), httpClient));

    environment.jersey().register(resource);
  }
}
