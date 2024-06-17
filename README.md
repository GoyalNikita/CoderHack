# CoderHack Leaderboard API

This project is a RESTful API service for managing the leaderboard of a coding contest platform using Spring Boot and MongoDB. The service allows for CRUD operations on user registrations and provides functionality to update scores and assign badges based on the users' scores.

# Author 
Nikita Goyal

## Table of Contents
- [Requirements](#Requirements)
- [Endpoints](#endpoints)
- [Validation and Error Handling](#validation-and-error-handling)
- [Getting Started](#getting-started)
- [Running the Tests](#running-the-tests)
- [Postman Collection](#postman-collection)
## Requirements

- The API must handle CRUD operations for user registrations.
- Each user has the following fields:
  - `User ID` (Unique Identifier)
  - `Username`
  - `Score` (0 <= Score <= 100)
  - `Badges` (Code Ninja, Code Champ, Code Master)
- User registration requests must have a `User ID` and `Username`. The score must be 0, and the badges must be empty initially after the registration.
- Scores can be updated through PUT requests.
- Badges are awarded based on the score:
  - 1 <= Score < 30 -> Code Ninja
  - 30 <= Score < 60 -> Code Champ
  - 60 <= Score <= 100 -> Code Master
- A user can only have a maximum of three unique badges.
- User retrieval must be sorted based on the score with a time complexity of O(nlogn).
- Include basic JUnit test cases to verify the operations.

## Endpoints

- `GET /users` - Retrieve a list of all registered users.
- `GET /users/{userId}` - Retrieve the details of a specific user.
- `POST /users` - Register a new user to the contest.
- `PUT /users/{userId}` - Update the score of a specific user.
- `DELETE /users/{userId}` - Deregister a specific user from the contest.

## Validation and Error Handling

- Basic validation for all fields (e.g., `Score > 0`).
- Handle common errors and return appropriate HTTP codes (e.g., `404 User not found`).

## Getting Started

### Prerequisites

- Java 11 or higher
- Gradle
- MongoDB

### Setup

1. Clone the repository:
    ```bash
    git clone https://github.com/GoyalNikita/CoderHack.git
    cd CoderHack
    ```

2. Install dependencies and build the project:
    ```bash
    ./gradlew build
    ```

3. Run the application:
    ```bash
    ./gradlew bootRun
    ```

4. The API will be available at `http://localhost:8080`.

### Configuration

- Update `application.properties` with your MongoDB configurations if needed:
    ```properties
    spring.data.mongodb.uri=mongodb://localhost:27017/your-db
    ```

## Running the Tests

Run the following command to execute the tests:
```bash
./gradlew test
```

## Postman Collection

You can use the following Postman Collection to test the API:
[CoderHack Leaderboard API Postman Collection](https://red-firefly-736811.postman.co/workspace/New-Team-Workspace~446ac2dc-32b8-4882-b3de-4aba165c8ad5/collection/36335262-d1d8e412-a08a-43ec-90f1-766093c6d161?action=share&creator=36335262)

