package interfaces;

import entities.Usuario;
import java.util.List;

public interface UsuarioDao {
    Long guardar(Usuario usuario);
    Long actualizar(Usuario usuario);
    Usuario buscarPorId(Long id);
    List<Usuario> listar();
    boolean existeEmail(String mail);
    Long eliminar(Usuario usuario);
}