package interfaces;

import entities.DetallePedido;

import java.util.List;

public interface DetallePedidoDao {
    List<DetallePedido> listar(Long pedidoId);
    Long guardar(DetallePedido detalle, Long pedidoId);
    DetallePedido buscarDetalle(Long pedidoId, Long productoId);
    Long eliminarDetalle(Long pedidoId, Long productoId);
}