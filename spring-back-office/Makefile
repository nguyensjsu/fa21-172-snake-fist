all: clean

clean:
	gradle clean

compile:
	gradle build

test:
	gradle test

jar: 
	gradle build -x test
	gradle bootJar

run: jar
	echo Starting Spring at:  http://localhost:8080
	java -jar build/libs/spring-back-office-1.0.jar

# Docker

docker-build: jar
	docker build -t spring-back-office .
	docker images

docker-run: docker-build
	docker run --name spring-back-office -td -p 80:8080 spring-back-office	
	docker ps

docker-clean:
	docker stop spring-back-office
	docker rm spring-back-office
	docker rmi spring-back-office

docker-shell:
	docker exec -it spring-back-office bash 

docker-push:
	docker login
	docker build -t miguelg719/spring-back-office:v1 -t miguelg719/spring-back-officel:v1 .
	docker push miguelg719/spring-back-officel:v1 
# Compose

network-ls:
	docker network ls 

network-create:
	docker network create --driver bridge spring-back-office-v1_network

network-prune:
	docker network prune

compose-up:
	docker-compose up --scale gumball=2 -d

compose-down:
	docker-compose down 	

lb-stats:
	echo "user = admin | password = admin"
	open http://localhost:1936

lb-test:
	open http://localhost


# Pod

pod-run:
	kubectl apply -f pod.yaml

pod-list:
	kubectl get pods

pod-desc:
	kubectl describe pods spring-back-office

pod-delete:
	kubectl delete -f pod.yaml

pod-shell:
	kubectl exec -it spring-back-office -- /bin/bash

pod-logs:
	kubectl logs -f spring-back-office

# Deployment

deployment-create:
	kubectl create -f deployment.yaml --save-config 

deployment-get:
	kubectl get deployments

deployment-get-pods:
	kubectl get pods -l name=spring-back-office

deployment-pod-shell:
	kubectl exec -it $(pod) -- /bin/bash

deployment-upgrade:
	kubectl apply  -f deployment.yaml

deployment-delete:
	kubectl delete deployment spring-back-office-deployment

# Service

service-create:
	kubectl create -f service.yaml

service-get:
	kubectl get services

service-get-ip:
	kubectl get service spring-back-office -o wide

service-delete:
	kubectl delete service spring-back-office

# Ingress

ingress-apply:
	kubectl apply -f ingress.yaml

ingress-ip:
	kubectl get ingress spring-back-office-ingress




