
package service;

import dao.*;
import entities.DetallePedido;
import entities.Pedido;
import entities.Producto;
import entities.Usuario;
import enums.Estado;
import enums.FormaPago;
import exceptions.NoEncontradoException;
import interfaces.DetallePedidoDao;
import interfaces.PedidoDao;
import interfaces.UsuarioDao;


import java.util.List;
public class PedidoService {

    private final PedidoDao pedidoDao = new PedidoDaoImpl();
    private final UsuarioDao usuarioDao = new UsuarioDaoImpl();
    private final DetallePedidoDao detallePedidoDao = new DetallePedidoImpl();

    public List<Pedido> listarPedidos() {
        List<Pedido> pedidos = pedidoDao.listar();
        for (Pedido p : pedidos) {
            p.setDetalles(detallePedidoDao.listar(p.getId()));
        }
        return pedidos;
    }

    public List<Pedido> listarPedidoPorIdUsuario(Long usuarioId) {
        List<Pedido> pedidos = pedidoDao.buscarPorUsuario(usuarioId);
        for (Pedido p : pedidos) {
            p.setDetalles(detallePedidoDao.listar(p.getId()));
        }
        return pedidos;
    }

    public Pedido agregarPedido(Long usuarioId, FormaPago formaPago) throws NoEncontradoException {
        Usuario usuario = usuarioDao.buscarPorId(usuarioId);
        if (usuario == null) {
            throw new NoEncontradoException("Usuario con ID " + usuarioId + " no encontrado o eliminado.");
        }
        return new Pedido(usuario, formaPago);
    }

    public void guardarPedido(Pedido pedido) {
        if (pedido.getDetalles().isEmpty()) {
            throw new IllegalStateException("El pedido debe tener al menos un detalle.");
        }
        pedidoDao.guardar(pedido);
        for (DetallePedido detalle : pedido.getDetalles()) {
            detallePedidoDao.guardar(detalle, pedido.getId());
        }
    }

    public void eliminarPedidoPorId(Long id) throws NoEncontradoException {
        Pedido pedido= pedidoDao.buscarPorId(id);
        if(pedido==null){
            throw new NoEncontradoException("Error pedido no encontrada");
        }
        pedido.setDeletedAt();
        pedido.setEliminado(true);
        pedidoDao.eliminar(pedido);
    }

    public void actualizarEstadoYPagoPorId(Long id, Estado estado, FormaPago formaPago) throws NoEncontradoException {
        Pedido pedido = buscarPedidoPorId(id);
        if (estado != null) pedido.setEstado(estado);
        if (formaPago != null) pedido.setFormaPago(formaPago);
        pedido.setUpdatedAt();
        pedidoDao.actualizar(pedido);
    }

    public Pedido buscarPedidoPorId(Long id) throws NoEncontradoException {
        Pedido pedido = pedidoDao.buscarPorId(id);
        if (pedido == null) {
            throw new NoEncontradoException("Pedido con ID " + id + " no encontrado o eliminado.");
        }
        pedido.setDetalles(detallePedidoDao.listar(pedido.getId()));
        return pedido;
    }

    /* FUNCIONES DE DETALLES */

    public void agregarDetalle(Pedido pedido, Producto producto, int cantidad) {
        pedido.addDetallePedido(cantidad, producto.getPrecio(), producto);
        pedido.calcularTotal();
    }

    public DetallePedido buscarDetallePorId(Long pedidoId, Long productoId) throws NoEncontradoException {
        DetallePedido detalle = detallePedidoDao.buscarDetalle(pedidoId, productoId);
        if (detalle == null) {
            throw new NoEncontradoException("Detalle no encontrado para pedido " + pedidoId + " y producto " + productoId);
        }
        return detalle;
    }

    public void eliminarDetallePorId(Long pedidoId, Long productoId) throws NoEncontradoException {
        buscarDetallePorId(pedidoId, productoId);

        detallePedidoDao.eliminarDetalle(pedidoId, productoId);

        Pedido pedido = buscarPedidoPorId(pedidoId);
        pedido.calcularTotal();
        pedido.setUpdatedAt();
        pedidoDao.actualizar(pedido);
    }

}
