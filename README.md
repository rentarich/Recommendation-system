# Ukazi

## RUN IN DOCKER NETWORK:
MS: docker run --network rentarich-network -d --name recommendation -p 3333:3333 bc29cb71f5c2
BAZA: docker run --network rentarich-network -d --name database -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=rentarich -p 5432:5432 postgres:13
