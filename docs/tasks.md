# Must
* [Done] Build capture route
* [Done] Build capture service 
* Add Tests
  * Test Captura Service
  * Test route
  * Test serialization
* Add formatter
* Validation
  * Json response modelled with errors
  * graceful error handling
  * Validate css selector 
* Request Logging
  * generate uuid and pass in header of request to captura and response to user
  * Postgres db with requests
  * /stats/domain/
  * /stats
* Build fake Captura
* Build other capture routes


# Nice to Have
* Health Check
* Image caching?
* Load testing
* Authentication and Authorisation
* OAuth
* Swagger [support](https://github.com/swagger-akka-http/swagger-akka-http)
* README?
* Dockerized
* Rate limiting
* Rollbar

# Design choices
* Save images locally and redirect
OR
* Save images on service and redirect 
OR 
* Stream image back

# Questions
* Is there a real service Captura is based on? If so is there an associated website?
  * Yes, no websit
* What HTTP method does the captura service take?
  * Get
* Does caputra return the image or a link to the image or a redirect to the image?
  * Png Image

# Observations
* Captura seem like it might be misusing some of the HTTP status codes
* Is what we are doing a good idea?
