package ie.eoin.sample.ojos.resources;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.concurrent.atomic.AtomicLong;

import javax.imageio.ImageIO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ie.eoin.sample.ojos.api.ImageRequest;
import ie.eoin.sample.ojos.api.ImageResponse;
import ie.eoin.sample.ojos.client.CapturaClient;

// import java.util.Optional;

@Path("/capture")
@Produces(MediaType.APPLICATION_JSON)
public class ImageResource {
  private final String template;
  private final String defaultName;
  private final AtomicLong counter;
  private final CapturaClient capturaClient;

  public ImageResource(String template, String defaultName, CapturaClient client) {
    this.template = template;
    this.defaultName = defaultName;
    this.counter = new AtomicLong();
    this.capturaClient = client;
  }

  @Path("/redirect")
  @GET
  public Response getRedirectImage(
      @QueryParam("url") String url, @QueryParam("selector") String selector) throws Exception {
    ImageResponse response = capturaClient.getImage(new ImageRequest(url, selector));
    return Response.seeOther(new URI(response.getImageLocation())).build();
  }

  // TODO Exception Handling
  @Path("/default")
  @GET
  @Produces("image/png")
  public Response getDefaultImage() throws Exception {

    BufferedImage image =
        ImageIO.read(ImageResource.class.getResourceAsStream("/assets/images/1x1.png"));

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(image, "png", baos);
    byte[] imageData = baos.toByteArray();

    // uncomment line below to send non-streamed
    return Response.ok(imageData).build();

    // uncomment line below to send streamed
    //        return Response.ok(new ByteArrayInputStream(imageData)).build();
  }
}
