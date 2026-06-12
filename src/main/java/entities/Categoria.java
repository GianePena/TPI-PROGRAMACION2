package entities;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Categoria extends Base{
    private String nombre;
    private String descripcion;
    private List<Producto> productos = new ArrayList<>();

    public Categoria(String nombre, String descripcion) {
        super();
        setNombre(nombre);
        setDescripcion(descripcion);
    }

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

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public void agregarProducto(Producto producto){
        this.productos.add(producto);
    }

    @Override
    public String toString() {
        String productosStr="";
        for(Producto p:productos){
            productosStr+= " - "+p.getNombre()+'\n';
        }
        return "\nCATEGORIA "+ nombre.toUpperCase() + " " +super.toString() + '\n' +
                "| Descripcion: " + descripcion + '\n' +
                (productos.isEmpty()? "| Categoria sin productos":"| Productos: "+ '\n' + productosStr );
    }

}
