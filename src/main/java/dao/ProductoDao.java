package dao;

import entities.Producto;

import java.util.List;

public interface ProductoDao {
     List<Producto> listar();
     Producto buscarPorNombre(String nombre);
     Producto buscarPorId(Long id);
     Long guardar(Producto p);
     Long actualizar(Producto p);
     Long eliminar(Producto p);
}
