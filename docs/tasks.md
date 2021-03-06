# Must
* [Done] Build capture route
* [Done] Build capture service 
* Add Tests
  * [Done] Test Captura Service
  * Test route
  * Test serialization
* [Done] Add formatter
* Validation
  * Json response modelled with errors
  * graceful error handling
  * Validate css selector 
* Request Logging
  * [Done] generate uuid per request
  * Postgres db with requests
  * /stats/domain/
  * /stats
* [Done] Build fake Captura
* [Done] Clean up config
* Abstract image saving service


# Nice to Have
* Health Check
* Image caching?
* Load testing
* Build other capture routes
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
