/*package service;

import dao.PedidoDao;
import dao.UsuarioDao;
import entities.Pedido;
import entities.Producto;
import entities.Usuario;
import enums.Estado;
import enums.FormaPago;
import exceptions.CategoriaNoEncontradaException;


import java.util.List;

public class PedidoService {

    private final PedidoDao pedidoDao;
    private final UsuarioDao usuarioDao;

    public PedidoService(PedidoDao pedidoDao, UsuarioDao usuarioDao) {
        this.pedidoDao = pedidoDao;
        this.usuarioDao = usuarioDao;
    }

    public Pedido iniciarPedido(Long usuarioId, FormaPago formaPago) {
        Usuario usuario = usuarioDao.buscarPorId(usuarioId).orElseThrow(() -> new CategoriaNoEncontradaException(
                        "Usuario con ID " + usuarioId + " no encontrado o eliminado."));
        return new Pedido(usuario, formaPago);
    }

    public void agregarDetalle(Pedido pedido, Producto producto, int cantidad) {
        pedido.addDetallePedido(cantidad, producto.getPrecio(), producto);
        pedido.calcularTotal();
    }

    public void guardar(Pedido pedido) {
        if (pedido.getDetalles().isEmpty()) {
            throw new IllegalStateException("El pedido debe tener al menos un detalle.");
        }
        pedidoDao.guardar(pedido);
    }

    public List<Pedido> listar() {
        return pedidoDao.listarTodos();
    }

    public List<Pedido> listarPorUsuario(Long usuarioId) {
        return pedidoDao.buscarPorUsuario(usuarioId);
    }

    public Pedido buscarPorId(Long id) {
        return pedidoDao.buscarPorId(id)
                .orElseThrow(() -> new CategoriaNoEncontradaException(
                        "Pedido con ID " + id + " no encontrado o eliminado."));
    }

    public void actualizarEstadoYPago(Long id, Estado estado, FormaPago formaPago) {
        Pedido pedido = buscarPorId(id);
        if (estado != null) pedido.setEstado(estado);
        if (formaPago != null) pedido.setFormaPago(formaPago);
        pedidoDao.actualizar(pedido);
    }

    public void eliminar(Long id) {
        buscarPorId(id);
        pedidoDao.eliminar(id);
    }
}*/