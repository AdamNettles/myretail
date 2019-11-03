# myretail
Simple Springboot 2 api which synthesizes data from redsky.target.com with pre-existing Cassandra data to creat a product pricing summary.  Also allows modification of pricing data.

#### Local testing
Requirements:  Docker, Docker Compose
 
1. Setup the Cassandra container
```
docker-compose up
```
2. Build and run the Springboot jar using gradle
```
./gradlew clean build bootRun
```

3. hit the endpoints from swagger
[http://localhost:8080/swagger-ui.html#/my-retail-controller](http://localhost:8080/swagger-ui.html#/my-retail-controller)

#### Instructions
myRetail is a rapidly growing company with HQ in Richmond, VA and over 200 stores across the east coast. myRetail wants to make its internal data available to any number of client devices, from myRetail.com to native mobile apps.  The goal for this exercise is to create an end-to-end Proof-of-Concept for a products API, which will aggregate product data from multiple sources and return it as JSON to the caller.

Your goal is to create a RESTful service that can retrieve product and price details by ID.  The URL structure is up to you to define, but try to follow some sort of logical convention.

Build an application that performs the following actions:

Responds to an HTTP GET request at /products/{id} and delivers product data as JSON, where {id} will be a number.  Example product IDs: `15117729, 16483589, 16696652, 16752456, 15643793`

Example response: 
```
{
  "id": 13860428,
  "name": "The Big Lebowski (Blu-ray) (Widescreen)",
  "current_price": {
    "value": 13.49,
    "currency_code": "USD"
  }
}
```
Performs an HTTP GET to retrieve the product name from an external API. (For this exercise the data will come from redsky.target.com, but let’s just pretend this is an internal resource hosted by myRetail)

Example:

http://redsky.target.com/v2/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics

Reads pricing information from a NoSQL data store and combines it with the product id and name from the HTTP request into a single response.
- BONUS: Accepts an HTTP PUT request at the same path (/products/{id}), containing a JSON request body similar to the GET response, and updates the product’s price in the data store.