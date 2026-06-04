package dao;

import entities.Producto;

import java.util.List;

public interface ProductoDao {
    public List<Producto> listar();
    public Producto buscarPorNombre(String nombre);
    public Producto buscarPorId(Long id);
    public Long guardar(Producto p);
    public Long actualizar(Producto p);
    public Long eliminar(Producto p);
}
