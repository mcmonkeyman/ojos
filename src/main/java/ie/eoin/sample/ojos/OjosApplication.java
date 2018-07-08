package ie.eoin.sample.ojos;

import org.apache.http.client.HttpClient;

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
  }

  @Override
  public void run(OjosConfiguration configuration, Environment environment) {

    final HttpClient httpClient =
        new HttpClientBuilder(environment)
            .using(configuration.getHttpClientConfiguration())
            .build("HttpClient");

    final ImageResource resource =
        new ImageResource(
            configuration.getTemplate(),
            configuration.getDefaultName(),
            new CapturaClient(httpClient));

    environment.jersey().register(resource);
  }
}
