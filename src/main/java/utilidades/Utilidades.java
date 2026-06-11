package utilidades;


import exceptions.IdInvalidException;
import exceptions.StringInvalidException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Utilidades {

    static public LocalDateTime generarFecha() {
        return LocalDateTime.now();
    }


    public static <T> void mostrarLista(List<T> lista){
        for(T elemento : lista){
            System.out.println(elemento);
        }
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
    public static void validarString(String str) throws StringInvalidException {
        if (str == null || str.isBlank()) {
            throw new StringInvalidException("El texto no puede ser vacío");
        }
    }
    public static void validarId(Long id) throws IdInvalidException {
        if (id == null || id <= 0) {
            throw new IdInvalidException("El ID no puede ser nulo o menor a 0");
        }
    }
    public static Long leerLong(Scanner sc) {
        while (true) {
            try {
                Long id = Long.parseLong(sc.nextLine().trim());
                validarId(id);  // ← valida que sea positivo
                return id;
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido. Intente nuevamente");
            } catch (IdInvalidException e) {
                System.out.println("ID inválido: " + e.getMessage() + ". Intente nuevamente");
            }
        }
    }


    public static int leerInt(Scanner sc) {
        while (true) {
            try {
                int num = Integer.parseInt(sc.nextLine().trim());
                return num;
            } catch (NumberFormatException e) {
                System.out.println("Opcion invalida, ingrese un numero : " + e.getMessage());
            }
        }
    }

}