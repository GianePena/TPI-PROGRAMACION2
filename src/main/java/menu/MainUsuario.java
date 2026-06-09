package menu;

import entities.Usuario;
import enums.Rol;
import service.UsuarioService;
import utilidades.Utilidades;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MainUsuario {

    private final UsuarioService usuarioService;

    public MainUsuario(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public void mostrarMenu(Scanner sc) {
        int opcion;
        do {
            System.out.println("\n=== MENU USUARIOS ===");
            System.out.println("1. Listar usuarios");
            System.out.println("2. Crear usuario");
            System.out.println("3. Editar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione: ");

            opcion = Utilidades.leerInt(sc);

            switch (opcion) {
                case 1 -> listarUsuarios();
                case 2 -> crearUsuario(sc);
                case 3 -> editarUsuario(sc);
                case 4 -> eliminarUsuario(sc);
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opcion invalida");
            }
        } while (opcion != 0);
    }

    private void listarUsuarios() {
        System.out.println("\n--- LISTADO DE USUARIOS ---");
        List<Usuario> lista = usuarioService.listarUsuarios();

        if (lista.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
        } else {
            for (Usuario u : lista) {
                System.out.println(u.toStringLista());
            }
        }
    }

    private void crearUsuario(Scanner sc) {
        System.out.println("\n--- CREAR USUARIO ---");

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
            Long id = usuarioService.createUsuario(nombre, apellido, mail, celular, contrasenia, rol);
            System.out.println("Usuario creado con ID: " + id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editarUsuario(Scanner sc) {
        System.out.println("\n--- EDITAR USUARIO ---");
        listarUsuarios();

        System.out.print("ID del usuario a editar: ");
        Long id = (long) Utilidades.leerInt(sc);

        Optional<Usuario> opt = usuarioService.buscarPorId(id);
        if (opt.isEmpty()) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        Usuario u = opt.get();
        System.out.println("Editando: " + u.getNombre() + " " + u.getApellido());

        System.out.print("Nuevo nombre [" + u.getNombre() + "]: ");
        String nombre = Utilidades.leerString(sc);
        if (!nombre.isEmpty()) u.setNombre(nombre);

        System.out.print("Nuevo apellido [" + u.getApellido() + "]: ");
        String apellido = Utilidades.leerString(sc);
        if (!apellido.isEmpty()) u.setApellido(apellido);

        System.out.print("Nuevo mail [" + u.getMail() + "]: ");
        String mail = Utilidades.leerString(sc);
        if (!mail.isEmpty()) u.setMail(mail);

        System.out.print("Nuevo celular [" + u.getCelular() + "]: ");
        String celular = Utilidades.leerString(sc);
        if (!celular.isEmpty()) u.setCelular(celular);

        System.out.print("Nueva contrasenia: ");
        String contrasenia = Utilidades.leerString(sc);
        if (!contrasenia.isEmpty()) u.setContrasenia(contrasenia);

        try {
            usuarioService.updateUsuario(id, u.getNombre(), u.getApellido(), u.getMail(),
                    u.getCelular(), u.getContrasenia(), u.getRol());
            System.out.println("Usuario actualizado.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminarUsuario(Scanner sc) {
        System.out.println("\n--- ELIMINAR USUARIO ---");
        listarUsuarios();

        System.out.print("ID del usuario a eliminar: ");
        Long id = (long) Utilidades.leerInt(sc);

        System.out.print("Confirmar eliminacion? (S/N): ");
        String confirm = Utilidades.leerString(sc);

        if (confirm.equalsIgnoreCase("S")) {
            try {
                usuarioService.deleteUsuario(id);
                System.out.println("Usuario eliminado (baja logica).");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Eliminacion cancelada.");
        }
    }
}