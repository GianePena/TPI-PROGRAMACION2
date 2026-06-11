package utilidades;

import entities.Categoria;
import entities.Producto;
import exceptions.StringInvalidException;
import menu.MainCategoria;
import menu.MainProducto;
import menu.MainUsuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Utilidades {

    static public LocalDateTime generarFecha() {
        return LocalDateTime.now();
    }

    static public void validarString(String str) throws StringInvalidException {
        if (str == null || str.trim().isEmpty()) {
            throw new StringInvalidException("Error ingreso invalido");
        }
    }
    public static void mostrarListaCategoria(List<Categoria> categorias){
        for(Categoria c:categorias){
            System.out.println(c);
        }
    }
    public static void mostrarListaProductos(List<Producto> productos){
        for(Producto p: productos){
            System.out.println(p);
        }
    }
    static void mostrarOpcionesMenu(){
        System.out.println("=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
        System.out.println("1. Categorias");
        System.out.println("2. Productos");
        System.out.println("3. Usuarios");
        System.out.println("4. Pedidos");
        System.out.println("0. Salir");
        System.out.print("Ingrese una opcion: ");
    }

    static void mostrarSubopcionesMenu(Scanner sc) {
        System.out.println("  1. Listar");
        System.out.println("  2. Crear");
        System.out.println("  3. Editar");
        System.out.println("  4. Eliminar");
        System.out.print("Ingrese una opcion: ");

    }

    public static String leerString(Scanner sc) {
        while (true) {
            try {
                String str = sc.nextLine().trim();
                Utilidades.validarString(str);
                return str;
            } catch (StringInvalidException e) {
                System.out.print("Texto invalido " + e.getMessage());
                System.out.println("Intente nuevamente");
            }
        }
    }

    public static Long leerLong(Scanner sc) {
        while (true) {
            try {
                Long id = Long.parseLong(sc.nextLine().trim());
                return id;
            } catch (NumberFormatException e) {
                System.out.print("ID invalida: " + e.getMessage());
            }
        }
    }

    public static int leerInt(Scanner sc) {
        while (true) {
            try {
                int num = Integer.parseInt(sc.nextLine().trim());
                return num;
            } catch (NumberFormatException e) {
                System.out.print("Opcion invalida, ingrese un numero: " + e.getMessage());
            }
        }
    }

    public static void mostrarMenu(Scanner sc) {
        while (true) {
            mostrarOpcionesMenu();
            int opcion=leerInt(sc);
            int subOpcion;
            switch (opcion){
                case 1:
                    System.out.println("=== CATEGORIAS ===");
                    mostrarSubopcionesMenu(sc);
                    subOpcion = leerInt(sc);
                    MainCategoria.menuCategoria(subOpcion, sc);
                    continue;
                case 2:
                    System.out.println("=== PRODUCTOS ===");
                    mostrarSubopcionesMenu(sc);
                    subOpcion = leerInt(sc);
                    MainProducto.menuProducto(subOpcion, sc);
                    continue;

                case 3:
                    System.out.println("=== USUARIOS ===");
                    mostrarSubopcionesMenu(sc);
                    subOpcion = leerInt(sc);
                    MainUsuario.menuUsuario(subOpcion, sc);
                    continue;

                case 4:
                    System.out.println("=== PEDIDOS ===");
                    mostrarSubopcionesMenu(sc);
                    continue;

                case 0:
                    System.out.println("SALIDA DEL SISTEMA DE PEDIDOS!!!");
                    return;

                default:
                    System.out.println("Error ingrese una opcion correcta de 0 a 4");
            }
        }
    }
}