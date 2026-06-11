package dao;

import entities.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioDao {
    Long guardar(Usuario usuario);
    Long actualizar(Usuario usuario);
    Usuario buscarPorId(Long id);
    List<Usuario> listar();
    boolean existeEmail(String mail);
    Long eliminar(Usuario usuario);
}
