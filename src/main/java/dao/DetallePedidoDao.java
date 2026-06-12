package dao;

import entities.DetallePedido;

import java.util.List;

public interface DetallePedidoDao {
    List<DetallePedido> listar(Long pedidoId);
    Long guardar(DetallePedido detalle, Long pedidoId);
    Long eliminar(Long id);
}