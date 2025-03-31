# receipt-processor-challenge
A simple receipt processor api


This Sprint Boot application is composed of two endpoints.

# Endpoint: Process Receipts
- Path: /receipts/process
- Method: POST
- Payload: Receipt JSON
- Response: JSON containing an id for the receipt

This endpoint takes in a JSON receipt and returns a JSON object with a random generated ID.
The ID returned can be passed into the other endpoint at /receipts/{id}/points to get the number of points the receipt was awarded.

Example Response:
{ "id": "7fb1377b-b223-49d9-a31a-5a02701dd310" }

# Endpoint: Get Points
- Path: /receipts/{id}/points
- Method: GET
- Response: A JSON object containing the number of points awarded.

A getter endpoint that looks up the receipt by the ID returned by /receipts/process and returns an object specifying the points awarded.

Example Response:
{ "points": 32 }

# Running the Receipt API using Docker
First use
./mvnw clean package
to create a jar file in the target directory

In the main directory, you can build the Docker image using
docker build -t receipt-processor .

Then you can run the docker image using
docker run -p 8080:8080 receipt-processor

This runs the image at https://localhost:8080

# Running the Receipt API without Docker
Again first use ./mvnw clean package to create a jar file in the target directory

In the main directory, you can run the jar file using
java -jar target/challenge-0.0.1-SNAPSHOT.jar

This runs the API at https://localhost:8080

# Testing the API
Running the command 
./mvnw clean package
will run the defined JUnit tests in the repository

After running the image or jar files (with Docker and without Docker), you can use the curl command to post JSON at the endpoint.

Example command:
curl -X POST http://localhost:8080/receipts/process \
  -H "Content-Type: application/json" \
  -d '{
  "retailer": "Target",
  "purchaseDate": "2022-01-01",
  "purchaseTime": "13:01",
  "items": [
    {
      "shortDescription": "Mountain Dew 12PK",
      "price": "6.49"
    },{
      "shortDescription": "Emils Cheese Pizza",
      "price": "12.25"
    },{
      "shortDescription": "Knorr Creamy Chicken",
      "price": "1.26"
    },{
      "shortDescription": "Doritos Nacho Cheese",
      "price": "3.35"
    },{
      "shortDescription": "   Klarbrunn 12-PK 12 FL OZ  ",
      "price": "12.00"
    }
  ],
  "total": "35.35"
  }'

Running this command should return a JSON object with a key of "id" and a value of the id generated.

Verify that navigating to /receipts/{id}/points gets you a JSON object containing the number of points for that receipt id.

