# parking-lot-processor

## Introduction
Spring Boot app that have 2 API endpoints available:
  1. Display the closest parking based on the provided geo coordinates (latitude and longitude).
  2. Calculate a parking score for a given latitude and longitude based on how many
parkings they have in 1km radius.

## Technologies
 * Spring Boot v3.0.5
 * Apache Camel v4.0.0-M2
 * ElasticSearch (docker image v7.10.0)
 
 ## Setup
 ### Requirements
 * Java 17
 * Docker
 * IDE of choice (recommended IntellJ IDEA)
 
 ### Run
  1. Open the cmd/bash and run the following command to pull and build the docker image with ElasticSearch cluster
  
```docker run -p 9200:9200 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.10.0```
 
  2. Clone the app and run it in IDE (as a Spring Boot app)
  3. Data ingestion: 
    Create a folder with name **in** inside the **C:/** (see **inputDir** config in **application.yml**) (Windows users) 
    For the MAC users, you need to change the value of the **inputDir** in **application.yml** and put the whatever location you choose.
  4. and the app will automatically pick it up and store the data in ElasticSearch. 
  
  Now when we have elasticSearch cluster and our app up and running and the data is present in the repository, we can now start consuming the API endpoints.
 
  ### Consuming APIs
   1. **findClosest**

       ```curl --location 'http://localhost:8080/parking-lot/closest?latitude=-89.8125&longitude=20.4612'```
       
   2. **calculateScore**
   
       ```curl --location 'http://localhost:8080/parking-lot/score?latitude=33.9855269&longitude=-118.4686714'```
       
   #### Note:
   
   APIs require auth by providing the corresponding username and password (Basic Auth) - **see the application.yml for credentials**
