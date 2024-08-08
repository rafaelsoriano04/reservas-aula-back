# Proyecto de Reservas

Este proyecto utiliza Gradle para construir un JAR ejecutable de Spring Boot y Docker para la contenedorización. Sigue los pasos a continuación para configurar y ejecutar el proyecto.

## Instrucciones

### 1. Construir el JAR ejecutable

Ejecuta el siguiente comando dentro de la carpeta del proyecto para construir el archivo JAR ejecutable usando Gradle:

```sh
./gradlew bootJar
```
Este comando generará un JAR ejecutable en el directorio build/libs.

### 2. Construir la imagen Docker
Crea una imagen Docker para el proyecto usando el siguiente comando:
```sh
docker build -t reservas-back:latest .
```
Este comando utiliza el Dockerfile presente en la raíz del proyecto para construir una imagen con la etiqueta reservas-back:latest.

### 3. Ejecutar el contenedor con Docker Compose
Finalmente, usa Docker Compose para iniciar los contenedores definidos en el archivo docker-compose.yml:
```sh
docker-compose up
```
Este comando iniciará todos los servicios definidos en el archivo docker-compose.yml, incluyendo el contenedor con la imagen reservas-back.
