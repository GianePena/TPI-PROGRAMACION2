package dao;

import entities.Categoria;

import java.util.List;

public interface CategoriaDao {
    public List<Categoria>listar();
    public Categoria buscarPorNombre(String nombre);
    public Categoria buscarPorId(Long id);
    public Long guardar(Categoria c);
    public Long actualizar(Categoria c);
    public Long eliminar(Categoria c);
}
