# CHALLENGE BACKEND - Java Spring Boot (API)

Desarrollo de una API que permite exponer la información de personajes y sus películas para que cualquier frontend pueda consumirla.  

***

* [**Modelado de Base de Datos**](#modelado-de-Base-de-Datos)
* [Run del **proyecto**](#Run-del-proyecto)
* [Probando los servicios **REST**](#Probando-los-servicios-REST)
* [Colección de **Postman** para pruebas](#Colección-de-Postman-para-pruebas)
* [Construido con](#Construido-con)
* [Desarrollador](#Desarrollador)
* [Expresiones de Gratitud](#Expresiones-de-Gratitud)

---

## Modelado de Base de Datos
![image](https://user-images.githubusercontent.com/60399697/194789872-00538f99-92d0-46ad-bbbd-749aadc90c5e.png)



## Run del proyecto

Al dar inicio a nuestra aplicación se van a crear las siguientes tablas en nuestra base de datos:.

_Muestra de creación de las tablas desde H2-Console._  
![image](https://user-images.githubusercontent.com/60399697/194791022-e399e1ca-c96a-4933-9a23-353d02bef23d.png)

_...y para acceder a la consola H2 vamos a esta dirección:_

```
http://localhost:8080/h2-console/
```

Y si miramos la tabla 'role' tiene los siguientes datos, estos fueron creados por componente al iniciar la aplicación:  
![image](https://user-images.githubusercontent.com/60399697/194791432-4e3e4610-2de6-48cd-99bd-e43db769a5ea.png)



## Probando los servicios REST

Utilizamos **Postman** para probar los servicios

_Creación de usuario._  
<img width="700" src="https://user-images.githubusercontent.com/60399697/194791731-61cabf9a-c68f-4c02-a3f9-7c778b10c483.png">


_Login exitoso de admin._  
<img width="700" src="https://user-images.githubusercontent.com/60399697/194791716-75911a70-6e7d-42e1-880d-ddbc28c5b16d.png">

El rol Admin por defecto tiene también el rol User, nos devuelve un token que es el que utilizamos para todas las peticiones.  
<img width="700" src="https://user-images.githubusercontent.com/60399697/194792591-937ae42a-bd62-44b6-b356-e1a23cbfc455.png">


Recordemos que para cada petición debemos mandar un **token** que nos devuelve nuestro servicio de login y debes ser de tipo **Bearer**.

_Se tendrá como respuesta **un status code 401** cuando el usuario no está logueado o no tiene el Rol correspondiente para hacer alguna acción._



## Colección de Postman para pruebas

En el siguiente **[link](https://drive.google.com/file/d/13Nqb6ReOPk9hxhk9UOEGR4rhYeM1n60W/view?usp=sharing)** está la **[colección](https://drive.google.com/file/d/13Nqb6ReOPk9hxhk9UOEGR4rhYeM1n60W/view?usp=sharing)** de **[Postman](https://drive.google.com/file/d/13Nqb6ReOPk9hxhk9UOEGR4rhYeM1n60W/view?usp=sharing)**

Se recomienda abrir con un visor adecuado para archivos JSON.


## Construido con

_Algunas de las herramientas que utilizadas para crear el proyecto_

* [Bootify](https://bootify.io/) - Es un servicio freemium que acelera el desarrollo de Spring Boot
* [Spring](https://docs.spring.io/spring-boot/docs/2.4.12/reference/html/documentation-overview.html) - El framework usado
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [H2 In-Memory Database](https://mvnrepository.com/artifact/com.h2database/h2) - Base de Datos
* [Spring Security](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security) - Framework de apoyo de Spring que provee una serie de servicios de seguridad
* [JWT (JSON Web Token)](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt) - Estándar que define un mecanismo para poder propagar la identidad de un usuario entre dos partes
* [Mockito Core](https://mvnrepository.com/artifact/org.mockito/mockito-core) - Proporciona diferentes formas de simular una clase, esto para tests
* [SendGrid](https://docs.sendgrid.com/for-developers/sending-email/api-getting-started) - Usado para generar Emails
* Entre otros



## Desarrollador

* **Juan Santa** - [jcsantahurtado](https://github.com/jcsantahurtado)



## Expresiones de Gratitud

* A Alkemi por compartir este challenge
* A ONE Alura + Oracle por traerme de vuelta al mundo de la programación
* A amoelcodigo.com, también a LucasMoy y a Amigos Code en YouTube fuentes consultadas

---
⌨️ con ❤️ por [jcsantahurtado](https://github.com/jcsantahurtado) 😊
