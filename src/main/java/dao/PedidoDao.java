package dao;

import entities.Pedido;
import entities.Producto;

import java.util.List;

public interface PedidoDao {
    List<Pedido> listar();
    Pedido buscarPorId(Long id);
    List<Pedido> buscarPorUsuario(Long usuarioId);
    Long guardar(Pedido pedido);
    Long actualizar(Pedido pedido);
    Long eliminar(Long id);
}

