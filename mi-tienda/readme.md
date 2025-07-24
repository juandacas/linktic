# Mi Tienda - Microservicios de Productos e Inventario

Este proyecto consiste en dos microservicios desarrollados con Java y Spring Boot:

- **productos-service**: Gestión de productos.
- **inventario-service**: Control de inventario y compras.

## 🚀 Tecnologías utilizadas

- Java 17
- Spring Boot
- PostgreSQL
- Docker y Docker Compose
- JUnit + Mockito (pruebas unitarias)

---

## 📦 Estructura del proyecto

```
mi-tienda/
├── productos-service/
│   └── target/productos-service.jar
├── inventario-service/
│   └── target/inventario-service.jar
├── docker-compose.yml
```

---

## 🔧 Requisitos previos

- [Docker Desktop](https://www.docker.com/products/docker-desktop) instalado
- Puerto `5432`, `8081` y `8082` disponibles

---

## ▶️ Cómo ejecutar el proyecto

1. Compila los dos microservicios:

```bash
cd productos-service
./mvnw clean package -DskipTests

cd ../inventario-service
./mvnw clean package -DskipTests
```

2. En la raíz del proyecto `mi-tienda/`, ejecuta:

```bash
docker-compose up --build
```

3. Los servicios estarán disponibles en:

- `http://localhost:8081/api/productos`
- `http://localhost:8082/api/inventario`

---

## 🥪 Pruebas unitarias

Para ejecutar las pruebas:

```bash
cd productos-service
./mvnw test

cd ../inventario-service
./mvnw test
```

---

## 📡 Endpoints principales

### Productos

| Método | Endpoint              | Descripción             |
| ------ | --------------------- | ----------------------- |
| GET    | `/api/productos`      | Listar productos        |
| POST   | `/api/productos`      | Crear producto          |
| GET    | `/api/productos/{id}` | Obtener producto por ID |

### Inventario

| Método | Endpoint                  | Descripción                     |
| ------ | ------------------------- | ------------------------------- |
| POST   | `/api/inventario`         | Crear inventario                |
| GET    | `/api/inventario/{id}`    | Obtener inventario por producto |
| PUT    | `/api/inventario/{id}`    | Actualizar inventario           |
| POST   | `/api/inventario/comprar` | Realizar compra                 |

---

## 📁 Variables de entorno

Las credenciales de las bases de datos están definidas en `docker-compose.yml` y referenciadas en `application.yml` con variables como:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

---

## 🧼 Para detener y limpiar

```bash
docker-compose down -v
```

---

## 👨‍💼 Autor

Juan David Castrillón\
Back-End Developer

