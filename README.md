# Pizza Toppings Service

## Introduction

This is a backend app that facilitates the publishing, aggregating and retrieving of pizza toppings and their corresponding
counts, unique by user and total, to help determine the number of toppings the business should order. 
There are two parts of this app that facilitate this, a write side and a read side. 

* Read side - under the com.lisa.luk.api package.
   * Entry point is the `/api/v1/toppings` GET endpoint.
   * This endpoint will call an in memory SQLite database and return a list of toppings & their counts, and the most popular and least popular toppings.
* Write side - under the com.lisa.luk.pipeline package.
   * Entry point is the `/api/v1/toppings/publish` GET endpoint.
   * This endpoint will grab existing toppings & their counts from the SQLite database, and then call an external API built by 
another team at Accumulus, and aggregate the data to update the toppings counts.

## How To Start

You should be able to run the app without spinning up any other containers, since the SQLite database is embedded in the app.
When testing this I mocked the external API on port 8081 that returns the list of tuples of user emails to their selected toppings.

## Versions
* [Spring Boot 3.0.5](https://www.baeldung.com/spring-boot-3-spring-6-new)
* [Kotlin 1.8.0](https://kotlinlang.org/docs/whatsnew18.html)
* [Java 17](https://www.oracle.com/java/technologies/javase/17-relnote-issues.html)
* [Apache Beam 2.46.0](https://beam.apache.org/get-started/beam-overview/)
* [SQLite JDBC 3.36.0.3](https://sqlite.org/index.html)
* [Ktorm 3.3.0](https://github.com/kotlin-orm/ktorm)

## Possible Future Enhancements & Iterations
* We may want to consider a Delete endpoint and scheduled job to clear the database after we have ordered the toppings the customers have submit.
* Another option for this is to add a column that would indicate when the data is considered to be expired, since SQLite doesn't have built-in support for TTLs.
    * This would line up with however frequently the ordering of toppings is done.
* We would want to consider another database that can be deployed and scaled independently if our data gets bigger.
* Adding an additional table to store customer information along with their previous orders.
   * This could be used to personalize pizzas recommendations for returning customers, help with easy reorders, and increase 
the accuracy of the 'unique count' of toppings since this app assumes that every new submission is a new customer when aggregating topping counts.
   * However, this would require a mechanism to be able to delete this user data as well due to California's Consumer Privacy Act.
* Features for requesting sauces, and allowing customers to submit toppings we may not have yet.