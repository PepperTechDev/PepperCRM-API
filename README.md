# PepperCRM-API

## Deployment

[![CI/CD Pipeline for Deployment](https://github.com/PepperTechDev/PepperCRM-API/actions/workflows/deployment.yml/badge.svg)](https://github.com/PepperTechDev/PepperCRM-API/actions/workflows/deployment.yml)

### 1. Compilación de la Aplicación

- *Abre la terminal* y navega hasta la raíz de tu proyecto.
- Ejecuta el comando:

  bash
  mvn clean package -DskipTests -P docker -f pom.xml
  

    - Esto limpiará el proyecto, compilará el código y empaquetará la aplicación en un archivo JAR. El uso de -DskipTests omite las pruebas durante la construcción, esto con la finalidad de que no genere error, dado que se cambia las properties por variables de entorno. El uso de -P docker es porque se definió un perfil de build específico donde compila ignorando el .env.

### 2. Creación de la Imagen Docker

- *Asegúrate de tener un Dockerfile* en la raíz de tu proyecto. Este archivo define cómo se debe construir la imagen Docker.
- *Ejecuta el siguiente comando* para construir la imagen:

  bash
  docker build --platform linux/amd64 -t sebastian190030/api-peppercrm:latest .
  

    - Esto crea una imagen basada en tu aplicación.

### 3. Publicación de la Imagen en Docker Hub

- *Inicia sesión en Docker Hub* desde la terminal:

  bash
  docker login
  

- *Sube la imagen* a tu repositorio (cambiando sebastian190030 por tu usuario) en Docker Hub:

  bash
  docker push sebastian190030/api-peppercrm:latest
  

    - Esto hace que tu imagen esté disponible en línea públicamente.

### 4. Despliegue en Render.com

#### 4.1 Creación de Web Service

- *Inicia sesión en tu cuenta de Render.com*.
- *Crea un nuevo servicio* seleccionando "New" y luego "Web Service".
- *Elige Docker* como tipo de despliegue.
- *Proporciona el nombre de tu repositorio* de Docker Hub: sebastian190030/api-peppercrm.
- *Configura las variables de entorno* necesarias para tu aplicación.
- *Haz clic en "Crear"* para iniciar el despliegue.

#### 4.2 Despliegue
- *Usa el Deploy hook* para hacer deploy automático

  bash
  cmd /c deploy.render.cmd
  
  - Esto ejecuta un CURL -X POST https://api.render.com/deploy/srv-csgeg0lumphs73b48veg?key={key}.

## Estructura del Proyecto

Este documento describe la estructura del proyecto *PepperCRM API, desarrollado con **Spring Boot, **Java 17* y gestionado con *Maven*. A continuación, se detallan los directorios y la organización general del código fuente, con el objetivo de proporcionar una visión clara de cómo está estructurada la aplicación.
