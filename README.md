# REACTIVE SPRING
Práctica Reactive Spring curso Micael Gallego
4 Octubre de 2020

<h2>Enunciado</h2>

La aplicación ofrecerá 7 endpoints REST, a saber:
<ul>
    <li>Recuperación de todas las entradas</li>
    <li>Recuperación de una entrada por su id</li>
    <li>Creación de una nueva entrada</li>
    <li>Borrado de una entrada existente</li>
    <li>Modificiación de uno o varios campos simples de la entrada (sin modificar la lista de comentarios)</li>
    <li>Creación de un nuevo comentario asociado a una entrada</li>
    <li>Borrado de un comentario existente</li>
</ul>

Se usará como base de datos MongoDB.<br>
Se deberán crear tests usando WebTesClient. 

<h2>MongoDB</h2>

Para utilizar la base de datos MongoDB se ha utilizado la imagen de Docker Hub
https://hub.docker.com/_/mongo

<h3>Arranque</h3>
Para arrancar ejecutaremos:<br>
<code>docker-compose up</code><br>

<h3>Credenciales - Collection posts-comments</h3>

Las credenciales del usuario se han creado con mongo
Accedemos al contenedor de Mongo, levantado anteriormente con docker-compose<br>
<code>docker exec -it src_mongo_1 bash</code>

Ejecutamos la consola de MongoDB<br>
<code>mongo</code>

Dentro de la console escribimos los comandos siguientes:<br>
<code>use pract</code><br>
<code>b.createUser({user: "edrf", pwd:"pwd", roles:[{role: "readWrite", db:"post-comments"} ]})</code>

<h3>Propiedades MongoDb Spring Boot</h3>

En el fichero application.properties es necesario incluir las propiedades datos siguientes:
spring.data.mongodb.database=posts-comments
spring.data.mongodb.username=edrf
spring.data.mongodb.password=pwd
spring.data.mongodb.authentication-database=pract
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017


<h2>Postman collection</h2>
En la carpeta de resources de test se ha incluido una colección de Postman para probar todos los endpoints del API

<h5>Curso Project Reactor.postman_collection.json</h5>
