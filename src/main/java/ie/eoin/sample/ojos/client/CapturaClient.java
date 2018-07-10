package ie.eoin.sample.ojos.client;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ie.eoin.sample.ojos.api.ImageRequest;
import ie.eoin.sample.ojos.api.ImageResponse;

public class CapturaClient {

  private HttpClient httpClient;
  static Logger log = LoggerFactory.getLogger(CapturaClient.class);
  private String targetHost;
  private String targetPath;
  private String targetUrl;
  private ImageResponse defaultImage = new ImageResponse("/images/1x1.png");
  private String imageCreationLog =
      "Created image from captura, details '%s'. returning default image to client.";
  private String problemImageCreationLog =
      "Could not create image from captura, details '%s'. returning default image to client.";
  private String requestProblemLog =
      "Could not contact captura. caught '%s' while processing request <%s> :=> <%s>";
  private String statusCodeLog =
      "Got status code %d from captura, meaning '%s'. returning default image to client.";
  private static Map<Integer, String> statusCodesToMessage =
      new HashMap<Integer, String>() {
        {
          put(205, "Dynamic size selector on page not found");
          put(400, "Request is too large");
          put(500, "Internal server error");
          put(502, "Capture page not found");
          put(503, "Capture site could not be contacted");
          put(504, "Capture site did not load in time");
        }
      };
  private Path currentRelativePath = Paths.get("").toAbsolutePath().normalize();
  private String imageFileBasePath =
      Paths.get(currentRelativePath.toString(), "src", "main", "resources", "assets").toString();
  private String imageFilePath = "images";

  public CapturaClient(String host, String path, HttpClient httpClient) {
    this.targetHost = host;
    this.targetPath = path;
    this.targetUrl = String.format("%s/%s", targetHost, targetPath);
    this.httpClient = httpClient;
  }

  public ImageResponse getImage(ImageRequest request) {

    HttpGet httpGet = new HttpGet(buildUrlString(request));
    ImageResponse result = defaultImage;

    try {
      ResponseHandler<ImageResponse> responseHandler =
          new ResponseHandler<ImageResponse>() {
            @Override
            public ImageResponse handleResponse(final HttpResponse response) {

              int status = response.getStatusLine().getStatusCode();
              if (status == 200) {
                HttpEntity entity = response.getEntity();
                try {
                  String imageName = getImageName();
                  String newImageLocation =
                      String.format("%s/%s/%s", imageFileBasePath, imageFilePath, imageName);
                  InputStream instream = entity.getContent();
                  OutputStream outstream = new FileOutputStream(newImageLocation);
                  org.apache.commons.io.IOUtils.copy(instream, outstream);
                  outstream.close();
                  instream.close();
                  log.debug(String.format(imageCreationLog, imageFilePath));

                  return new ImageResponse(String.format("%s/%s", imageFilePath, imageName));

                } catch (Exception e) {
                  System.out.println("threw exception");
                  e.printStackTrace();
                  log.debug(String.format(problemImageCreationLog, e.toString()));
                  return defaultImage;
                }

              } else if (statusCodesToMessage.containsKey(status)) {

                log.debug(String.format(statusCodeLog, status, statusCodesToMessage.get(status)));
                return defaultImage;
              } else {

                log.debug(String.format(statusCodeLog, status, ""));
                return defaultImage;
              }
            }
          };
      result = httpClient.execute(httpGet, responseHandler);
    } catch (HttpResponseException httpResponseException) {
      log.debug(
          String.format(
              requestProblemLog,
              "HttpResponseException",
              httpGet.toString(),
              httpResponseException.getMessage()));
    } catch (IOException ioe) {
      log.debug(
          String.format(requestProblemLog, "IOException", httpGet.toString(), ioe.getMessage()));
    } finally {
      httpGet.releaseConnection();
    }
    return result;
  }

  private String buildUrlString(ImageRequest request) {
    StringBuilder b = new StringBuilder();
    b.append(targetUrl);
    b.append("?url=");
    b.append(request.getUrl());
    if (request.getSelector() != null && !request.getSelector().isEmpty()) {
      b.append("&dynamic_selector_size=");
      b.append(request.getSelector());
    }
    return b.toString();
  }

  private String getImageName() {
    return String.format("%s.png", UUID.randomUUID().toString());
  }
}
