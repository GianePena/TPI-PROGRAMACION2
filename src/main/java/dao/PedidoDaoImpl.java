package dao;

import config.HikariConnection;
import entities.Categoria;
import entities.Pedido;
import entities.Usuario;
import enums.Estado;
import enums.FormaPago;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDaoImpl implements PedidoDao {

    @Override
    public Long guardar(Pedido p) {

        String sql = """
            INSERT INTO pedidos
            (fecha, estado, total, forma_pago, usuario_id)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {

            ps.setDate(1, Date.valueOf(p.getFecha()));
            ps.setString(2, p.getEstado().name());
            ps.setDouble(3, p.getTotal());
            ps.setString(4, p.getFormaPago().name());
            ps.setLong(5, p.getUsuario().getId());

            int filas = ps.executeUpdate();

            if (filas > 0) {

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
            throw new RuntimeException("Error al guardar pedido", e);
        }
    }

    @Override
    public List<Pedido> listar() {

        String sql = """
            SELECT *
            FROM pedidos
            """;

        List<Pedido> pedidos = new ArrayList<>();

        try (
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Usuario usuario =
                        new UsuarioDaoImpl()
                                .buscarPorId(rs.getLong("usuario_id"));

                Pedido pedido = new Pedido(
                        rs.getLong("id"),
                        rs.getDate("fecha").toLocalDate(),
                        Estado.valueOf(rs.getString("estado")),
                        rs.getDouble("total"),
                        FormaPago.valueOf(rs.getString("forma_pago")),
                        usuario,
                        false,
                        rs.getTimestamp("created_at").toLocalDateTime()
                );

                pedidos.add(pedido);
            }

            return pedidos;

        } catch (Exception e) {
            throw new RuntimeException("Error al listar pedidos", e);
        }
    }

    @Override
    public List<Pedido> buscarPorUsuario(Long usuarioId) {

        String sql = """
            SELECT *
            FROM pedidos
            WHERE usuario_id = ?
            """;

        List<Pedido> pedidos = new ArrayList<>();

        try (
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setLong(1, usuarioId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Usuario usuario =
                            new UsuarioDaoImpl()
                                    .buscarPorId(rs.getLong("usuario_id"));

                    Pedido pedido = new Pedido(
                            rs.getLong("id"),
                            rs.getDate("fecha").toLocalDate(),
                            Estado.valueOf(rs.getString("estado")),
                            rs.getDouble("total"),
                            FormaPago.valueOf(rs.getString("forma_pago")),
                            usuario,
                            false,
                            rs.getTimestamp("created_at").toLocalDateTime()
                    );

                    pedidos.add(pedido);
                }
            }

            return pedidos;

        } catch (Exception e) {
            throw new RuntimeException("Error al buscar pedidos del usuario", e);
        }
    }

    @Override
    public Pedido buscarPorId(Long id) {

        String sql = """
            SELECT *
            FROM pedidos
            WHERE id = ?
            """;

        try (
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Usuario usuario =
                            new UsuarioDaoImpl()
                                    .buscarPorId(rs.getLong("usuario_id"));

                    return new Pedido(
                            rs.getLong("id"),
                            rs.getDate("fecha").toLocalDate(),
                            Estado.valueOf(rs.getString("estado")),
                            rs.getDouble("total"),
                            FormaPago.valueOf(rs.getString("forma_pago")),
                            usuario,
                            false,
                            rs.getTimestamp("created_at").toLocalDateTime()
                    );
                }
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("Error al buscar pedido", e);
        }
    }

    @Override
    public Long actualizar(Pedido p) {

        String sql = """
            UPDATE pedidos
            SET estado = ?,
                forma_pago = ?,
                total = ?
            WHERE id = ?
            """;

        try (
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, p.getEstado().name());
            ps.setString(2, p.getFormaPago().name());
            ps.setDouble(3, p.getTotal());
            ps.setLong(4, p.getId());

            int filas = ps.executeUpdate();

            return filas > 0 ? p.getId() : null;

        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar pedido", e);
        }
    }

    @Override
    public Long eliminar(Long id) {

        String sql = """
                UPDATE pedidos
                SET eliminado = true
                WHERE id = ?
                """;

        try (
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setLong(1, id);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                return id;
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar producto", e);
        }
    }


}
