# TztExpress

## Getting Started

This project uses [Docker][docker] and [Gradle][gradle]. In order to use Docker
on your machine you should install the [Docker Toolbox][toolbox].

If you've set up Docker and gradle you can run the project as followed:
1. `docker-compose up db`
2. `gradle build`

[docker]: https://www.docker.com/
[gradle]: https://gradle.org/
[toolbox]: https://www.docker.com/docker-toolbox

### Connecting to local postgres database

If you want to connect to the local postgres database you should use the
`postgres` as username and leave the password blank (Postgres is using it's
default port `5432`).

## Running

There are two options to start this project. One way to do it with `gradle`
another way to do this is with `docker`.

### Gradle

To run the application you should use `gradle bootRun`.

### Docker

To run the application you should use `docker-compose up api db`.

## Testing

In order to test this project you should use `gradle test`
