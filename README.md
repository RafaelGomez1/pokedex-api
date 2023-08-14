# HeyTrade Pokemon Challenge

**Project description**
* This project is a Kotlin Spring Boot application created for a Backend engineering role. 
* I kept the tech stack pretty basic using
  * Kotlin 1.9
  * Arrow 1.2.0
  * Java 17
  * Spring Boot 3

---
**How to use**

* The whole API is deployed on a digital ocean droplet so there's no need to download the docker image directly if you still want to, there's instructions about how to do it below.
  In order to perform some calls to it, you have a Postman Collection with some examples on the resources folder pointing to the deployed version.
* To get the server up and running locally, you can just pull the docker image from my personal dockerhub:

        docker pull rafagomezp/pokedex-api
        docker run -d -p 8084:8084 rafagomezp/pokedex-api
  
* Once the server is running, the in memory database should be set up automatically but in case it doesn't, perform the following action:

        1. Call the Register endpoint (this will create some random pokemons and insert them into the in memory database)
* Now you can start using any of the following endpoints:

        2. Find Pokemon ->  GET /pokemons/{pokemonId}
        3. Search pokemon -> GET /pokemons/
        4. Mark pokemon as favorite -> PATCH "/pokemons/{pokemonId}/mark"
        5. Unmark pokemon as favorite -> PATCH "/pokemons/{pokemonId}/unmark"
---
**Design and Code considerations**

Here I will try to explain some of the logic or reasons for the current implementation of the project.
---
* Project Structure

  - Given the amount of time given I chose to focus on simplicity and ended up distributing the packages in a more conventional structure (main/test) where each one has all the bounded context.

  - Following DDD principles I decided to create one bounded context:

        1. Pokemon: Contains all pokemon module and logic.

  - The Pokemon bounded context is structured following Hexagonal Architecture with the following packages:

         application -> all use-cases and validations
         domain -> aggregates, entities, value objects and ports
         primary adapter -> driver layer of infrastructure splitted between controllers and subscribers
         secondary adapter -> driven layer of infrastructure containing database adapter
  - I've used a simple approach of Command/Query seggregation where the handler creates the Value Objects (which contain data validation)
    and directly calls the application service to perform the business logic passing the Value Objects as parameters.
  - We also have the testFixtures module where can find all the ObjectMothers of the bounded context.
---
* Functional Programming

  - I'm a very big fan of functional programming. I've been bringing functional concepts and techniques to my teams' 
  OOP codebase for about 2 years. In this test I used the latest version of arrow with its Raise DSL to avoid throwing exceptions
  when all we want to do is control the execution flow. There are many reason to use this approach such as Performance, Developer Experience 
  and Code Readability, but I won't go into details here.
  
  - I also used some custom-made typealiases and Higher Order Functions to help improve readability when trying to return errors on controllers.    
---
* Dependency Injection

  - Since I chose to use context receivers, there was no real need for me to create classes containing only a single method. I replaced them all with
  simple functions and as a result the only things needed to inject are the ports of each use-case.
---

* Testing Strategy
  
  - I used the approach we use at my current team where the 'unit' of the unit test is the whole logical unit instead of a single class.
  Thus making only a single unit test for every use-case, allowing for a super flexible and user-centric unit testing that holds over time and refactors easily.
  To do that, I avoided using Mocks and used Fakes instead.
  
  - Given the fact that I chose to use an in-memory database and the FakeRepository contains the exact same logic as the "real" database, 
  In this case I avoided creating any integration tests for that part of the codebase.

  - Regarding e2e tests, I couldn't make them in time so for this first PoC I also avoided creating them.
---
* Error Handling
  
  - Every use case has a sealed class containing all the possible errors that can happen in that situation. This includes
    data validation, business logic errors and an Unknown (error containing a Throwable) for any unexpected situation.
  - Those errors are being passed as a valid function (the execution flow is not interrupted) return value until they reach the
    primary adapter layer where they are converted into a standard response using spring boot's ResponseEntity API.
  - In case of an Unknown error we throw the cause of that exception so the real problem does not get lost.

---
**Improvements**
* Add support for multiple users

  Initially I wanted to create a dedicated pokedex for every user, this way the pokemons would be decoupled for the favorite logic and every single search query would be faster
  since we'd avoid extra db table joins. But given the limited amount of time and the fact that this super simple PoC might not scale at all I decided for a single user approach where
  all the functionalities are present but only one user can use the API.
---
* Dedicated database

  In order to get the most out of the API, a production ready db would be the next step.


