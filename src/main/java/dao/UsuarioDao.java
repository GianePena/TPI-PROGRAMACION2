package dao;

import entities.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioDao {
    Long save(Usuario usuario);
    void update(Usuario usuario);
    Optional<Usuario> findById(Long id);
    List<Usuario> findAll();
    Optional<Usuario> findByMail(String mail);
    void delete(Long id);
    boolean existsByMail(String mail);
}
