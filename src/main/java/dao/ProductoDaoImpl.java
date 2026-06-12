package dao;

import config.HikariConnection;
import entities.Categoria;
import entities.Producto;

import java.sql.*;
        import java.util.ArrayList;
import java.util.List;

public class ProductoDaoImpl implements ProductoDao {

    @Override
    public Long guardar(Producto p) {

        String sql = """ 
                INSERT INTO productos
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
            throw new RuntimeException("Error al guardar producto", e);
        }
    }

    @Override
    public List<Producto> listar() {

        String sql = """
                SELECT *
                FROM productos p
                JOIN categorias c
                ON c.id = p.categoria_id
                WHERE p.eliminado = 0
                AND c.deleted_at IS NULL;
                """;

        List<Producto> productos = new ArrayList<>();

        try (
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                System.out.println("Producto encontrado: " +
                        rs.getString("nombre"));

                Categoria categoria =
                        new CategoriaDaoImpl()
                                .buscarPorId(rs.getLong("categoria_id"));

                Producto producto = mapearProducto(rs, categoria);

                productos.add(producto);
            }
            System.out.println("Cantidad productos: " + productos.size());

            return productos;

        } catch (Exception e) {
            throw new RuntimeException("Error al listar productos", e);
        }
    }

    @Override
    public Producto buscarPorNombre(String nombre) {

        String sql = """
                SELECT *
                FROM productos
                WHERE nombre = ?
                AND eliminado = 0
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

                    return mapearProducto(rs,categoria);
                }
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("Error al buscar producto", e);
        }
    }

    @Override
    public Producto buscarPorId(Long id) {

        String sql = """
                SELECT *
                FROM productos
                WHERE id = ?
                AND eliminado = 0
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

                    return mapearProducto(rs, categoria);
                }
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("Error al buscar producto", e);
        }
    }

    @Override
    public Long actualizar(Producto p) {

        String sql = """
                UPDATE productos
                SET nombre = ?,
                    precio = ?,
                    descripcion = ?,
                    stock = ?,
                    imagen = ?,
                    disponible = ?,
                    categoria_id = ?
                WHERE id = ?
                AND eliminado = 0
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
    public Long eliminar(Producto p) {

        String sql = """
                UPDATE productos
                SET eliminado = 1
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
    private Producto mapearProducto(ResultSet rs, Categoria categoria)throws SQLException{
        return new Producto(
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
    }
}