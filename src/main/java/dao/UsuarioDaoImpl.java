package dao;

import config.HikariConnection;
import entities.Usuario;
import enums.Rol;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import interfaces.UsuarioDao;
import utilidades.Utilidades;

public class UsuarioDaoImpl implements UsuarioDao {

    @Override
    public Long guardar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, mail, celular, contrasenia, rol, eliminado, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = HikariConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getMail());
            ps.setString(4, usuario.getCelular());
            ps.setString(5, usuario.getContrasenia());
            ps.setString(6, usuario.getRol().name());
            ps.setBoolean(7, usuario.isEliminado());
            ps.setTimestamp(8, Timestamp.valueOf(usuario.getCreatedAt()));

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    Long id= rs.getLong(1);
                    usuario.setId(id);
                    return id;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardarPedido usuario: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Long actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, mail = ?, celular = ?, contrasenia = ?, rol = ?, eliminado = ?, updated_at = ? WHERE id = ?";
        try (Connection conn = HikariConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getMail());
            ps.setString(4, usuario.getCelular());
            ps.setString(5, usuario.getContrasenia());
            ps.setString(6, usuario.getRol().name());
            ps.setBoolean(7, usuario.isEliminado());
            ps.setTimestamp(8, Timestamp.valueOf(usuario.getUpdatedAt()));
            ps.setLong(9, usuario.getId());

            ps.executeUpdate();
            int filasAfectadas=ps.executeUpdate();
            if (filasAfectadas>0) {
                return usuario.getId();
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario buscarPorId(Long id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection conn = HikariConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario por ID: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Usuario> listar() {
        String sql = "SELECT * FROM usuarios WHERE eliminado = 0";
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = HikariConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listarPedidos usuarios: " + e.getMessage(), e);
        }
        return usuarios;
    }

    @Override
    public boolean existeEmail(String mail) {
        String sql = "SELECT * FROM usuarios WHERE mail = ?";
        try (Connection conn = HikariConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mail);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario por mail: " + e.getMessage(), e);
        }
        return false;
    }


    @Override
    public Long eliminar(Usuario usuario) {
        String sql = "UPDATE usuarios SET eliminado =TRUE, deleted_at=? WHERE id = ?";
        try (Connection conn = HikariConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(Utilidades.generarFecha()));
            ps.setLong(2, usuario.getId());

            int filasAfectadas=ps.executeUpdate();
            if (filasAfectadas>0) {
                return usuario.getId();
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminarPedidoPorId usuario: " + e.getMessage(), e);
        }
    }


    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getLong("id"),
                rs.getBoolean("eliminado"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("mail"),
                rs.getString("celular"),
                rs.getString("contrasenia"),
                Rol.valueOf(rs.getString("rol"))
        );
    }
}