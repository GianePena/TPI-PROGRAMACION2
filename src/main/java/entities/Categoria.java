package entities;


import exceptions.StringInvalidException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Categoria extends Base{
    private String nombre;
    private String descripcion;
    private List<Producto> productos=new ArrayList<>();

    public Categoria(Long id, String nombre, String descripcion, LocalDateTime localDateTime) {
        super(id, localDateTime);
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    public Categoria(String nombre, String descripcion) {
        super();
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setNombre(String nombre) throws StringInvalidException{
        this.nombre = nombre;

    }
    public void agregarProducto(Producto producto){
        this.productos.add(producto);
    }


    public void setDescripcion(String descripcion) throws StringInvalidException{
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        String productosStr="";
        for(Producto p:productos){
            //productosStr+=p.getNombre()+'\n';
        }
        return super.toString()+
                "Categoria: '" + nombre + '\'' +
                "Descripcion: '" + descripcion + '\''+
                (productos.isEmpty()? "Categoria sin productos":"Productos: "+ '\n' + productosStr );
    }
}
