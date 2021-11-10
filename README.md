# Ukazi

## RUN IN DOCKER NETWORK:
### MS: 
```docker run --network rentarich-network -d --name recommendation -p 3333:3333 bc29cb71f5c2 -e test-config=test02```

### BAZA: 
``` docker run --network rentarich-network -d --name database -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=rentarich -p 5432:5432 postgres:13```

### Docker config:
```add to docker run: -e test-config=test02```

### Consul:
1. run ```consul agent -dev```
2. run MS
3. go to localhost:8500
4. copy config path from terminal
5. set new value
6. test
