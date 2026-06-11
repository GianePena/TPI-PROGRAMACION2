package utilidades;


import exceptions.StringInvalidException;
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
                System.out.println("Opcion invalida, ingrese un numero : " + e.getMessage());
            }
        }
    }

}