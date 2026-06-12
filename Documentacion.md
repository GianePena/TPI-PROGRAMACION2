# Documentacion — Food Store

Documentación paso a paso para operar el sistema desde la consola.

---

## Menú principal

Al iniciar la aplicación se muestra el menú principal. Ingresás el número de la opción y presionás Enter.

```
=== SISTEMA DE PEDIDOS (FOOD STORE) ===
1. Categorias
2. Productos
3. Usuarios
4. Pedidos
0. Salir
Ingrese una opcion:
```

Cada módulo tiene un submenú con las operaciones disponibles.

---

##  Categorías

> Las categorías agrupan los productos. Deben existir antes de poder crear productos.

### Listar categorías

```
=== CATEGORIAS ===
  1. Listar   ← seleccionar
```

Muestra todas las categorías activas con su ID, nombre y descripción.

---

### Crear categoría

```
=== CATEGORIAS ===
  2. Crear   ← seleccionar

=== CREAR CATEGORIA ===
Nombre: Pizzas
Descripcion: Pizzas a la piedra y al molde

→ Categoria creada con ID: 1
```

**Campos requeridos:**
| Campo | Descripción |
|---|---|
| Nombre | Texto, no puede estar vacío ni repetido |
| Descripción | Texto libre |

---

### Editar categoría

```
=== CATEGORIAS ===
  3. Editar   ← seleccionar

--- CATEGORIAS ---
| ID: 1  | Nombre: Pizzas ...

=== EDITAR CATEGORIA ===
- ID de la categoria a editar: 1
Ingrese el atributo a modificar ( NOMBRE | DESCRIPCION ): NOMBRE
Nuevo valor: Pizzas y Empanadas

→ Categoria actualizada. ID: 1
```

**Atributos editables:** `NOMBRE` o `DESCRIPCION` (escribirlo en mayúsculas tal cual se muestra).

---

### Eliminar categoría

```
=== CATEGORIAS ===
  4. Eliminar   ← seleccionar

- ID de la categoria a eliminar: 1
Seguro quiere eliminar categoria: 1
Ingrese S para confirmar y N para volver: S | N: S

→ Categoria eliminada. ID: 1
```

>  La eliminación es lógica (soft delete). El registro no se borra de la base de datos, solo se marca como eliminado.

---

##  Productos

> Para crear un producto necesitás tener al menos una **categoría** creada previamente.

### Listar productos

```
=== PRODUCTOS ===
  1. Listar   ← seleccionar
```

Muestra todos los productos activos con nombre, precio, stock, disponibilidad y categoría.

---

### Crear producto

```
=== PRODUCTOS ===
  2. Crear   ← seleccionar

=== CREAR PRODUCTO ===
Nombre: Pizza Muzzarella
Precio: 1500.0
Descripcion: Pizza clasica con salsa y muzzarella
Stock: 20
Imagen: pizza_muzza.jpg

=== CATEGORIAS DISPONIBLES ===
| ID: 1  | Nombre: Pizzas ...

ID Categoria: 1

→ Producto creado con ID: 3
```

**Campos requeridos:**
| Campo | Descripción |
|---|---|
| Nombre | Texto, no puede repetirse |
| Precio | Número decimal (ej: `1500.0`) |
| Descripción | Texto libre |
| Stock | Número entero. Si es `0`, el producto queda como no disponible automáticamente |
| Imagen | Nombre del archivo de imagen (ej: `pizza.jpg`) |
| ID Categoría | ID de una categoría existente (el sistema las lista para elegir) |

---

### Editar producto

```
=== PRODUCTOS ===
  3. Editar   ← seleccionar

--- PRODUCTOS DISPONIBLES ---
...lista de productos...

--- EDITAR PRODUCTO ---
ID del producto a editar: 3

--- ATRIBUTO A MODIFICAR ---
1. Nombre
2. Precio
3. Descripcion
4. Stock
5. Imagen
6. Categoria

Seleccione una opcion: 2
- Nuevo precio: 1800.0

→ Producto actualizado. ID: 3
```

A diferencia de las categorías, los productos tienen un menú numerado para elegir el atributo a editar. Al seleccionar **Categoría (6)**, se listan las categorías disponibles y se ingresa el ID de la nueva.

>  Cuando se actualiza el stock, la disponibilidad del producto se actualiza automáticamente: stock > 0 → disponible, stock = 0 → no disponible.

---

### Eliminar producto

```
=== PRODUCTOS ===
  4. Eliminar   ← seleccionar

ID del producto a eliminar: 3
Seguro quiere eliminar producto: 3
Ingrese S para confirmar y N para volver: S

→ Producto eliminado. ID: 3
```

---

##  Usuarios

### Listar usuarios

```
=== USUARIOS ===
  1. Listar   ← seleccionar
```

---

### Crear usuario

```
=== USUARIOS ===
  2. Crear   ← seleccionar

=== CREAR USUARIO ===
Nombre: Juan
Apellido: Pérez
Mail: juan@mail.com
Celular: 2615551234
Contrasenia: clave123
Rol (1 = ADMIN, 2 = USUARIO): 2

→ Usuario creado con ID: 5
```

**Campos requeridos:**
| Campo | Descripción |
|---|---|
| Nombre | Texto |
| Apellido | Texto |
| Mail | Debe ser único en el sistema |
| Celular | Texto (puede incluir código de área) |
| Contraseña | Texto |
| Rol | `1` para ADMIN, `2` para USUARIO |

---

### Editar usuario

```
=== USUARIOS ===
  3. Editar   ← seleccionar

--- USUARIOS ---
...lista de usuarios...

=== EDITAR USUARIO ===
ID del usuario a editar: 5
Ingrese el atributo a modificar ( NOMBRE | APELLIDO | MAIL | CELULAR | CONTRASEÑA ): MAIL
Nuevo valor: juanperez@mail.com

→ Usuario actualizado. ID: 5
```

**Atributos editables:** `NOMBRE`, `APELLIDO`, `MAIL`, `CELULAR`, `CONTRASEÑA` (en mayúsculas).

---

### Eliminar usuario

```
=== USUARIOS ===
  4. Eliminar   ← seleccionar

...lista de usuarios...

ID del usuario a eliminar: 5
Confirmar eliminacion? (S/N): S

→ Usuario eliminado (baja logica).
```

---

##  Pedidos

> Para crear un pedido necesitás tener al menos un **usuario** y un **producto** creados previamente.

El menú de pedidos tiene una opción extra respecto a los otros módulos:

```
=== PEDIDOS ===
  1. Listar
  2. Crear
  3. Editar
  4. Eliminar
  5. Actualizar detalle
```

### Listar pedidos

```
=== PEDIDOS ===
  1. Listar   ← seleccionar
```

Muestra todos los pedidos con su ID, fecha, estado, forma de pago, usuario y el detalle de productos.

---

### Crear pedido

El flujo de creación tiene varias etapas:

**Etapa 1 — Elegir usuario y forma de pago**

```
=== CREAR PEDIDO ===
Usuarios disponibles:
| ID: 1 | Juan Pérez ...

ID de usuario: 1

Forma de pago:
1. TARJETA
2. TRANSFERENCIA
3. EFECTIVO
Seleccione: 3
```

**Etapa 2 — Agregar productos al pedido**

```
PRODUCTO | ID: 1 | Pizza Muzzarella | Precio: 1500.0 ...
PRODUCTO | ID: 2 | Empanada Carne   | Precio: 300.0  ...

ID de producto (0 para terminar): 1
Cantidad: 2

→ Detalle agregado. Total parcial: $3000.0

¿Agregar otro producto? (S/N): S

ID de producto (0 para terminar): 2
Cantidad: 4

→ Detalle agregado. Total parcial: $4200.0

¿Agregar otro producto? (S/N): N
```

Podés seguir agregando productos ingresando `S`. Cuando terminés, ingresás `N` o `0` como ID de producto.

**Resultado final:**

```
→ Pedido creado. ID: 7 | Total: $4200.0
```

>  El pedido se crea en estado `PENDIENTE` por defecto.

---

### Editar pedido (estado y forma de pago)

```
=== PEDIDOS ===
  3. Editar   ← seleccionar

=== ACTUALIZAR PEDIDO ===
...lista de pedidos...

ID del pedido: 7

Nuevo estado:
0. Sin cambio
1. PENDIENTE
2. CONFIRMADO
3. TERMINADO
4. CANCELADO
→ 2

Nueva forma de pago:
0. Sin cambio
1. TARJETA
2. TRANSFERENCIA
3. EFECTIVO
→ 0

→ Pedido actualizado.
```

Seleccionando `0` en cualquier campo, ese dato **no se modifica**. Útil para cambiar solo el estado sin tocar la forma de pago y viceversa.

**Flujo de estados sugerido:**
```
PENDIENTE → CONFIRMADO → TERMINADO
                       ↘ CANCELADO
```

---

### Eliminar pedido

```
=== PEDIDOS ===
  4. Eliminar   ← seleccionar

ID del pedido: 7
¿Seguro quiere eliminar el pedido 7?
Ingrese S para confirmar o N para cancelar: S

→ Pedido eliminado.
```

---

### Actualizar detalle del pedido

Permite buscar o eliminar un producto específico dentro de un pedido existente.

```
=== PEDIDOS ===
  5. Actualizar detalle   ← seleccionar

=== GESTIONAR DETALLE DE PEDIDO ===
...lista de pedidos...

ID del pedido: 7

...lista de productos...

ID del producto: 2

1. BUSCAR
2. ELIMINAR
Seleccione: 1

→ DetallePedido | Producto: Empanada Carne | Cantidad: 4 | Subtotal: $1200.0
```

Si seleccionás **ELIMINAR (2)**:

```
¿Confirma eliminar? (S/N): S
→ Detalle eliminado.
```

> ️ Al eliminar un detalle, el total del pedido no se recalcula automáticamente en pantalla. Para verlo actualizado, volver a listar los pedidos.

