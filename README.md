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

## Testing

In order to test this project you should use `gradle test`
