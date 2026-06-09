package service;

import dao.UsuarioDao;
import dao.UsuarioDaoImpl;
import entities.Usuario;
import enums.Rol;
import exceptions.UsuarioExistenteException;

import java.util.List;
import java.util.Optional;

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
        if (usuarioDao.existsByMail(mail)) {
            throw new UsuarioExistenteException(mail);
        }

        Usuario usuario = new Usuario(nombre.trim(), apellido.trim(), mail.trim(), celular.trim(), contrasenia.trim(), rol);
        return usuarioDao.save(usuario);
    }

    public void updateUsuario(Long id, String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol) {
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

        usuarioDao.update(usuario);
    }

    public void deleteUsuario(Long id) {
        Optional<Usuario> optionalUsuario = usuarioDao.findById(id);

        if (optionalUsuario.isEmpty()) {
            throw new RuntimeException("El usuario con id " + id + " no existe");
        }
        usuarioDao.delete(id);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDao.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioDao.findById(id);
    }
}



