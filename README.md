# NatureConect — Server

Backend server for **NatureConect**, a citizen-science / research platform where users publish geolocated bird sightings and researchers analyse the collected data.

The server is a **Spring Boot 3 REST API** backed by **MySQL**, secured with **Spring Security + JWT**, and it integrates a **Python (Ultralytics YOLO) pipeline** that detects and classifies birds in uploaded photos. It also serves a small set of static pages (login, registration and a researcher reports dashboard with charts and a heat map).

> Repository: `tfg-NatureConect-server` — backend of the NatureConect TFG (final degree project).

---

## Table of contents

1. [Features](#features)
2. [Tech stack](#tech-stack)
3. [Architecture](#architecture)
4. [Project structure](#project-structure)
5. [Prerequisites](#prerequisites)
6. [Getting started](#getting-started)
   - [1. Create the MySQL database](#1-create-the-mysql-database)
   - [2. Configure the application](#2-configure-the-application)
   - [3. Install the Python dependencies](#3-install-the-python-dependencies)
   - [4. Run the server](#4-run-the-server)
   - [5. Run the tests](#5-run-the-tests)
7. [Configuration reference](#configuration-reference)
8. [Database schema](#database-schema)
9. [Authentication](#authentication)
10. [API reference](#api-reference)
11. [Static web pages](#static-web-pages)
12. [Bird detection pipeline](#bird-detection-pipeline)
13. [Image storage](#image-storage)
14. [Known limitations](#known-limitations)
15. [License](#license)

---

## Features

- **Two kinds of accounts**
  - *Users* — publish sightings, like posts, receive notifications.
  - *Researchers* (`investigador`) — access the reports dashboard, protected by JWT.
- **Publications** with geolocation (latitude/longitude), photo upload, date, bird species attached and tags (`etiquetas`).
- **Likes** with add/remove and per-user state.
- **Notifications** — returns the likes received by a user's publications since a given date.
- **Taxonomy browsing** — birds (`ave`) grouped by family (`familia`), free-text search by species name and filter by family.
- **Sensitive-species protection** — the public feed omits sightings of birds marked as *Vulnerable* or *En peligro de extinción*, so their exact locations are never exposed.
- **Bird detection & classification** — a YOLO detector + YOLO classifier are spawned from Java for each uploaded image.
- **Researcher reports dashboard** (`informes.html`) — species filter, date range, Chart.js bar/line charts and a Leaflet heat map of observations.
- **Image upload** (multipart, up to 50 MB) stored on disk under `uploads/publicaciones` and served back as static content.
- **JWT authentication** (HS256, 1-hour expiry) issued on researcher register/login.

---

## Tech stack

| Layer | Technology |
| --- | --- |
| Language | Java 17 |
| Framework | Spring Boot 3.5.0 (Spring Web, Spring Security, Spring Data JPA) |
| Persistence | Hibernate / JPA, MySQL Connector/J 8.0.33 |
| Auth | Spring Security + JJWT 0.12.6 (HMAC-SHA), BCrypt password hashing |
| Build | Maven (Maven Wrapper included: `mvnw` / `mvnw.cmd`) |
| AI / CV | Python 3, Ultralytics YOLO, OpenCV (`src/main/python/ia.py`) |
| Frontend (static) | HTML/CSS/vanilla JS, Chart.js, Leaflet + leaflet.heat |
| Tests | JUnit 5 via `spring-boot-starter-test` |

---

## Architecture

The code follows a classic three-tier layout:

```
HTTP request
   │
   ▼
Controller  (controllers/*)     ── parsing, validation, response envelope
   │
   ▼
Service     (service/*)         ── business rules (implements *ServiceInterface)
   │
   ▼
Repository  (repository/*)      ── Spring Data JPA interfaces
   │
   ▼
MySQL  (models/* = JPA entities)
```

Cross-cutting concerns:

- `security/SecurityConfig` — security filter chain (CSRF disabled, URL rules).
- `filters/JwtAuthenticationFilter` — reads the `token` request parameter, validates the JWT and populates the `SecurityContext`.
- `service/modeloService` — spawns the Python process and parses its JSON stdout.

---

## Project structure

```
.
├── pom.xml                        # Maven build (Spring Boot 3.5.0 parent)
├── mvnw / mvnw.cmd                # Maven wrapper
├── doc/                           # Generated Javadoc (open doc/index.html)
├── uploads/                       # Uploaded images (uploads/publicaciones/…)
├── src/
│   ├── main/
│   │   ├── java/com/natureconect/natureConect/
│   │   │   ├── NatureConectApplication.java    # Entry point
│   │   │   ├── controllers/                    # REST controllers
│   │   │   ├── models/                         # JPA entities + DTOs
│   │   │   ├── repository/                     # Spring Data repositories
│   │   │   ├── service/                        # Business logic + interfaces
│   │   │   ├── security/SecurityConfig.java    # Security filter chain
│   │   │   └── filters/JwtAuthenticationFilter.java
│   │   ├── python/
│   │   │   ├── ia.py                           # Detection + classification script
│   │   │   ├── detector.pt                     # YOLO detection weights
│   │   │   └── clasificador.pt                 # YOLO classification weights
│   │   └── resources/
│   │       ├── application.properties          # Datasource, JPA, uploads config
│   │       └── static/
│   │           ├── login.html                  # Researcher login
│   │           ├── registrar.html              # Researcher registration
│   │           ├── informes.html               # Reports dashboard (auth)
│   │           └── css/
│   └── test/java/…/NatureConectApplicationTests.java
```

---

## Prerequisites

- **Java 17+**
- **Maven 3.8+** (or just use the bundled `mvnw` wrapper)
- **MySQL 8** running locally (default: `localhost:3306`)
- **Python 3** with `ultralytics` and `opencv-python`, available on the path as `python3`
- A modern browser (for the static pages)

---

## Getting started

### 1. Create the MySQL database

The default configuration expects a database called `natureconect` with user/password `usuario`:

```sql
CREATE DATABASE natureconect;
CREATE USER 'usuario'@'localhost' IDENTIFIED BY 'usuario';
GRANT ALL PRIVILEGES ON natureconect.* TO 'usuario'@'localhost';
FLUSH PRIVILEGES;
```

Hibernate runs with `spring.jpa.hibernate.ddl-auto=update`, so the tables
(`usuarios`, `investigador`, `publicacion`, `ave`, `familia`, `etiquetas`,
`likes`, `aves_publicacion`, `etiqueta_publicacion`, …) are created/updated
automatically on startup. Seed data for birds/families/tags must be inserted
manually (or by your own script).

### 2. Configure the application

Edit `src/main/resources/application.properties` if your environment differs:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/natureconect
spring.datasource.username=usuario
spring.datasource.password=usuario
```

### 3. Install the Python dependencies

```bash
python3 -m pip install ultralytics opencv-python
```

Make sure the model weights exist (they are committed in the repo):

- `src/main/python/detector.pt`
- `src/main/python/clasificador.pt`

> The Java side invokes the script as `python3 src/main/python/ia.py <image>`,
> so **the server must be started from the project root** for the relative
> paths to resolve.

### 4. Run the server

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
./mvnw.cmd spring-boot:run
```

The API will be available at **http://localhost:8080**.

To build an executable jar instead:

```bash
./mvnw clean package
java -jar target/natureConect-0.0.1-SNAPSHOT.jar
```

### 5. Run the tests

```bash
./mvnw test
```

> Note: `NatureConectApplicationTests` is a `@SpringBootTest`, so it needs a
> reachable MySQL instance to load the application context.

---

## Configuration reference

| Property | Default | Description |
| --- | --- | --- |
| `spring.datasource.url` | `jdbc:mysql://localhost:3306/natureconect` | JDBC URL |
| `spring.datasource.username` / `password` | `usuario` / `usuario` | DB credentials |
| `spring.jpa.hibernate.ddl-auto` | `update` | Auto-create/update schema |
| `spring.jpa.show-sql` | `true` | Log executed SQL |
| `spring.servlet.multipart.max-file-size` | `50MB` | Max upload size |
| `spring.web.resources.static-locations` | `classpath:/static/,file:uploads/` | Serves uploaded images |
| Upload directory (in code) | `uploads/publicaciones` | `PublicacionService.UPLOAD_DIR` |
| Detection confidence threshold (in code) | `0.75` | `CONFIDENCE_THRESHOLD` in `ia.py` |
| JWT secret (in code) | hard-coded constant | `JwtAuthenticationFilter` / `InvestigadorController` |
| JWT expiry (in code) | 1 hour | `InvestigadorController` |

Debug logging for Spring Data, Hibernate SQL, HTTP and web is enabled in
`application.properties`; disable it for production.

---

## Database schema

| Table | Purpose |
| --- | --- |
| `usuarios` | App users (`id_usuario`, `nombre_usuario`, `email_usuario`, `password`) |
| `investigador` | Researchers (`nombre`, `apellidos`, `institucion`, `correo`, `password`) |
| `publicacion` | Sighting posts (`id_publicacion`, `id_foto`, `me_gustas`, `latitud`, `longitud`, `fecha`) |
| `ave` | Bird species (`nombre_comun`, `nombre_cientifico`, `proteccion`, FK → family) |
| `familia` | Bird families |
| `etiquetas` | Free-form tags |
| `aves_publicacion` | M2M: birds ↔ publications |
| `etiqueta_publicacion` | M2M: tags ↔ publications |
| `likes` | Likes (`id_publicacion`, `id_usuario`, `fecha`) |

---

## Authentication

There are two authentication layers:

1. **Public API** — `/api/**` and `/publicaciones/**` are currently `permitAll`
   in `SecurityConfig`, so the mobile/app-facing endpoints work without a token.
2. **Researcher dashboard** — `/informes.html` requires authentication.
   - `POST /api/investigador/registrar` and `POST /api/investigador/login`
     return a JWT (`token`, HS256, valid for 1 hour).
   - The static login/register pages store it in `localStorage` and redirect to
     `/informes.html?token=<jwt>`.
   - `JwtAuthenticationFilter` reads the `token` **request parameter** and, if
     valid, sets the authentication (subject = researcher e-mail).

Passwords for both users and researchers are hashed with **BCrypt**.

> ⚠️ The JWT secret is a hard-coded constant shared by two classes. Move it to
> an environment variable / externalised config before any real deployment —
> see [Known limitations](#known-limitations).

---

## API reference

All JSON responses use a common envelope:

```json
{ "success": true, "message": "...", "data": ... }
```

Unless noted otherwise, parameters are sent as `application/x-www-form-urlencoded`
or `multipart/form-data` request parameters (not a JSON body).

### Users — `/api/usuarios`

| Method | Endpoint | Params | Description |
| --- | --- | --- | --- |
| POST | `/api/usuarios/registrar` | `nombre`, `password`, `email` | Register a user |
| POST | `/api/usuarios/login` | `nombre`, `password` | Log in |

### Researchers — `/api/investigador`

| Method | Endpoint | Params | Description |
| --- | --- | --- | --- |
| POST | `/api/investigador/registrar` | `nombre`, `apellidos`, `institucion`, `password`, `correo` | Register a researcher → returns `token` |
| POST | `/api/investigador/login` | `correo`, `password` | Log in → returns `token` |

### Birds & taxonomy

| Method | Endpoint | Params | Description |
| --- | --- | --- | --- |
| GET | `/api/ave/listar` | — | List all bird species (with family) |
| GET | `/api/familia/listar` | — | List all families |
| GET | `/api/etiqueta/listar` | — | List all tags (204 if empty) |
| POST | `/api/etiqueta/buscarEtiqueta` | `texto` | Search tags by text |

### Publications — `/api/publicacion`

| Method | Endpoint | Params | Description |
| --- | --- | --- | --- |
| POST | `/api/publicacion/Subir` | multipart `imagen` | Store an image file (returns success) |
| POST | `/api/publicacion/crearP` | `id_usuario`, `latitud`, `longitud` | Create the publication for the uploaded image |
| GET | `/api/publicacion/listar` | — | Feed: hides publications containing sensitive species (`proteccion` = `Vulnerable` / `En peligro de extinción`), so their locations stay private |
| POST | `/api/publicacion/ver` | `id_publicacion`, `id_usuario` | Publication detail incl. like state |
| POST | `/api/publicacion/verInvitado` | `id_publicacion` | Publication detail for guests (no user) |
| POST | `/api/publicacion/misPublicaciones` | `id_usuario` | Publications of a user |
| POST | `/api/publicacion/nave` | `id_publicacion`, `id_ave` | Attach a bird to a publication |
| POST | `/api/publicacion/nEtiqueta` | `id_publicacion`, `id_etiqueta` | Attach a tag to a publication |
| POST | `/api/publicacion/dar` | `id_publicacion`, `id_usuario` | Like |
| POST | `/api/publicacion/quitar` | `id_publicacion`, `id_usuario` | Remove like |
| POST | `/api/publicacion/Buscar` | `texto` | Search publications by bird name |
| POST | `/api/publicacion/filtrarFamilia` | `familia` | Filter publications by family |
| GET | `/api/publicacion/especie` | `id`, `desde`, `hasta` (ISO dates) | Aggregated data for the reports: heat-map points, monthly bars, yearly line |

> Typical flow: `POST /Subir` (image) → `POST /crearP` (gets the id of the
> just-stored file) → `POST /nave` / `POST /nEtiqueta` to enrich it.

### Bird detection — `/api/deteccion`

| Method | Endpoint | Params | Description |
| --- | --- | --- | --- |
| POST | `/api/deteccion/clasificar` | multipart `imagen` | Run YOLO detection + classification |

Example response:

```json
{
  "imagen": "/tmp/imagen_123.jpg",
  "objetos": [
    {
      "bbox": [120, 80, 400, 300],
      "confianza_deteccion": 0.91,
      "clase": "ardenza comun",
      "confianza_clasificacion": 0.87
    }
  ]
}
```

### Notifications — `/api/notificaciones`

| Method | Endpoint | Params | Description |
| --- | --- | --- | --- |
| POST | `/api/notificaciones/notificaciones` | `id_usuario`, `fecha` (`yyyy-MM-dd`) | Likes received by the user's publications since `fecha` |

---

## Static web pages

Served from `src/main/resources/static`:

| Page | Access | Purpose |
| --- | --- | --- |
| `/login.html` | public | Researcher login → stores JWT → redirects to `/informes.html?token=…` |
| `/registrar.html` | public | Researcher registration → same flow |
| `/informes.html` | **authenticated** | Reports dashboard: species select, date range, Chart.js bar + line charts, Leaflet heat map (Spain bounds) |
| `/css/*.css` | public | Stylesheets |

---

## Bird detection pipeline

`POST /api/deteccion/clasificar` → `modeloController` → `modeloService.procesarImagen()`:

1. The uploaded file is written to a temporary `.jpg`.
2. Java spawns `python3 src/main/python/ia.py <absolute path>`.
3. `ia.py`:
   - loads `detector.pt` (detection) and `clasificador.pt` (classification),
   - runs detection and keeps boxes with confidence **≥ 0.75**,
   - crops each box and runs the classifier on the crop,
   - prints a single-line JSON document to stdout.
4. `modeloService` reads stdout, picks the line that looks like `{…}` and
   deserialises it into `ClasificacionResultado` (list of `ObjetoDetectado`).
5. The temporary file is deleted and the result is returned.

**Requirements:** `python3`, `ultralytics`, `opencv-python`, and the working
directory set to the project root (relative model paths).

---

## Image storage

- Uploads go to `uploads/publicaciones/<n>.<ext>` (`jpg`, `jpeg`, `png`, `gif`).
- The file name is derived from the number of images already in the folder.
- `spring.web.resources.static-locations` also maps `file:uploads/`, so images
  are reachable over HTTP (e.g. `/publicaciones/1.jpg`).

---

## Known limitations

Things to address before using this server outside a development/academic
environment:

- **`/api/**` is fully open** — only `/informes.html` is protected. Add proper
  per-endpoint authorization for anything sensitive.
- **Hard-coded JWT secret** duplicated in `JwtAuthenticationFilter` and
  `InvestigadorController`; move it to configuration/secrets management.
- **Token passed as a query parameter** (visible in logs/history) — prefer
  `Authorization: Bearer` headers.
- **No input sanitisation / rate limiting**; error messages leak exception text.
- **Photo naming based on directory listing** can collide under concurrency.
- **Python invocation is Unix-oriented** (`python3`, relative paths) — needs
  adaptation on Windows.
- **Debug SQL/HTTP logging is enabled** by default.
- `spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect`
  is deprecated in Hibernate 6; letting Spring Boot auto-detect the dialect is
  preferred.
- No test coverage beyond the default context-load test.

---

## License

No license file is included in this repository. All rights reserved by the
author unless stated otherwise.
