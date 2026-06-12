package menu;

import entities.Categoria;
import entities.Producto;
import exceptions.*;
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
            Utilidades.mostrarLista(productos);
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
            Utilidades.mostrarLista(categoriaService.listarCategorias());

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

        } catch (EsExistenteException |
                 StringInvalidException |
                 IllegalArgumentException e) {

            System.out.println(e.getMessage());
        }
    }

    private static void mostrarAtributos(){
        System.out.println("""
                
                --- ATRIBUTO A MODIFICAR ---
                1. Nombre
                2. Precio
                3. Descripcion
                4. Stock
                5. Imagen
                6. Categoria
                """);

        System.out.print("Seleccione una opcion: ");
    }

    private static void editarProducto(Scanner sc) {

        try {
            System.out.println("\n--- PRODUCTOS DISPONIBLES ---");
            service.listarProductos().forEach(System.out::println);

            System.out.println("\n--- EDITAR PRODUCTO ---");
            System.out.print("ID del producto a editar: ");
            Long id = Utilidades.leerLong(sc);

            MainProducto.mostrarAtributos();
            int opcion = Utilidades.leerInt(sc);
            String atributo;
            String nuevoValor;

            switch (opcion) {

                case 1:
                    atributo = "nombre";
                    System.out.print("- Nuevo nombre: ");
                    nuevoValor = Utilidades.leerString(sc);
                    break;

                case 2:
                    atributo = "precio";
                    System.out.print("- Nuevo precio: ");
                    nuevoValor = Utilidades.leerString(sc);
                    break;

                case 3:
                    atributo = "descripcion";
                    System.out.print("- Nueva descripcion: ");
                    nuevoValor = Utilidades.leerString(sc);
                    break;

                case 4:
                    atributo = "stock";
                    System.out.print("- Nuevo stock: ");
                    nuevoValor = Utilidades.leerString(sc);
                    break;

                case 5:
                    atributo = "imagen";
                    System.out.print("- Nueva imagen: ");
                    nuevoValor = Utilidades.leerString(sc);
                    break;

                case 6:
                    atributo = "categoria";
                    System.out.println("\n--- CATEGORIAS DISPONIBLES ---");
                    categoriaService.listarCategorias().forEach(System.out::println);

                    System.out.print("ID de la nueva categoria: ");
                    Long idCategoria = Utilidades.leerLong(sc);
                    nuevoValor = idCategoria.toString();
                    break;

                default: throw new IllegalArgumentException("Opcion invalida");
            }
            Long idActualizado = service.actualizarProducto(id, atributo, nuevoValor);
            System.out.println("Producto actualizado. ID: " + idActualizado);

        } catch (NoEncontradoException | StringInvalidException |
                 IllegalArgumentException | IdInvalidException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void eliminarProducto(Scanner sc) {

        try {
            System.out.println("--- ELIMINAR PRODUCTO ---");
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

        } catch (NoEncontradoException | IdInvalidException e) {

            System.out.println(e.getMessage());
        }
    }
}