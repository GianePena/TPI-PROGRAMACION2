package entities;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Categoria extends Base{
    private String nombre;
    private String descripcion;
    private List<Producto> productos = new ArrayList<>();

    // Constructor para una categoria nueva
    public Categoria(String nombre, String descripcion) {
        super();
        setNombre(nombre);
        setDescripcion(descripcion);
    }

    // Constructor para reconstruir desde la DB
    public Categoria(Long id, boolean eliminado, LocalDateTime createdAt, String nombre, String descripcion) {
        super(id, eliminado, createdAt);
        setNombre(nombre);
        setDescripcion(descripcion);
    }

    public Categoria(String nombre) {
        super();
        this.nombre = nombre;
        this.descripcion = "Sin descripcion";
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre inválido");
        }
        this.nombre = nombre;
    }


    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("Descripción inválida");
        }
        this.descripcion = descripcion;
    }

    public List<Producto> getProductos() {
        return productos;
    }
    public void setProductos(List<Producto> productos) {
        if (productos == null) {
            throw new IllegalArgumentException("Lista inválida");
        }
        this.productos = productos;
    }

    // Metodos
    public void agregarProducto(Producto producto){
        // TODO validarlo antes de agregarlo
        this.productos.add(producto);
    }

    @Override
    public String toString() {
        String productosStr="";
        for(Producto p:productos){
            productosStr+=p.getNombre()+'\n';
        }
        return super.toString() +
                "| Categoria: " + nombre + '\'' +
                "| Descripcion: " + descripcion + '\''+
                (productos.isEmpty()? "| Categoria sin productos":"| Productos: "+ '\n' + productosStr );
    }
}
