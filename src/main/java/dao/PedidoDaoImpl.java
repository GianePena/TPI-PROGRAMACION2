package dao;

import config.HikariConnection;
import entities.Categoria;
import entities.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDaoImpl implements PedidoDao {

    @Override
    public Long guardar(Pedido p) {

        String sql = """ 
                INSERT INTO producto
                (nombre, precio, descripcion, stock, imagen,
                 disponible, categoria_id, eliminado, created_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {

            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setString(3, p.getDescripcion());
            ps.setInt(4, p.getStock());
            ps.setString(5, p.getImagen());
            ps.setBoolean(6, p.isDisponible());
            ps.setLong(7, p.getCategoria().getId());
            ps.setBoolean(8, p.isEliminado());
            ps.setTimestamp(9, Timestamp.valueOf(p.getCreatedAt()));

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {

                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next()) {

                        Long idGenerado = rs.getLong(1);
                        p.setId(idGenerado);

                        return idGenerado;
                    }
                }
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el pedido", e);
        }
    }

    @Override
    public List<Pedido> listar() {

        String sql = """
                SELECT *
                FROM pedidos
                WHERE eliminado = false
                """;

        List<Pedido> pedidos = new ArrayList<>();

        try (
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Categoria categoria =
                        new CategoriaDaoImpl()
                                .buscarPorId(rs.getLong("categoria_id"));

                Pedido pedido = new Pedido(
                        rs.getLong("id"),
                        rs.getBoolean("eliminado"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getString("descripcion"),
                        rs.getInt("stock"),
                        rs.getString("imagen"),
                        rs.getBoolean("disponible"),
                        categoria
                );

                pedidos.add(pedido);
            }

            return pedidos;

        } catch (Exception e) {
            throw new RuntimeException("Error al listar productos", e);
        }
    }

    @Override
    public Pedido buscarPorNombre(String nombre) {

        String sql = """
                SELECT *
                FROM producto
                WHERE nombre = ?
                AND eliminado = false
                """;

        try (
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Categoria categoria =
                            new CategoriaDaoImpl()
                                    .buscarPorId(rs.getLong("categoria_id"));

                    return new Pedido(
                            rs.getLong("id"),
                            rs.getBoolean("eliminado"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getString("nombre"),
                            rs.getDouble("precio"),
                            rs.getString("descripcion"),
                            rs.getInt("stock"),
                            rs.getString("imagen"),
                            rs.getBoolean("disponible"),
                            categoria
                    );
                }
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el pedido", e);
        }
    }

    @Override
    public Pedido buscarPorId(Long id) {

        String sql = """
                SELECT *
                FROM pedido
                WHERE id = ?
                AND eliminado = false
                """;

        try (
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Categoria categoria =
                            new CategoriaDaoImpl()
                                    .buscarPorId(rs.getLong("categoria_id"));

                    return new Pedido(
                            rs.getLong("id"),
                            rs.getBoolean("eliminado"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getString("nombre"),
                            rs.getDouble("precio"),
                            rs.getString("descripcion"),
                            rs.getInt("stock"),
                            rs.getString("imagen"),
                            rs.getBoolean("disponible"),
                            categoria
                    );
                }
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("Error al buscar producto", e);
        }
    }

    @Override
    public Long actualizar(Pedido p) {

        String sql = """
                UPDATE producto
                SET nombre = ?,
                    precio = ?,
                    descripcion = ?,
                    stock = ?,
                    imagen = ?,
                    disponible = ?,
                    categoria_id = ?
                WHERE id = ?
                AND eliminado = false
                """;

        try (
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setString(3, p.getDescripcion());
            ps.setInt(4, p.getStock());
            ps.setString(5, p.getImagen());
            ps.setBoolean(6, p.isDisponible());
            ps.setLong(7, p.getCategoria().getId());
            ps.setLong(8, p.getId());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                return p.getId();
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar producto", e);
        }
    }

    @Override
    public Long eliminar(Pedido p) {

        String sql = """
                UPDATE pedido
                SET eliminado = true
                WHERE id = ?
                """;

        try (
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setLong(1, p.getId());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                return p.getId();
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar producto", e);
        }
    }


}
