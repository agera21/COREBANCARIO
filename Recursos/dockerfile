FROM openjdk:17-alpine

WORKDIR /app

# Copiar el JAR
COPY /corebancario.jar .


# Exponer el puerto HTTPS (8080)
EXPOSE 8080

# Ejecutar la app
ENTRYPOINT ["java", "-Dspring.config.location=file:/etc/corebancario/application.properties", "-jar", "corebancario.jar"]

# Para mas de un properties
# ENTRYPOINT ["java", "-Dspring.config.location=file:/etc/enee/application.properties,file:/etc/pruebacrm/override.properties", "-jar", "enee.jar"]
