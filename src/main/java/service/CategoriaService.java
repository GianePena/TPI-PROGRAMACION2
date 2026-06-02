package service;

import dao.CategoriaDao;
import dao.CategoriaDaoImpl;
import entities.Categoria;
import exceptions.CategoriaExistenteException;
import exceptions.CategoriaNoEncontradaException;
import exceptions.StringInvalidException;


import java.util.List;

public class CategoriaService {

    private CategoriaDao dao= new CategoriaDaoImpl();
    public List<Categoria>listarCategorias(){
        return dao.listar();
    }
    public Long agregarCategoria(String nombre, String descripcion) throws CategoriaExistenteException,StringInvalidException{
        Categoria existente=dao.buscarPorNombre(nombre);
        if(existente!=null){
            throw new CategoriaExistenteException("Error ya existe una categoria con nombre " + nombre);
        }
        Categoria categoria=new Categoria(nombre, descripcion);
        return dao.guardar(categoria);
    }
    public Long actualizarCategoria(Long id, String atributo, String valor)throws CategoriaNoEncontradaException, StringInvalidException,IllegalArgumentException{
        Categoria categoria=dao.buscarPorId(id);
        if (categoria==null){
            throw new CategoriaNoEncontradaException("Error categoria no encontrada");
        }
        categoria.setUpdatedAt();
        switch (atributo.toLowerCase()){
            case "nombre":
                categoria.setNombre(valor);
                return dao.actualizar(categoria);
            case "descripcion":
                categoria.setDescripcion(valor);
                return dao.actualizar(categoria);
            default:
                throw new StringInvalidException("Error atributo a modificar invalido");
        }
    }

    public Long eliminarCategoria(Long id)throws CategoriaNoEncontradaException{
        Categoria categoria=dao.buscarPorId(id);
        if (categoria==null){
            throw new CategoriaNoEncontradaException("Error categoria no encontrada");
        }
        categoria.setEliminado(true);
        categoria.setDeletedAt();
        return dao.eliminar(categoria);
    }



}
