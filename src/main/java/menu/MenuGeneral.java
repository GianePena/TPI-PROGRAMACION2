package menu;

import utilidades.Utilidades;

import java.util.Scanner;

public class MenuGeneral {
    static void mostrarOpcionesMenu(){
        System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
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
    public static void mostrarMenu(Scanner sc) {
        while (true) {
            mostrarOpcionesMenu();
            int opcion= Utilidades.leerInt(sc);
            int subOpcion;
            switch (opcion){
                case 1:
                    System.out.println("=== CATEGORIAS ===");
                    mostrarSubopcionesMenu(sc);
                    subOpcion = Utilidades.leerInt(sc);
                    MainCategoria.menuCategoria(subOpcion, sc);
                    continue;
                case 2:
                    System.out.println("=== PRODUCTOS ===");
                    mostrarSubopcionesMenu(sc);
                    subOpcion = Utilidades.leerInt(sc);
                    MainProducto.menuProducto(subOpcion, sc);
                    continue;

                case 3:
                    System.out.println("=== USUARIOS ===");
                    mostrarSubopcionesMenu(sc);
                    subOpcion =  Utilidades.leerInt(sc);
                    MainUsuario.menuUsuario(subOpcion, sc);
                    continue;

                case 4:
                    System.out.println("=== PEDIDOS ===");
                    mostrarSubopcionesMenu(sc);
                   subOpcion =  Utilidades.leerInt(sc);
                   MainPedido.menuPedido(subOpcion,sc);
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