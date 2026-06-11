package service;

import dao.CategoriaDao;
import dao.CategoriaDaoImpl;
import entities.Categoria;
import exceptions.CategoriaExistenteException;
import exceptions.CategoriaNoEncontradaException;
import exceptions.IdInvalidException;
import exceptions.StringInvalidException;
import utilidades.Utilidades;


import java.util.List;

public class CategoriaService {

    private CategoriaDao dao= new CategoriaDaoImpl();
    public List<Categoria>listarCategorias(){
        return dao.listar();
    }

    public Categoria buscarPorId(Long id)throws IdInvalidException{
        Utilidades.validarId(id);
        return dao.buscarPorId(id);
    }

    public Long agregarCategoria(String nombre, String descripcion) throws CategoriaExistenteException,StringInvalidException{
        Utilidades.validarString(nombre);
        Utilidades.validarString(descripcion);

        Categoria existente=dao.buscarPorNombre(nombre);
        if(existente!=null){
            throw new CategoriaExistenteException("Error ya existe una categoria con nombre " + nombre);
        }
        Categoria categoria=new Categoria(nombre, descripcion);
        return dao.guardar(categoria);
    }

    public Long actualizarCategoria(Long id, String atributo, String valor)throws CategoriaNoEncontradaException, StringInvalidException,IllegalArgumentException, IdInvalidException{
        Utilidades.validarId(id);
        Utilidades.validarString(atributo);
        Utilidades.validarString(valor);
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
    public Long eliminarCategoria(Long id)throws CategoriaNoEncontradaException, IdInvalidException {
        Utilidades.validarId(id);
        Categoria categoria=dao.buscarPorId(id);
        if (categoria==null){
            throw new CategoriaNoEncontradaException("Error categoria no encontrada");
        }
        categoria.setEliminado(true);
        categoria.setDeletedAt();
        return dao.eliminar(categoria);
    }



}
