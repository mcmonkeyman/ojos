package ie.eoin.sample.ojos.resources;

import java.net.URI;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ie.eoin.sample.ojos.api.ImageRequest;
import ie.eoin.sample.ojos.api.ImageResponse;
import ie.eoin.sample.ojos.client.CapturaClient;

@Path("/capture")
@Produces(MediaType.APPLICATION_JSON)
public class ImageResource {
  private final CapturaClient capturaClient;

  public ImageResource(CapturaClient client) {
    this.capturaClient = client;
  }

  @Path("/redirect")
  @GET
  public Response getRedirectImage(
      @QueryParam("url") String url, @QueryParam("selector") String selector) throws Exception {
    ImageResponse response = capturaClient.getImage(new ImageRequest(url, selector));
    return Response.seeOther(new URI(response.getImageLocation())).build();
  }

  @Path("/info")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @POST
  public Response getInfoResponse(ImageRequest request) {
    ImageResponse response = capturaClient.getImage(request);
    return Response.ok(response).build();
  }
}
