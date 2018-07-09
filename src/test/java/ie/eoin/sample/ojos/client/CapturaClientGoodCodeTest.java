package ie.eoin.sample.ojos.client;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import ie.eoin.sample.ojos.api.ImageRequest;
import ie.eoin.sample.ojos.api.ImageResponse;

public class CapturaClientGoodCodeTest extends Mockito {

  private final ImageRequest emptyRequest = new ImageRequest("", "");
  private final ImageResponse defaultResponse = new ImageResponse("/images/1x1.png");
  private Path currentRelativePath = Paths.get("").toAbsolutePath().normalize();
  private File imageFile =
      new File(currentRelativePath.toString() + "/src/test/resources/images/banana.png");

  @Test
  public void should_return_default_for_good_status_code() {
    // given
    HttpResponse httpResponse = mock(HttpResponse.class);
    StatusLine statusLine = mock(StatusLine.class);
    EntityBuilder builder = EntityBuilder.create();

    builder.setFile(imageFile);
    HttpEntity entity = builder.build();

    // and
    when(statusLine.getStatusCode()).thenReturn(200);
    when(httpResponse.getStatusLine()).thenReturn(statusLine);
    when(httpResponse.getEntity()).thenReturn(entity);

    // and:
    HttpClient httpClient = new FakeHttpClient(httpResponse);
    CapturaClient client = new CapturaClient("", "", httpClient);

    // then:
    ImageResponse result = client.getImage(emptyRequest);
    Assert.assertNotEquals(result.getImageLocation(), defaultResponse.getImageLocation());
  }
}
