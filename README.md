# TPI - Sistema de Gestión de Pedidos - Programación II

## Descripción

Aplicación desarrollada en Java utilizando JDBC para la gestión de categorías, productos y pedidos sobre una base de datos MySQL.

El proyecto fue realizado como parte del Trabajo Práctico N.º 10 de Programación II, aplicando conceptos de acceso a datos, arquitectura por capas, patrón DAO, validaciones de negocio y manejo de conexiones mediante HikariCP.

---

## Objetivos

- Implementar acceso a bases de datos utilizando JDBC.
- Aplicar el patrón DAO para separar la lógica de persistencia.
- Utilizar una arquitectura modular y mantenible.
- Implementar operaciones CRUD sobre distintas entidades.
- Gestionar pedidos y sus detalles.
- Aplicar validaciones de negocio mediante la capa Service.
- Utilizar pool de conexiones con HikariCP.

---

## Tecnologías Utilizadas

- Java
- Maven
- JDBC
- MySQL
- HikariCP
- MySQL Connector/J

---

## Dependencias

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>9.3.0</version>
</dependency>

<dependency>
    <groupId>com.zaxxer</groupId>
    <artifactId>HikariCP</artifactId>
    <version>6.3.0</version>
</dependency>
```


---
## Estructura del Proyecto

```text
src/main/java
│
├── config
│   └── HikariConnection
│
├── dao
│   ├── CategoriaDao
│   ├── CategoriaDaoImpl
│   ├── ProductoDao
│   ├── ProductoDaoImpl
│   ├── PedidoDao
│   └── PedidoDaoImpl
│
├── entities
│   ├── Base
│   ├── Categoria
│   ├── Producto
│   ├── Pedido
│   ├── DetallePedido
│   └── Usuario
│
├── enums
│   ├── Estado
│   ├── FormaPago
│   └── Rol
│
├── exceptions
│   └── Excepciones personalizadas
│
├── interfaces
│   └── Calculable
│
├── service
│   ├── CategoriaService
│   ├── ProductoService
│   └── PedidoService
│
├── menu
│   ├── MainCategoria
│   ├── MainProducto
│   └── MainPedido
│
└── Main
```

---

## Funcionalidades

### Categorías

- Alta de categorías.
- Modificación de categorías.
- Eliminación lógica.
- Búsqueda por ID.
- Listado completo.
- Validación de nombres duplicados.

### Productos

- Alta de productos.
- Modificación de productos.
- Eliminación lógica.
- Búsqueda por ID.
- Listado general.
- Asociación con categorías.
- Validación de datos obligatorios.

### Pedidos

- Creación de pedidos.
- Asociación de productos al pedido.
- Cálculo automático del total.
- Gestión de detalles del pedido.
- Manejo de estados.
- Registro de forma de pago.

---

## Validaciones Implementadas

El sistema utiliza excepciones personalizadas para validar:

- Categorías existentes.
- Productos existentes.
- Campos obligatorios.
- Strings vacíos o inválidos.
- Datos inconsistentes.

Algunas excepciones utilizadas:

- CategoriaExistenteException
- CategoriaNoEncontradaException
- ProductoExistenteException
- ProductoNoEncontradoException
- StringInvalidException
- AtributoInvalidoException

---

## Modelo de Negocio

### Categoría

Representa una clasificación de productos.

### Producto

Contiene información relacionada con:

- Nombre
- Descripción
- Precio
- Stock
- Categoría

### Pedido

Contiene:

- Fecha
- Estado
- Forma de pago
- Usuario
- Total
- Detalles del pedido

### DetallePedido

Representa cada producto incluido dentro de un pedido junto con:

- Cantidad
- Subtotal

---

## Patrón de Diseño Aplicado

##  Arquitectura del proyecto

El proyecto sigue una arquitectura en capas:

```
    Menú (UI de consola)
       ↓
   Service (lógica de negocio)
       ↓
   DAO / Interfaces (acceso a datos)
       ↓
   MySQL (persistencia)
```


### DAO (Data Access Object)

Permite desacoplar la lógica de acceso a datos de la lógica de negocio.

### Arquitectura por Capas

- Presentación (menu)
- Negocio (service)
- Persistencia (dao)
- Modelo (entities)

---

##  Configuración y ejecución

### 1. Clonar el repositorio

```bash
git clone <url-del-repositorio>
cd food-store
```

### 2. Configurar la conexión a la base de datos

Editar el archivo `src/main/java/config/HikariConnection.java` con tus credenciales:

```java
config.setJdbcUrl("jdbc:mysql://localhost:3306/foodstore?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
config.setUsername("root");
config.setPassword("tu_contraseña");
```

### 3. Compilar y ejecutar

```bash
mvn compile
mvn exec:java -Dexec.mainClass="Main"
```

> Al iniciar, la aplicación ejecuta automáticamente el `schema.sql` para crear la base de datos `foodstore` y todas sus tablas si no existen.
 
---

##  Funcionalidades

El sistema presenta un menú principal con cuatro módulos:

```
=== SISTEMA DE PEDIDOS (FOOD STORE) ===
1. Categorías
2. Productos
3. Usuarios
4. Pedidos
0. Salir
```

Cada módulo ofrece las operaciones **CRUD** completas:

###  Categorías
- Listar todas las categorías activas
- Crear nueva categoría (nombre + descripción)
- Editar nombre o descripción
- Eliminar (soft delete)
###  Productos
- Listar todos los productos activos con su categoría
- Crear producto (nombre, precio, descripción, stock, imagen, categoría)
- La disponibilidad se actualiza automáticamente según el stock
- Editar datos del producto
- Eliminar (soft delete)
###  Usuarios
- Listar todos los usuarios
- Crear usuario (nombre, apellido, mail, celular, contraseña, rol)
- Roles disponibles: `ADMIN` | `USUARIO`
- Editar datos del usuario
- Eliminar (soft delete)
###  Pedidos
- Listar todos los pedidos con sus detalles
- Crear pedido (asociado a un usuario, con forma de pago)
- Agregar/quitar productos al detalle del pedido
- Actualizar el detalle de un pedido existente
- Estados del pedido: `PENDIENTE` → `CONFIRMADO` → `TERMINADO` | `CANCELADO`
- Formas de pago: `EFECTIVO` | `TARJETA` | `TRANSFERENCIA`
- El total se calcula automáticamente a partir de los detalles
- Eliminar pedido (soft delete)
---

## Estado del Proyecto

Proyecto académico en desarrollo correspondiente al Trabajo Práctico 10 de Programación II.

Actualmente se encuentran implementadas las capas principales del sistema y la estructura necesaria para la gestión de categorías, productos y pedidos mediante JDBC.

---

## Autores

Agustina Fontagnol, Gianella Peña, Martina Suarez y Yoselie Aquino

Tecnicatura Universitaria en Programación.
Programación II.
