# Secret Keeper
> an application that manages secrets

This project is an application that helps manage all sort of 
secrets (credentials, credit cards, etc ..).


## run
> Please make sure you have a postgresql database running before starting the app,
> or run it with docker-compose
### run in dev mode

`java -Dspring.profiles.active=debug SecretKeeper`

### docker build

``` bash
# build jar, image, then run it
mvn clean package && docker build -t secret-keeper . && docker run -d -p 8080:8080 -e PROFILE=development,debug --name secret-keeper && docker ps | grep "secret-keeper"
```

### docker compose

``` bash
# create an env file with postgresql credentials
echo "DB_USER=postgres\nDB_PASSWORD=postgres" > .env
# run docker-compose
docker-compose up
```

## Project description 
This project uses : 
- [Spring Boot](https://github.com/spring-projects/spring-boot)
- [Spring (MVC)](https://github.com/spring-projects/spring-framework)
- [Spring Data JPA](https://github.com/spring-projects/spring-data-jpa)
- [Liquibase](https://github.com/liquibase/liquibase)
- [TestContainers](https://github.com/testcontainers/testcontainers-java)