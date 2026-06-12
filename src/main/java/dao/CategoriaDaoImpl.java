package dao;

import config.HikariConnection;
import entities.Categoria;
import entities.Producto;
import interfaces.CategoriaDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDaoImpl implements CategoriaDao {
    @Override
    public Long guardar(Categoria categoria) {
        String sql="INSERT INTO categorias (nombre, descripcion, eliminado, created_at) VALUES( ?, ?, ?, ?)";
        try(
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setBoolean(3, categoria.isEliminado());
            ps.setTimestamp(4, Timestamp.valueOf(categoria.getCreatedAt()));

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        Long idGenerado = rs.getLong(1);
                        categoria.setId(idGenerado);
                        return idGenerado;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error al guardarPedido categoria", e);
        }
    }
    public Categoria buscarPorNombre(String nombre){
        String sql="SELECT * FROM categorias WHERE nombre=? AND eliminado = false";
        try(
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, nombre);
            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                    return mapearCategoria(rs);
                }
            }
            return null;

        }catch (Exception e){
            throw new RuntimeException("Error al buscar categoria", e);
        }
    }

    public void buscarProductoPorCategoria(Categoria categoria) {
        String sql = "SELECT * FROM productos WHERE categoria_id=? AND eliminado = 0";
        try (
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setLong(1, categoria.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

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

        } catch (Exception e) {
            throw new RuntimeException("Error al buscar producto por categoría", e);
        }
    }

    @Override
    public List<Categoria> listar() {
        String sql="SELECT * FROM categorias WHERE eliminado = false";
        List<Categoria>catagorias=new ArrayList<>();
        try(
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Categoria categoria=mapearCategoria(rs);
                buscarProductoPorCategoria(categoria);
                catagorias.add(categoria);
            }
            return catagorias;
        }catch (Exception e){
            throw new RuntimeException("Error al listarPedidos categorias", e);
        }
    }


    @Override
    public Categoria buscarPorId(Long id) {
        String sql="SELECT * FROM categorias WHERE id=? AND eliminado = false";
        Categoria categoria;
        try(
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ){
            ps.setLong(1,id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return mapearCategoria(rs);
                }
            }
            return null;
        }catch (Exception e){
            throw new RuntimeException("Error al buscar categoria", e);
        }
    }



    @Override
    public Long actualizar(Categoria categoria) {
        String sql="UPDATE categorias SET nombre=?, descripcion=?, updated_at=? WHERE id=? AND eliminado = false";
        try(
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ){
            ps.setString(1,categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setTimestamp(3, Timestamp.valueOf(categoria.getUpdatedAt()));
            ps.setLong(4,categoria.getId());

            int filasAfectadas=ps.executeUpdate();
            if (filasAfectadas>0) {
                return categoria.getId();
            }
            return null;
        } catch (Exception e){
            throw new RuntimeException("Error al actualizar categoria", e);
        }
    }

    @Override
    public Long eliminar(Categoria categoria) {
        String sql="UPDATE categorias SET eliminado=?, deleted_at=? WHERE id=?";
        try(
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ){
            ps.setBoolean(1, categoria.isEliminado());
            ps.setTimestamp(2, Timestamp.valueOf(categoria.getDeletedAt()));
            ps.setLong(3, categoria.getId());
            int filasAfectadas=ps.executeUpdate();
            if (filasAfectadas>0) {
                return categoria.getId();
            }
            return null;
        } catch (Exception e){
            throw new RuntimeException("Error al eliminarPedidoPorId categoria", e);
        }
    }

    private Categoria mapearCategoria(ResultSet rs) throws SQLException{
        return  new Categoria(
                rs.getLong("id"),
                rs.getBoolean("eliminado"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getString("nombre"),
                rs.getString("descripcion")
        );
    }
}