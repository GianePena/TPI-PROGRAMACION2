package service;

import dao.UsuarioDao;
import dao.UsuarioDaoImpl;
import entities.Usuario;
import enums.Rol;
import exceptions.IdInvalidException;
import exceptions.StringInvalidException;
import exceptions.EsExistenteException;
import exceptions.NoEncontradoException;
import utilidades.Utilidades;


import java.util.List;


public class UsuarioService {
    private final UsuarioDao usuarioDao;

    public UsuarioService() {
        this.usuarioDao = new UsuarioDaoImpl();
    }

    public UsuarioService(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public Long createUsuario(String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol)throws StringInvalidException {
        Utilidades.validarString(nombre);
        Utilidades.validarString(apellido);
        Utilidades.validarString(contrasenia);
        Utilidades.validarString(mail);
        if(usuarioDao.existeEmail(mail)){
            throw new IllegalArgumentException("El email debe ser unico: email ya existente");
        }
        if (rol == null) {
            rol=Rol.USUARIO;
        }
        Usuario usuario = new Usuario(nombre.trim(), apellido.trim(), mail.trim(), celular.trim(), contrasenia.trim(), rol);
        return usuarioDao.guardar(usuario);
    }

    public Long updateUsuario(Long id, String atributo, String valor)throws StringInvalidException, IdInvalidException, NoEncontradoException {
        Utilidades.validarId(id);
        Utilidades.validarString(atributo);
        Utilidades.validarString(valor);

        Usuario usuario = usuarioDao.buscarPorId(id);
        if(usuario==null){
            throw new EsExistenteException("Usuario con id " + id + " no existe");
        }
        usuario.setUpdatedAt();
        switch (atributo.toLowerCase()){
            case "nombre":
                usuario.setNombre(valor);
                return usuarioDao.actualizar(usuario);
            case "apellido":
                usuario.setApellido(valor);
                return usuarioDao.actualizar(usuario);
            case "mail":
                if(usuarioDao.existeEmail(valor)){
                    throw new NoEncontradoException("Usuario con id " + id + " no existe");
                }
                usuario.setMail(valor);
                return usuarioDao.actualizar(usuario);
            case "celular":
                usuario.setCelular(valor);
                return usuarioDao.actualizar(usuario);
            case "contraseña":
                usuario.setContrasenia(valor);
                return usuarioDao.actualizar(usuario);
            case "rol":
                if(valor.equals("admin")){
                    usuario.setRol(Rol.ADMIN);
                }else{
                    usuario.setRol(Rol.USUARIO);
                }
                return usuarioDao.actualizar(usuario);
            default:
                throw new StringInvalidException(
                        "Error: atributo a modificar inválido"
                );
        }

    }

    public Long deleteUsuario(Long id)throws IdInvalidException, NoEncontradoException {
        Utilidades.validarId(id);
        Usuario usuario= usuarioDao.buscarPorId(id);
        if(usuario==null){
            throw new NoEncontradoException("El usuario con id " + id + " no existe");
        }

        usuario.setDeletedAt();
        usuario.setEliminado(true);
        return usuarioDao.eliminar(usuario);
    }


    public List<Usuario> listarUsuarios() {
        return usuarioDao.listar();
    }

}


