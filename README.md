## EXPLORE

- This is Job portal application, where different job opportunities are posted by different companies. 

### Points:

- This is a typical microservice based backend application.
- It has 4 microservices (Security service, Job service, Company service, Review Service), a Service registry and an API gateway.
- This application uses **Spring boot, Spring Security, Spring cloud, PostgreSQL, RabbitMQ and Docker** to run.
- **Jwt** authentication and authorization is included in the application.
- It has a role based authentication and authorization.
- Uses gateway to intercept the requests.
- **RabbitMQ** message broker is used for the purpose of MessageQueues.
- **Feign Clients** are used to have a conversation between the services.
- User can able to provide feedback to the company.
- Deployed to the docker repository.
- Feel free to pull the images, copy the docker-compose.yaml file which is inside the company service and test the application.