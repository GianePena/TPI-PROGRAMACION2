package entities;
import java.time.LocalDateTime;

public class Producto extends Base {

    // TODO validar en service
    private String nombre;
    private double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private boolean disponible;
    private Categoria categoria; // Relación N:1


    // Constructor producto nuevo
    public Producto(String nombre, double precio, String descripcion, int stock, String imagen, Categoria categoria) {
        super();
        this.setNombre(nombre);
        this.setPrecio(precio);
        this.setDescripcion(descripcion);
        this.setStock(stock);
        this.setImagen(imagen);
        this.setDisponible(stock > 0);
        this.setCategoria(categoria);
    }

    // Constructor producto recuperado de la DB
    public Producto(Long id, boolean eliminado, LocalDateTime createdAt, String nombre, double precio, String descripcion, int stock, String imagen, Boolean disponible, Categoria categoria) {
        super(id, eliminado, createdAt);
        this.setNombre(nombre);
        this.setPrecio(precio);
        this.setDescripcion(descripcion);
        this.setStock(stock);
        this.setImagen(imagen);
        this.setDisponible(disponible);
        this.setCategoria(categoria);
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {this.nombre = nombre;}

    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {this.precio = precio;}

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
        this.disponible = stock > 0;
    }

    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {this.imagen = imagen;}

    public boolean isDisponible() {
        return disponible;
    }
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Categoria getCategoria() {return categoria;}
    public void setCategoria(Categoria categoria) {this.categoria = categoria;}

    @Override
    public String toString() {
        return "PRODUCTO: " +
                "\n| Nombre: " + nombre +
                "\n| Precio: " + precio +
                "\n| Descripción: " + descripcion +
                "\n| Stock: " + stock +
                "\n| Imagen: " + imagen +
                "\n| Disponible: " + disponible +
                "\n| Categoria: " + categoria.getNombre() +
                "\n" + super.toString();
    }
}

