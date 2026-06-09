package menu;

import entities.Categoria;
import entities.Producto;
import exceptions.ProductoExistenteException;
import exceptions.ProductoNoEncontradoException;
import exceptions.StringInvalidException;
import service.CategoriaService;
import service.ProductoService;
import utilidades.Utilidades;

import java.util.List;
import java.util.Scanner;

public class MainProducto {

    private static final ProductoService service = new ProductoService();
    private static final CategoriaService categoriaService = new CategoriaService();
    public static void menuProducto(int opcion, Scanner sc) {
        switch (opcion) {

            case 1:
                listarProductos();
                break;

            case 2:
                crearProducto(sc);
                break;

            case 3:
                editarProducto(sc);
                break;

            case 4:
                eliminarProducto(sc);
                break;

            default:
                System.out.println("Opcion invalida. Ingrese una opcion del 1 al 4.");
        }
    }

    private static void listarProductos() {

        System.out.println("=== TODOS LOS PRODUCTOS ===");

        List<Producto> productos = service.listarProductos();

        if (productos.isEmpty()) {
            System.out.println("No hay productos cargados");
        } else {
            Utilidades.mostrarListaProductos(productos);
        }
    }

    private static void crearProducto(Scanner sc) {

        System.out.println("=== CREAR PRODUCTO ===");

        try {

            System.out.print("Nombre: ");
            String nombre = Utilidades.leerString(sc);

            System.out.print("Precio: ");
            double precio = Double.parseDouble(Utilidades.leerString(sc));

            System.out.print("Descripcion: ");
            String descripcion = Utilidades.leerString(sc);

            System.out.print("Stock: ");
            int stock = Integer.parseInt(Utilidades.leerString(sc));

            System.out.print("Imagen: ");
            String imagen = Utilidades.leerString(sc);

            System.out.println("=== CATEGORIAS DISPONIBLES ===");
            Utilidades.mostrarListaCategoria(categoriaService.listarCategorias());

            System.out.print("ID Categoria: ");
            Long idCategoria = Utilidades.leerLong(sc);

            Categoria categoria = null;

            for (Categoria c : categoriaService.listarCategorias()) {
                if (c.getId().equals(idCategoria)) {
                    categoria = c;
                    break;
                }
            }

            if (categoria == null) {
                throw new IllegalArgumentException("Categoria inexistente");
            }

            Long id = service.agregarProducto(
                    nombre,
                    precio,
                    descripcion,
                    stock,
                    imagen,
                    categoria
            );

            System.out.println("Producto creado con ID: " + id);

        } catch (ProductoExistenteException |
                 StringInvalidException |
                 IllegalArgumentException e) {

            System.out.println(e.getMessage());
        }
    }

    private static void editarProducto(Scanner sc) {

        try {

            System.out.print("ID del producto a editar: ");
            Long id = Utilidades.leerLong(sc);

            System.out.println("""
                    
                    Atributo a modificar:
                    nombre
                    precio
                    descripcion
                    stock
                    imagen
                    """);

            String atributo = Utilidades.leerString(sc);

            System.out.print("Nuevo valor: ");
            String nuevoValor = Utilidades.leerString(sc);

            Long idActualizado =
                    service.actualizarProducto(
                            id,
                            atributo,
                            nuevoValor
                    );

            System.out.println("Producto actualizado. ID: " + idActualizado);

        } catch (ProductoNoEncontradoException |
                 StringInvalidException |
                 IllegalArgumentException e) {

            System.out.println(e.getMessage());
        }
    }

    private static void eliminarProducto(Scanner sc) {

        try {

            System.out.print("ID del producto a eliminar: ");
            Long id = Utilidades.leerLong(sc);

            System.out.println("Seguro quiere eliminar producto: " + id);
            System.out.print("Ingrese S para confirmar y N para volver: ");

            String confirmacion = Utilidades.leerString(sc);

            if (confirmacion.equalsIgnoreCase("N")) {

                System.out.println("Eliminacion cancelada");
                return;
            }

            Long idEliminado = service.eliminarProducto(id);

            System.out.println("Producto eliminado. ID: " + idEliminado);

        } catch (ProductoNoEncontradoException e) {

            System.out.println(e.getMessage());
        }
    }
}