package dao;

import config.HikariConnection;
import entities.Categoria;
import entities.DetallePedido;
import entities.Producto;
import interfaces.DetallePedidoDao;

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
                         JOIN categorias c ON pr.categoria_id = c.id
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
            throw new RuntimeException("Error al listarPedidos detalles del pedido", e);
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
            throw new RuntimeException("Error al guardarPedido detalle de pedido", e);
        }
    }
    @Override
    public DetallePedido buscarDetalle(Long pedidoId, Long productoId) {

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
                     JOIN categorias c ON pr.categoria_id = c.id
            WHERE dp.pedido_id = ?
              AND dp.producto_id = ?
              AND dp.eliminado = FALSE
            """;

        try (
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setLong(1, pedidoId);
            ps.setLong(2, productoId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearDetalle(rs);
                }
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("Error al buscar detalle", e);
        }
    }

    @Override
    public Long eliminarDetalle(Long pedidoId, Long productoId) {

        String sql = """
            UPDATE detalle_pedido
            SET eliminado = TRUE
            WHERE pedido_id = ?
              AND producto_id = ?
            """;

        try (
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setLong(1, pedidoId);
            ps.setLong(2, productoId);

            int filas = ps.executeUpdate();
            return filas > 0 ? productoId : null;

        } catch (Exception e) {
            throw new RuntimeException("Error al eliminarPedidoPorId detalle", e);
        }
    }
    private DetallePedido mapearDetalle(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria(
                rs.getLong("categoria_id"),
                false,
                null,
                rs.getString("cat_nombre"),
                rs.getString("cat_desc")
        );

        Producto prod = new Producto(
                rs.getLong("producto_id"),
                false,
                null,
                rs.getString("prod_nombre"),
                rs.getDouble("prod_precio"),
                rs.getString("prod_desc"),
                rs.getInt("stock"),
                rs.getString("imagen"),
                rs.getBoolean("disponible"),
                categoria
        );

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