package dao;

import config.HikariConnection;
import entities.Categoria;
import entities.DetallePedido;
import entities.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DetallePedidoImpl implements DetallePedidoDao {

    @Override
    public List<DetallePedido> listar(Long pedidoId) {

        String sql = """
                SELECT dp.*,
                       pr.nombre      AS prod_nombre,
                       pr.precio      AS prod_precio,
                       pr.descripcion AS prod_desc,
                       pr.stock,
                       pr.imagen,
                       pr.disponible,
                       pr.categoria_id,
                       c.nombre       AS cat_nombre,
                       c.descripcion  AS cat_desc
                FROM detalle_pedido dp
                         JOIN productos pr ON dp.producto_id = pr.id
                         JOIN categoria c ON pr.categoria_id = c.id
                WHERE dp.pedido_id = ?
                  AND dp.eliminado = FALSE
                """;

        List<DetallePedido> detalles = new ArrayList<>();

        try (
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setLong(1, pedidoId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    detalles.add(mapearDetalle(rs));
                }
            }

            return detalles;

        } catch (Exception e) {
            throw new RuntimeException("Error al listar detalles del pedido", e);
        }
    }

    @Override
    public Long guardar(DetallePedido detalle, Long pedidoId) {

        String sql = """
            INSERT INTO detalle_pedido (pedido_id, producto_id, cantidad, subtotal)
            VALUES (?, ?, ?, ?)
            """;

        try (
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setLong(1, pedidoId);
            ps.setLong(2, detalle.getProducto().getId());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getSubtotal());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("Error al guardar detalle de pedido", e);
        }
    }
    @Override
    public Long eliminar(Long id) {

        String sql = """
                UPDATE detalle_pedido
                SET eliminado = TRUE
                WHERE id = ?
                """;

        try (
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setLong(1, id);

            int filas = ps.executeUpdate();
            return filas > 0 ? id : null;

        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar detalle de pedido", e);
        }
    }


    private DetallePedido mapearDetalle (ResultSet rs) throws SQLException {

        Categoria categoria = new Categoria(
                rs.getLong("id"),
                rs.getBoolean("eliminado"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getString("nombre"),
                rs.getString("descripcion")
        );

        Producto prod = new Producto(
                rs.getLong("id"),
                rs.getBoolean("eliminado"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getString("nombre"),
                rs.getDouble("precio"),
                rs.getString("descripcion"),
                rs.getInt("stock"),
                rs.getString("imagen"),
                rs.getBoolean("disponible"),
                categoria);

        return new DetallePedido(
                rs.getLong("id"),
                rs.getInt("cantidad"),
                rs.getDouble("subtotal"),
                rs.getBoolean("eliminado"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                prod
        );
    }
}