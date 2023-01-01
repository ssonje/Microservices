# Microservices

Project Architecture - 

![SpringBoot  - Microservices](https://user-images.githubusercontent.com/45000608/210170545-81ab459a-9051-41a9-b3a6-457b03afdf0b.jpg)

# Major Functionalities - 

- Every entity has its own microservice so that dependency is breakable while updating and uploading the other one.
- Following microservices are present in this Project - 
  - User Microservice - for `User` related services.
  - Rating Microservice - for `Rating` related services.
  - Hotel Microservice - For `Hotel` related services.
  - APIGateway Microservice - For accessing the API's using single host.
  - Service Registry Microservice - Common server where User, Rating & Hotel services are registered. 
  - Config Server Microservice - For storing common configuration in all the microservices.
- Code is modularized in such a manner that it is easy find components such as entities, payloads, controllers, etc.
- Handled the exceptions as follows - 
  - Handled Resouce not found exception.
  - Every other exception will be captured under the Global Exception.


# Microservice Architecture - 

![SpringBoot  - Microservices (2)](https://user-images.githubusercontent.com/45000608/210173655-3b692d80-569b-4106-b47b-b65a82c3164d.jpg)

# Testing - 

- Every Component used in each microservice has been unitly tested.
- We're constantly upgrading the test cases in-order to make sure not a single edge/test case will miss out.
