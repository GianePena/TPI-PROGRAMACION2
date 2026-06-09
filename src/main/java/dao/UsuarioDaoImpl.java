package dao;

import config.HikariConnection;
import entities.Usuario;
import enums.Rol;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDaoImpl implements UsuarioDao {

    @Override
    public Long save(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, mail, celular, contrasenia, rol, eliminado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = HikariConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getMail());
            ps.setString(4, usuario.getCelular());
            ps.setString(5, usuario.getContrasenia());
            ps.setString(6, usuario.getRol().name());
            ps.setBoolean(7, usuario.isEliminado());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar usuario: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void update(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, mail = ?, celular = ?, contrasenia = ?, rol = ?, eliminado = ? WHERE id = ?";
        try (Connection conn = HikariConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getMail());
            ps.setString(4, usuario.getCelular());
            ps.setString(5, usuario.getContrasenia());
            ps.setString(6, usuario.getRol().name());
            ps.setBoolean(7, usuario.isEliminado());
            ps.setLong(8, usuario.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection conn = HikariConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearUsuario(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario por ID: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Usuario> findAll() {
        String sql = "SELECT * FROM usuarios WHERE eliminado = FALSE";
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = HikariConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar usuarios: " + e.getMessage(), e);
        }
        return usuarios;
    }

    @Override
    public Optional<Usuario> findByMail(String mail) {
        String sql = "SELECT * FROM usuarios WHERE mail = ?";
        try (Connection conn = HikariConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, mail);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearUsuario(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario por mail: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        String sql = "UPDATE usuarios SET eliminado = TRUE WHERE id = ?";
        try (Connection conn = HikariConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsByMail(String mail) {
        return findByMail(mail).isPresent();
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {

        String nombre = rs.getString("nombre");
        String apellido = rs.getString("apellido");
        String mail = rs.getString("mail");
        String celular = rs.getString("celular");
        String contrasenia = rs.getString("contrasenia");
        Rol rol = Rol.valueOf(rs.getString("rol"));

        Usuario usuario = new Usuario(nombre, apellido, mail, celular, contrasenia, rol);

        usuario.setId(rs.getLong("id"));
        usuario.setEliminado(rs.getBoolean("eliminado"));

        Timestamp timestamp = rs.getTimestamp("created_at");
        if (timestamp != null) {
            usuario.setCreatedAt(timestamp.toLocalDateTime());
        }

        return usuario;
    }
}