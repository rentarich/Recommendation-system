# Ukazi

http://20.102.34.64/recommendation/v1/persons/1/recommend

- filter, pagination, sorting, partial responses (**REQUIRED** category field)

## MS 
```http://localhost:3333/v1/persons/{personId}/recommend```

## RUN IN DOCKER NETWORK:
### MS: 
```docker run --network rentarich-network -d --name recommendation -p 3333:3333 bc29cb71f5c2 -e broken=true```

### BAZA: 
``` docker run --network rentarich-network -d --name database -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=rentarich -p 5432:5432 postgres:13```

### Docker config:
```add to docker run: -e broken=true```

### Consul:
1. run ```consul agent -dev```
2. run MS
3. go to localhost:8500
4. copy config path from terminal
5. set new value
6. test

### ETCD

clone https://github.com/henszey/etcd-browser
```
cd to repo
docker build -t etcd-browser .
docker run --rm --name etcd-browser -p 0.0.0.0:8000:8000 --env ETCD_HOST=URL ETCD_PORT=PORT --env AUTH_PASS=doe -t -i etcd-browser
```

### K8S
1. install [Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli-windows?tabs=azure-cli)
2. open cmd
2. ```az aks install-cli```
3. ```az login```
4. ```az aks get-credentials --resource-group Rentarich_group --name Rentarich```
5. ```kubectl get nodes```
6. cd to your kubernetes deployment.yaml
7. ```kubectl create -f recommendation-deployment.yaml```
8. ```kubectl apply -f recommendation-deployment.yaml```
9. ```kubectl get services```
10.  EXTERNAL-IP:[PORT]/[REST path] in browser

```bash
kubectl get services
kubectl get deployments
kubectl get pods
kubectl logs recommendation-deployment-7d6b4b5c7d-qqn9v
kubectl delete pod [image name]
``` 

### HEALTH
``` http://localhost:3333/health/live``` 

### METRICS
``` http://localhost:3333/metrics``` 

header ```Accept: application/json```

### INGRESS
```
kubectl get services --namespace ingress-basic
kubectl apply -f <DEPL>.yaml --namespace ingress-basic
kubectl apply -f .\K8S\ingres.yaml --namespace ingress-basic
``` 

### OpenAPI
http://localhost:3333/api-specs/ui

http://20.62.179.11/recommendation/api-specs/v1/openapi.json

http://20.102.25.188:3333/api-specs/ui/?url=http://20.102.25.188:3333/api-specs/v1/openapi.json
