package menu;

import entities.Categoria;
import exceptions.CategoriaExistenteException;
import exceptions.CategoriaNoEncontradaException;
import exceptions.StringInvalidException;
import service.CategoriaService;
import utilidades.Utilidades;

import java.util.List;
import java.util.Scanner;

public class MainCategoria {
    private static final CategoriaService service = new CategoriaService();
    public static void menuCategoria(int opcion, Scanner sc) {
        switch (opcion) {
            case 1:
                listarCategorias();
                break;

            case 2:
                crearCategoria(sc);
                break;

            case 3:
                editarCategoria(sc);
                break;

            case 4:
                eliminarCategoria(sc);
                break;

            default:
                System.out.println("Opcion invalida. Ingrese una opcion del 1 al 4.");
        }
    }

    private static void listarCategorias(){
        System.out.println("=== TODAS LAS CATEGORIAS ===");
        List<Categoria>categorias= service.listarCategorias();
        if(categorias.isEmpty()){
            System.out.println("No hay categorías cargadas");
        }else{
            Utilidades.mostrarListaCategoria(categorias);
        }
    }
    private static void crearCategoria(Scanner sc){
        System.out.println("=== CREAR CATEGORIA ===");
        try {
            System.out.print("Nombre: ");
            String nombre = Utilidades.leerString(sc);

            System.out.print("Descripcion: ");
            String descripcion = Utilidades.leerString(sc);

            Long id = service.agregarCategoria(nombre,descripcion);
            System.out.println("Categoria creada con ID: " + id);

        }catch (CategoriaExistenteException | StringInvalidException e) {
            System.out.println(e.getMessage());
        }

    }
    private static void editarCategoria(Scanner sc){
        try {
            System.out.print("ID de la categoria a editar: ");
            Long id = Utilidades.leerLong(sc);

            System.out.println("Atributo a modificar (nombre || descripcion): ");
            String atributo = Utilidades.leerString(sc);

            System.out.print("Nuevo valor: ");
            String nuevoValor = Utilidades.leerString(sc);

            Long idActualizado = service.actualizarCategoria(id, atributo, nuevoValor);
            System.out.println("Categoria actualizada. ID: " + idActualizado);

        } catch (CategoriaNoEncontradaException | StringInvalidException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void eliminarCategoria(Scanner sc){
        try {
            System.out.print("ID de la categoria a eliminar: ");
            Long id = Utilidades.leerLong(sc);

            System.out.println("Seguro quiere eliminar categoria: "+ id );
            System.out.println("Ingrese S para confirmar y N para volver: S | N: ");
            String confirmacion = Utilidades.leerString(sc);
            if(confirmacion.toUpperCase().equals("N")){
                System.out.println("Eliminacion cancelada");
                return;
            }
            Long idEliminado = service.eliminarCategoria(id);
            System.out.println("Categoria eliminada. ID: " + idEliminado);

        } catch (CategoriaNoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

}
