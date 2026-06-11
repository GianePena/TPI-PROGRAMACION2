package service;

import dao.UsuarioDao;
import dao.UsuarioDaoImpl;
import entities.Usuario;
import enums.Rol;
import exceptions.StringInvalidException;
import exceptions.UsuarioExistenteException;


import java.util.List;


public class UsuarioService {
    private final UsuarioDao usuarioDao;

    public UsuarioService() {
        this.usuarioDao = new UsuarioDaoImpl();
    }

    public UsuarioService(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public Long createUsuario(String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        }
        if (mail == null || mail.trim().isEmpty()) {
            throw new IllegalArgumentException("El mail no puede estar vacío");
        }
        if (celular == null || celular.trim().isEmpty()) {
            throw new IllegalArgumentException("El celular no puede estar vacío");
        }
        if (contrasenia == null || contrasenia.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        if (rol == null) {
            throw new IllegalArgumentException("El rol no puede ser nulo");
        }
      /*  if (usuarioDao.existsByMail(mail)) {
            throw new UsuarioExistenteException(mail);
        }*/

        Usuario usuario = new Usuario(nombre.trim(), apellido.trim(), mail.trim(), celular.trim(), contrasenia.trim(), rol);
        return usuarioDao.guardar(usuario);
    }

   /* public void updateUsuario(Long id, String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol) {
        Optional<Usuario> optionalUsuario = usuarioDao.findById(id);

        if (optionalUsuario.isEmpty()) {
            throw new UsuarioExistenteException("Usuario con id " + id + " no existe");
        }
        String mailOriginal = optionalUsuario.get().getMail();
        if (!mailOriginal.equalsIgnoreCase(mail) && usuarioDao.existsByMail(mail)) {
            throw new UsuarioExistenteException(mail);
        }

        Usuario usuario = optionalUsuario.get();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setCelular(celular);
        usuario.setContrasenia(contrasenia);
        usuario.setRol(rol);
        usuario.setUpdatedAt();

        usuarioDao.update(usuario);
    }*/
   public Long updateUsuario(Long id, String atributo, String valor)throws StringInvalidException {
       Usuario usuario = usuarioDao.buscarPorId(id);
       if(usuario==null){
           throw new UsuarioExistenteException("Usuario con id " + id + " no existe");
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

    public Long deleteUsuario(Long id) {
        //Optional<Usuario> optionalUsuario = usuarioDao.findById(id);
        Usuario usuario= usuarioDao.buscarPorId(id);
        if(usuario==null){
            throw new RuntimeException("El usuario con id " + id + " no existe");
        }

        /*if (optionalUsuario.isEmpty()) {
            throw new RuntimeException("El usuario con id " + id + " no existe");
        }*/
        usuario.setDeletedAt();
        usuario.setEliminado(true);
        return usuarioDao.eliminar(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDao.listar();
    }

   /* public Optional<Usuario> buscarPorId(Long id) {
        return usuarioDao.findById(id);
    }*/
}



