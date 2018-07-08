# Task

The main task of this project is to build a web server (ojos) that interacts with another service (capturama) over http. The capturama API has some constraints which will be described below, but there is no expectation that portion is coded out.

## capturama

Accepts requests at `/capture` and generates an image in png format of a specified webpage. A `url` query parameter with an encoded value is always required to specify the page to generate an image from.
An additional `dynamic_size_selector` query parameter with an encoded value can be passed to specify a particular element to generate a png from rather than the entire page.
An example request to generate an image from a specific element on a specific page could look like `localhost:12345/capture?url=http%3A%2F%2Fwww.example.com%2F&dynamic_size_selector=body%20h1`.

### Status Codes

The capturama service returns HTTP status codes indicating the status of a request. Only for a 2xx level response is the png image generated.
200 => "Success"
205 => "Dynamic size selector on page not found"
400 => "Request is too large"
500 => "Internal server error"
502 => "Capture page not found"
503 => "Capture site could not be contacted"
504 => "Capture site did not load in time"

## ojos

Create an http server (ojos) that can receive an image request with a png extension, a url of a web page to capture, and possibly a css selector, returning a png image of the page or element on the page specified with the css selector.

1. Ojos needs to always return an image and a 200 status code regardless of whether an error is encountered in the capturamam service. This image should be a 1x1 pixel if it cannot capture the page.
1. All the above capturama status codes should be handled by ojos.
1. I also expect to be to get stats of the number of times a particular domain is captured. I expect these stats to be available offline at any time for my inspection.
1. I also want to assign an id to each request so that in the future I can track these requsts further down the line.

# Discussion

We'll want to talk about:
- thought process for working through the project
- initial assumptions that suprisingly did/didn't work out
- confirmation of code correctness

