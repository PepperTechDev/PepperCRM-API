# PepperCRM-API
## Deployment

[![CI/CD Pipeline for Deployment](https://github.com/PepperTechDev/PepperCRM-API/actions/workflows/deployment.yml/badge.svg)](https://github.com/PepperTechDev/PepperCRM-API/actions/workflows/deployment.yml)

### 1. Building the Application

- *Open your terminal* and navigate to the root directory of your project.
- Run the following command:

```bash
mvn clean package -DskipTests -P docker -f pom.xml
```

- This command cleans the project, compiles the code, and packages it into a JAR file.  
  The `-DskipTests` flag skips test execution during the build, which is useful when environment variables replace property files.  
  The `-P docker` flag activates a specific build profile that compiles the project while ignoring the `.env` file.

---

### 2. Building the Docker Image

- *Ensure you have a `Dockerfile`* in the root of your project. This file defines how your Docker image will be built.
- Run the following command to build the image:

```bash
docker build --platform linux/amd64 -t sebastian190030/api-peppercrm:latest .
```

- This command creates a Docker image based on your application.

---

### 3. Publishing the Image to Docker Hub

- *Log in to Docker Hub* from your terminal:

```bash
docker login
```

- *Push the image* to your Docker Hub repository (replace `sebastian190030` with your username):

```bash
docker push sebastian190030/api-peppercrm:latest
```

- This makes your Docker image publicly available online.

---

### 4. Deployment on Render.com

#### 4.1 Creating a Web Service

- *Log in to your [Render.com](https://render.com) account.*
- *Click "New" > "Web Service".*
- *Select Docker* as the deployment method.
- *Enter your Docker Hub repository name:*  
  `sebastian190030/api-peppercrm`
- *Set the necessary environment variables* for your application.
- *Click "Create"* to trigger the deployment.

---

#### 4.2 Manual Deployment via Deploy Hook

- *Use the deploy hook* to trigger automatic deployments:

```bash
curl -X POST https://api.render.com/deploy/srv-csgeg0lumphs73b48veg?key={key}
```

> Replace `{key}` with your actual deploy hook key provided by Render.

Alternatively, if you're using Windows, you might have a script like this:

```bash
cmd /c deploy.render.cmd
```

This command should internally execute the above `curl` command.

---

## Project Structure

This document describes the structure of the *PepperCRM API* project, developed using **Spring Boot**, **Java 17**, and managed with **Maven**. Below is an overview of the general directory structure to help you understand the organization of the codebase.

```plaintext
peppercrm-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/peppercrm/api/
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       ├── repository/
│   │   │       └── model/
│   │   └── resources/
│   │       ├── application.yml
│   │       └── logback.xml
│   └── test/
│       └── java/
│           └── com/peppercrm/api/
│               └── ...
├── Dockerfile
├── pom.xml
└── README.md
```

Let me know if you'd like to include example `.env` variables or environment settings for Render.
