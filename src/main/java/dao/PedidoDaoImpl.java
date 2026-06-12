
package dao;

import config.HikariConnection;
import entities.Categoria;
import entities.Pedido;
import entities.Producto;
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
                (fecha, estado, total, forma_pago, usuario_id, eliminado, created_at)
                VALUES (?, ?, ?, ?, ?, ?, ?)
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
            ps.setBoolean(6, p.isEliminado());
            ps.setTimestamp(7, Timestamp.valueOf(p.getCreatedAt()));

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
            WHERE eliminado= false
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
            WHERE
            usuario_id = ?
              AND eliminado = FALSE
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

    public void buscarDetallePorPedido(Categoria categoria){
        String sql="SELECT * FROM productos WHERE categoria_id=? AND eliminado = 0";
        try(
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ){
            ps.setLong(1, categoria.getId());
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){

                    Producto producto = new Producto(
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
                    categoria.agregarProducto(producto);
                }
            }

        }catch (Exception e){
            throw new RuntimeException("Error al buscar producto por categoría", e);
        }
    }


    @Override
    public Pedido buscarPorId(Long id) {

        String sql = """
            SELECT *
            FROM pedidos
            WHERE id = ?
            AND eliminado = FALSE
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
                total = ?,
                updated_at=?
            WHERE id = ?
            """;

        try (
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, p.getEstado().name());
            ps.setString(2, p.getFormaPago().name());
            ps.setDouble(3, p.getTotal());
            ps.setTimestamp(4, Timestamp.valueOf(p.getUpdatedAt()));
            ps.setLong(5, p.getId());

            int filas = ps.executeUpdate();

            return filas > 0 ? p.getId() : null;

        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar pedido", e);
        }
    }

    @Override
    public Long eliminar(Pedido pedido) {

        String sql = """
                UPDATE pedidos
                SET eliminado=?, deleted_at=?
                WHERE id = ?
                """;

        try (
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setBoolean(1, pedido.isEliminado());
            ps.setTimestamp(2, Timestamp.valueOf(pedido.getDeletedAt()));
            ps.setLong(3, pedido.getId());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                return pedido.getId();
            }
            return null;

        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar pedido", e);
        }
    }
}

