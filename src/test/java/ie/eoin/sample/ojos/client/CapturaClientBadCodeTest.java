package ie.eoin.sample.ojos.client;

import java.util.Arrays;
import java.util.Collection;

import org.apache.http.client.HttpClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import ie.eoin.sample.ojos.api.ImageRequest;
import ie.eoin.sample.ojos.api.ImageResponse;

@RunWith(Parameterized.class)
public class CapturaClientBadCodeTest extends Mockito {

  private final ImageRequest emptyRequest = new ImageRequest("", "");
  private final ImageResponse defaultResponse = new ImageResponse("/images/1x1.png");
  private int code;

  public CapturaClientBadCodeTest(int code) {
    this.code = code;
  }

  @Parameterized.Parameters
  public static Collection codes() {
    return Arrays.asList(new Object[][] {{205}, {400}, {500}, {502}, {503}, {504}});
  }

  @Test
  public void should_return_default_for_bad_status_codes() {
    // given
    HttpClient httpClient = new FakeHttpClient(code);

    // and:
    CapturaClient client = new CapturaClient("", "", httpClient);

    // then:
    ImageResponse result = client.getImage(emptyRequest);
    Assert.assertEquals(result.getImageLocation(), defaultResponse.getImageLocation());
  }
}
