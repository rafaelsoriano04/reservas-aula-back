# Proyecto de Reservas

Este proyecto utiliza Gradle para construir un JAR ejecutable de Spring Boot y Docker para la contenedorización. Sigue
los pasos a continuación para configurar y ejecutar el proyecto.

## Desplegar a Producción

### 1. Construir la imagen Docker

Crea una imagen Docker para el proyecto usando el siguiente comando:

```sh
docker build -t reservas-aulas-back:latest .
```

Este comando utiliza el Dockerfile presente en la raíz del proyecto para construir una imagen con la etiqueta
reservas-aulas-back:latest.

Configuración del Entorno de Desarrollo

### 2. Crear la base de datos MySQL

Asegúrate de que la base de datos MySQL llamada `gestor-reservas` esté creada y disponible para el proyecto.

### 3. Ejecutar el contenedor con Docker Compose

Finalmente, usa Docker Compose para iniciar los contenedores definidos en el archivo docker-compose.yml:

```sh
docker-compose up
```

Este comando iniciará todos los servicios definidos en el archivo docker-compose.yml, incluyendo el contenedor con la
imagen reservas-aulas-back.

## Configuración del Entorno de Desarrollo

Para configurar el entorno de desarrollo en Spring, asegúrate de que el perfil activo sea dev. Puedes establecer el
perfil activo en tu archivo de configuración `application.yml` como sigue:

### En `application.yml`

```yaml
spring:
  profiles:
    active: dev
```

Este ajuste configurará el perfil activo de Spring Boot para que use las propiedades definidas para el entorno de
desarrollo.
