package dao;

import config.HikariConnection;
import entities.Categoria;
import entities.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDaoImpl implements CategoriaDao{
    @Override
    public Long guardar(Categoria c) {
        String sql="INSERT INTO categoria (nombre, descripcion, eliminado, created_at) VALUES( ?, ?, ?, ?)";
        try(
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDescripcion());
            ps.setBoolean(3, c.isEliminado());
            ps.setTimestamp(4, Timestamp.valueOf(c.getCreatedAt()));

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if(rs.next()){
                        return rs.getLong(1);
                    }
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar categoria", e);
        }
    }
    public Categoria buscarPorNombre(String nombre){
        String sql="SELECT * FROM categoria WHERE nombre=? AND eliminado = false";
        try(
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
             ) {
            ps.setString(1, nombre);
            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                   return new Categoria(
                            rs.getLong("id"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                           rs.getTimestamp("created_at").toLocalDateTime()
                    );
                }
            }
            return null;

        }catch (Exception e){
            throw new RuntimeException("Error al buscar categoria", e);
        }
    }
    public void buscarProductoPorCategoria(Categoria categoria){
        String sql="SELECT * FROM productos WHERE categoria=? AND eliminado = false";
        try(
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
        ){
            ps.setString(1, categoria.getNombre());
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){

                    Producto producto = new Producto(
                            rs.getLong("id"),
                            rs.getString("nombre"),
                            rs.getDouble("precio"),
                            rs.getString("descripcion"),
                            rs.getInt("stock"),
                            rs.getString("imagen"),
                            rs.getBoolean("disponible")
                    );
                    categoria.agregarProducto(producto);
                }
            }

        }catch (Exception e){
            throw new RuntimeException("Error al buscar producto por categoría", e);
        }
    }

    @Override
    public List<Categoria> listar() {
        String sql="SELECT * FROM categoria WHERE eliminado<>true AND eliminado = false";
        List<Categoria>catagorias=new ArrayList<>();
        try(
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
        ){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Categoria categoria= new Categoria(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                    );
                buscarProductoPorCategoria(categoria);
                catagorias.add(categoria);
                }
            return catagorias;
        }catch (Exception e){
            throw new RuntimeException("Error al listar categorias", e);
        }
    }


    @Override
    public Categoria buscarPorId(Long id) {
        String sql="SELECT * FROM categoria WHERE id=? AND eliminado = false";
        Categoria categoria;
        try(
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
        ){
            ps.setLong(1,id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return  new Categoria(
                            rs.getLong("id"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    );
                }
            }
            return null;
        }catch (Exception e){
            throw new RuntimeException("Error al buscar categoria", e);
        }
    }



    @Override
    public Long actualizar(Categoria c) {
        String sql="UPDATE categoria SET nombre=?, descripcion=?, updated_at=? WHERE id=? AND eliminado = false";
        try(
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
        ){
            ps.setString(1,c.getNombre());
            ps.setString(2, c.getDescripcion());
            ps.setTimestamp(3, Timestamp.valueOf(c.getUpdatedAt()));
            ps.setLong(4,c.getId());

            int filasAfectadas=ps.executeUpdate();
            if (filasAfectadas>0) {
                return c.getId();
            }
            return null;
        } catch (Exception e){
            throw new RuntimeException("Error al actualizar categoria", e);
        }
    }

    @Override
    public Long eliminar(Categoria c) {
        String sql="UPDATE categoria SET eliminado=?, deleted_at=? WHERE id=?";
        try(
                Connection con = HikariConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
        ){
            ps.setBoolean(1, c.isEliminado());
            ps.setTimestamp(2, Timestamp.valueOf(c.getDeletedAt()));
            ps.setLong(3, c.getId());
            int filasAfectadas=ps.executeUpdate();
            if (filasAfectadas>0) {
                return c.getId();
            }
            return null;
        } catch (Exception e){
            throw new RuntimeException("Error al eliminar categoria", e);
        }
    }
}
