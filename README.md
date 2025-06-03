## Aplicación de ejemplo de JMS con ActiveMQ Artemis

## Arranque del servidor ActiveMQ Artemis
```shell
./create_folders.sh
docker compose up -d
```
## Parada del servidor Artemis
```shell
docker compose down
```
## Acceso al servidor Artemis
http://localhost:8161/

Datos de acceso: admin/password

## Arranque de la aplicación Web
```shell
mvn spring-boot:run
```

## Acceso a la aplicación Web
http://localhost:8080/

## Mandar mensaje a cola
POST http://localhost:8080/queues/send

```json
{
"id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
"content": "Este es el contenido del mensaje",
"queue": "MAIN_QUEUE"
}
```
## Recoger mensajes de cola
GET http://localhost:8080/queues/MAIN_QUEUE




## Referencias
Documentación Docker ActiveMQ Artemis: https://activemq.apache.org/components/artemis/documentation/latest/docker.html
Ejemplo de referencia: https://github.com/ZaTribune/springboot-activemq-artemis-example


