package exceptions;

public class ProductoExistenteException extends Exception {
    public ProductoExistenteException(String mensaje) {
        super(mensaje);
    }
}
