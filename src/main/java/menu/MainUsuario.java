package menu;

import entities.Usuario;
import enums.Rol;
import exceptions.IdInvalidException;
import exceptions.StringInvalidException;
import exceptions.EsExistenteException;
import exceptions.NoEncontradoException;
import service.UsuarioService;
import utilidades.Utilidades;

import java.util.List;
import java.util.Scanner;

public class MainUsuario {

    private static final UsuarioService usuarioService =new UsuarioService();
    public static void menuUsuario(int opcion, Scanner sc){
        switch (opcion){
            case 1:
                listarUsuarios();
                break;

            case 2:
                crearUsuario(sc);
                break;

            case 3:
                editarUsuario(sc);
                break;

            case 4:
                eliminarUsuario(sc);
                break;

            default:
                System.out.println("Opcion invalida. Ingrese una opcion del 1 al 4.");
        }
    }

    public static void listarUsuarios() {
        System.out.println("\n=== LISTADO DE USUARIOS ===");
        List<Usuario> lista = usuarioService.listarUsuarios();

        if (lista.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
        } else {
            for (Usuario u : lista) {
                System.out.println(u.toString());
            }
        }
    }

    private static void crearUsuario(Scanner sc) {
        System.out.println("\n=== CREAR USUARIO ===");

        System.out.print("Nombre: ");
        String nombre = Utilidades.leerString(sc);

        System.out.print("Apellido: ");
        String apellido = Utilidades.leerString(sc);

        System.out.print("Mail: ");
        String mail = Utilidades.leerString(sc);

        System.out.print("Celular: ");
        String celular = Utilidades.leerString(sc);

        System.out.print("Contrasenia: ");
        String contrasenia = Utilidades.leerString(sc);

        System.out.println("Rol (1 = ADMIN, 2 = USUARIO): ");
        int opRol = Utilidades.leerInt(sc);
        Rol rol = (opRol == 1) ? Rol.ADMIN : Rol.USUARIO;

        try {
            Long id = usuarioService.agregarUsuario(nombre, apellido, mail, celular, contrasenia, rol);
            System.out.println("Usuario creado con ID: " + id);
        } catch (StringInvalidException | EsExistenteException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private static void editarUsuario(Scanner sc) {
        System.out.println("\n--- USUARIOS ---");
        usuarioService.listarUsuarios().forEach(System.out::println);
        System.out.println("\n=== EDITAR USUARIO ===");

        System.out.print("ID del usuario a editar: ");
        Long id = Utilidades.leerLong(sc);

        System.out.print("Ingrese el atributo a modificar( NOMBRE | APELLIDO | MAIL | CELULAR | CONTRASEÑA) : ");
        String atributo= Utilidades.leerString(sc);

        System.out.print("Nuevo valor: ");
        String valor = Utilidades.leerString(sc);

        try {
            Long idActualizado= usuarioService.actualizarUsuario(id, atributo,valor);
            System.out.println("Usuario actualizada. ID: " + idActualizado);
        } catch (StringInvalidException | IdInvalidException | NoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void eliminarUsuario(Scanner sc) {
        System.out.println("\n=== ELIMINAR USUARIO ===");
        listarUsuarios();

        System.out.print("ID del usuario a eliminar: ");
        Long id = Utilidades.leerLong(sc);

        System.out.print("Confirmar eliminacion? (S/N): ");
        String confirm = Utilidades.leerString(sc);

        if (confirm.equalsIgnoreCase("S")) {
            try {
                usuarioService.eliminarUsuario(id);
                System.out.println("Usuario eliminado (baja logica).");
            } catch (IdInvalidException | NoEncontradoException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Eliminacion cancelada.");
        }
    }
}