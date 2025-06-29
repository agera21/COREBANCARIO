# Core Bancario 

**Core Bancario** es una aplicación backend desarrollada en **Spring Boot**, que emula las funcionalidades esenciales de un sistema bancario: gestión de clientes, cuentas y movimientos financieros. Este proyecto está diseñado para fines educativos, pruebas de concepto y entornos de prácticas con tecnologías empresariales modernas.


## Funcionalidades

- Registro y actualización de **clientes** y sus **datos personales**.
- Creación, búsqueda, listado y eliminación de **cuentas bancarias**.
- Registro de **movimientos** (depósitos y retiros) con validaciones de saldo.
- Reportes por **cliente** y **cuenta** filtrados por rango de fechas.
- Manejo de errores y validaciones a través de respuestas estructuradas.
- Integración con **base de datos MySQL**.
- Contenedor Docker preparado para despliegue.
- Archivos de prueba Postman incluidos.



## Requisitos

- Java 17
- Maven 3.8+
- Docker
- MySQL 8+
- Postman (opcional, para pruebas)



## Instrucciones de Docker

El contenedor se ejecutada en docker utilizando volumenes en un servidor linux

Utilizamos el comando docker build para crear la imagen, este comando se debe ejecutar una vez estemos en la carpeta raiz donde se encuenta nuestro archivo llamado *corebancario.ja* y nuestro *dockerfile*


    docker build -t corebancario .


Una vez creada la imagen vamos arrancar la imagen en un volumen utilizando el siguiene comando:

    docker run -v /opt/Fuentes/docker-volumenes/corebancario:/etc/corebancario  -v /etc/timezone:/etc/timezone:ro -v /etc/localtime:/etc/localtime:ro  --name corebancario -p 8095:8080 -d corebancario

Ruta donde se colocara el archivo .properties en el servidor linux: /opt/Fuentes/docker-volumenes/corebancario

Ruta dentro del contenedor donde se guardara el archivo properties: /etc/corebancario

Nombre del contenedor: --name corebancario 

Puerto a utilizar para el despliegue: -p 8095:8080 (en este caso el interno es 8080 y el externo de acceso es 8095)

Nombre de la imagen creada: -d corebancario

###  Application.properties

En este apartado se definen la conexion a la base de datos MySQL, se debe definir la ip a la que se conectará, el usuario y la contraseña


###  Script de base de datos
Se deja una archivo de base de datos llamado *BaseDeDatos.sql* el cual contiene el ddl de la misma.

### Nota 
Todos los archivos anteriormente mencionados esta en la carpeta raiz de este proyecto en la carpeta llamada *Recursos*.


