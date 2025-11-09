#  Uso de imagen base con Java 17
FROM eclipse-temurin:17-jre

# Directiorio donde se colocará la aplicación en el contenedor
WORKDIR /app

# Copiar el archivo jar del proyecto al directorio /app en el contenedor
COPY target/api-0.0.1-SNAPSHOT.jar /app/api-peppercrm.jar

# Exponer el puerto que usa la aplicación
EXPOSE 8091

# Comando para ejecutar aplicación
CMD ["java","-jar","/app/api-peppercrm.jar"]
