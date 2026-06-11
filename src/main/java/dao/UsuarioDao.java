package dao;

import entities.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioDao {
    Long guardar(Usuario usuario);
   // void update(Usuario usuario);
    Long actualizar(Usuario usuario);
    //Optional<Usuario> findById(Long id);
    Usuario buscarPorId(Long id);
    List<Usuario> listar();
    //Optional<Usuario> findByMail(String mail);
    Long eliminar(Usuario usuario);
    //boolean existsByMail(String mail);
}
