package ie.eoin.sample.ojos.client;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

/**
 * Fake implementation of {@link HttpClient} that always returns the same response no matter what is
 * executed.
 */
@SuppressWarnings("deprecation")
public class FakeHttpClient implements HttpClient {
  private final Queue<HttpResponse> responses;

  public FakeHttpClient(int code) {
    // given
    HttpResponse response = mock(HttpResponse.class);
    StatusLine statusLine = mock(StatusLine.class);

    // and
    when(statusLine.getStatusCode()).thenReturn(code);
    when(response.getStatusLine()).thenReturn(statusLine);

    // then
    this.responses = new LinkedList<>(Collections.singletonList(response));
  }

  public FakeHttpClient(final HttpResponse response) {
    this.responses = new LinkedList<>(Collections.singletonList(response));
  }

  public FakeHttpClient(final Queue<HttpResponse> responses) {
    this.responses = responses;
  }

  @Override
  public org.apache.http.params.HttpParams getParams() {
    throw new UnsupportedOperationException("We don't test deprecated methods");
  }

  @Override
  public org.apache.http.conn.ClientConnectionManager getConnectionManager() {
    throw new UnsupportedOperationException("We don't test deprecated methods");
  }

  @Override
  public HttpResponse execute(HttpUriRequest request) throws IOException, ClientProtocolException {
    return responses.poll();
  }

  @Override
  public HttpResponse execute(HttpUriRequest request, HttpContext context)
      throws IOException, ClientProtocolException {
    return responses.poll();
  }

  @Override
  public HttpResponse execute(HttpHost target, HttpRequest request)
      throws IOException, ClientProtocolException {
    return responses.poll();
  }

  @Override
  public HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context)
      throws IOException, ClientProtocolException {
    return responses.poll();
  }

  @Override
  public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler)
      throws IOException, ClientProtocolException {
    return responseHandler.handleResponse(responses.poll());
  }

  @Override
  public <T> T execute(
      HttpUriRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context)
      throws IOException, ClientProtocolException {
    return responseHandler.handleResponse(responses.poll());
  }

  @Override
  public <T> T execute(
      HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler)
      throws IOException, ClientProtocolException {
    return responseHandler.handleResponse(responses.poll());
  }

  @Override
  public <T> T execute(
      HttpHost target,
      HttpRequest request,
      ResponseHandler<? extends T> responseHandler,
      HttpContext context)
      throws IOException, ClientProtocolException {
    return responseHandler.handleResponse(responses.poll());
  }
}
