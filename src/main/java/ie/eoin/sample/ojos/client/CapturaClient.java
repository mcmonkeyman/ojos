package ie.eoin.sample.ojos.client;


import ie.eoin.sample.ojos.api.ImageRequest;
import ie.eoin.sample.ojos.api.ImageResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CapturaClient {

    final HttpClient httpClient;
    final static Logger log = LoggerFactory.getLogger(CapturaClient.class);
//    private static final String targetHost = "http://captura.com";
//    private static final String targetPath = "capture";
    private static final String targetHost = "https://upload.wikimedia.org";
    private static final String targetPath = "wikipedia/commons/d/d9/Test.png";
    private static final String targetUrl = String.format("%s/%s", targetHost, targetPath);
    private final ImageResponse defaultImage = new ImageResponse("/images/1x1.png");
    private String imageCreationLog = "Could not create image from captura, details '%s'. returning default image to client.";
    private String requestProblemLog = "Could not contact captura. caught '%s' while processing request <%s> :=> <%s>";
    private String statusCodeLog = "Got status code %d from captura, meaning '%s'. returning default image to client.";
    private static Map<Integer, String> statusCodesToMessage = new HashMap<Integer, String>()
    {
        {
            put(205, "Dynamic size selector on page not found");
            put(400, "Request is too large");
            put(500, "Internal server error");
            put(502, "Capture page not found");
            put(503, "Capture site could not be contacted");
            put(504, "Capture site did not load in time");
        }
    };

    public CapturaClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public ImageResponse getImage(ImageRequest request) {

        HttpGet httpGet = new HttpGet(buildUrlString(request));
        ImageResponse result = defaultImage;

        try {
            ResponseHandler<ImageResponse> responseHandler = new ResponseHandler<ImageResponse>(){
                @Override
                public ImageResponse handleResponse(final HttpResponse response) {

                    int status = response.getStatusLine().getStatusCode();
                    System.out.println(status);
                    if (status == 200) {
                        HttpEntity entity = response.getEntity();
                        try {
                            System.out.println("got in here");

//                    java.nio.file.Path outputPath = FileSystems.getDefault().getPath("src/main/resources/assets", fileName);

                            String newImageLocation = String.format("/Users/eoconnor/code/dropwizard-experiment/ojos/src/main/resources/assets/images/%s.png", getImageName("thing"));
                            InputStream instream = entity.getContent();
                            OutputStream outstream = new FileOutputStream(newImageLocation);
                            org.apache.commons.io.IOUtils.copy(instream, outstream);
                            outstream.close();
                            instream.close();
                            return new ImageResponse(newImageLocation);

                        } catch (Exception e) {
                            System.out.println("threw exception");
                            e.printStackTrace();
                            log.debug(String.format(imageCreationLog, e.toString()));
                            return defaultImage;
                        }

                    } else if(statusCodesToMessage.containsKey(status)){

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
            log.debug(String.format(requestProblemLog, "HttpResponseException", httpGet.toString(), httpResponseException.getMessage()));
        } catch (IOException ioe) {
            log.debug(String.format(requestProblemLog, "IOException", httpGet.toString(), ioe.getMessage()));
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
        b.append("&dynamic_selector_size=");
        b.append(request.getSelector());
        return b.toString();
    }

    private String getImageName(String name) {
        return String.format("%s-%s", name, UUID.nameUUIDFromBytes(name.getBytes()).toString());
    }

}