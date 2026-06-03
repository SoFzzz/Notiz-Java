# **¡Bienvenido al Proyecto Notiz (Java Version)!** 🎓🚀

Soy **Paula Sofía González Zambrano**, estudiante de Cuarto semestre de **Ingeniería de Software** en la **Universidad Cooperativa de Colombia (UCC)**.

**Notiz (Java Version)** es una especificación y plataforma web empresarial para la gestión académica institucional. Permite a los profesores registrar calificaciones dinámicas, a los estudiantes realizar consultas en tiempo real y a los administradores generar reportes de rendimiento y auditoría con los más altos estándares de rendimiento y seguridad.

---

## 🛠️ Stack Tecnológico

* **Lenguaje:** Java 21 LTS (Virtual Threads habilitados).
* **Framework Backend:** Spring Boot 4.0.3. (Spring Web, Spring Security, Spring Cache).
* **Persistencia NoSQL:** MongoDB Atlas (Modelo documental flexible).
* **Base de Datos en Memoria:** Redis (Gestión de caché y blacklist de sesiones).
* **Autenticación Federada:** Google Identity Services (OAuth2).
* **Servicio de Notificación:** SendGrid API (Envío transaccional).
* **Generación de Reportes:** OpenPDF (Estándar de preservación de accesibilidad PDF/A-1a).
* **Mensajería Tiempo Real:** Spring WebSocket (Subprotocolo STOMP).
* **Entorno de Despliegue:** Render.com (Contenedores Docker multi-stage).

---

## 🏗️ Estado Actual del Proyecto (Implementado)

Actualmente, el andamiaje del proyecto base ha sido **100% completado** siguiendo una arquitectura limpia (Clean Architecture) y adhiriéndose estrictamente a los diagramas UML propuestos (Diagrama de Clases y Diagrama de Desarrollo por Capas).

El proyecto está estructurado bajo las siguientes capas (`src/main/java/com/notiz/`):

1. **`model`**: Entidades del dominio mapeadas a documentos de MongoDB mediante herencia (clase base abstracta `BaseDocument` y `User`) y polimorfismo (`Admin`, `Professor`, `Student`, `Parent`).
2. **`repository`**: Interfaces de acceso a datos utilizando `MongoRepository`.
3. **`dto`**: Objetos de Transferencia de Datos para desacoplar el modelo interno de las peticiones REST, validados con `jakarta.validation`.
4. **`service`**: Lógica de negocio (Interfaces e Implementaciones) centrada en el dominio (e.g. `ChatMessageService`, `GradeModificationRequestService`).
5. **`controller`**: Puntos de entrada de la API REST que exponen los endpoints (`UserController`, `CommunicationController`, `ReportController`, etc.).
6. **`security`**: Implementación de seguridad basada en tokens JWT con roles (`ROLE_ADMIN`, `ROLE_PROFESSOR`, etc.).
7. **`exception`**: Manejo centralizado de errores globales mediante `@RestControllerAdvice`.
8. **`config`**: Configuración de dependencias externas (MongoDB Auditing, Redis, WebSockets).

---

## 🚀 Cómo ejecutar el proyecto

1. **Clonar** el repositorio y abrir en **IntelliJ IDEA**.
2. **Cargar Maven**: Asegúrate de descargar las dependencias listadas en el `pom.xml`.
3. **Configurar el Entorno**: Validar la configuración de `application.yml` (Asegúrate de contar con tus credenciales de MongoDB Atlas, Redis, y SendGrid).
4. Ejecutar la clase principal `NotizApplication.java` o correr el comando:
   ```bash
   mvn spring-boot:run
   ```
5. La API estará disponible en `http://localhost:8080`.

---
*Este proyecto es parte de la formación académica y refleja la aplicación de metodologías de diseño y arquitectura de software.*
