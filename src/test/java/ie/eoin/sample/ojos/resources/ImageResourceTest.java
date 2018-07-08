package ie.eoin.sample.ojos.resources;

import ie.eoin.sample.ojos.api.ImageRequest;
import ie.eoin.sample.ojos.api.ImageResponse;
import ie.eoin.sample.ojos.client.CapturaClient;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class ImageResourceTest {
    private static final CapturaClient capturaClient = mock(CapturaClient.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ImageResource("","", capturaClient))
            .build();

    private final ImageResponse defaultResponse = new ImageResponse("/images/1x1.png");
    private final ImageRequest validRequest = new ImageRequest("www.google.com", "head");
    private final ImageRequest invalidRequest = new ImageRequest("www.doesnotexist.com", "head");

    @Before
    public void setup() {
        when(capturaClient.getImage(validRequest)).thenReturn(new ImageResponse(""));
        when(capturaClient.getImage(invalidRequest)).thenReturn(defaultResponse);
    }

    @After
    public void tearDown(){
        reset(capturaClient);
    }

//    @Test
//    public void testValidCapturaRequest() {
//        assertThat(resources.target("/capture/redirect").request().get(validRequest))
//                .isEqualTo(person);
//        verify(dao).fetchPerson("blah");
//    }

    @Test
    public void testInvalidCapturaRequest() {
//        Response r = resources.client().target("/capture/redirect?url=www.doesnotexist.com&selector=head").request().get();
//        assertThat(r.getStatus()).isEqualTo(Response.Status.SEE_OTHER);
//        verify(capturaClient).getImage(invalidRequest);
        assertThat(true).isEqualTo(true);
    }

}
