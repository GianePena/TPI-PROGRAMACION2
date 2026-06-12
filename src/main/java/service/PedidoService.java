package service;

import dao.*;
import entities.DetallePedido;
import entities.Pedido;
import entities.Producto;
import entities.Usuario;
import enums.Estado;
import enums.FormaPago;
import exceptions.NoEncontradoException;


import java.util.List;

public class PedidoService {

    private final PedidoDao pedidoDao= new PedidoDaoImpl();
    private final UsuarioDao usuarioDao=new UsuarioDaoImpl();
    private final DetallePedidoDao detallePedidoDao=new DetallePedidoImpl();


    public Pedido iniciarPedido(Long usuarioId, FormaPago formaPago) throws NoEncontradoException {

        Usuario usuario = usuarioDao.buscarPorId(usuarioId);

        if (usuario == null) {
            throw new NoEncontradoException("Usuario con ID " + usuarioId + " no encontrado o eliminado.");
        }

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

        for (DetallePedido detalle : pedido.getDetalles()) {
            detallePedidoDao.guardar(detalle, pedido.getId());
        }
    }
    public List<Pedido> listar() {
        return pedidoDao.listar();
    }

    public List<Pedido> listarPorUsuario(Long usuarioId) {
        return pedidoDao.buscarPorUsuario(usuarioId);
    }

    public Pedido buscarPorId(Long id) throws NoEncontradoException {

        Pedido pedido = pedidoDao.buscarPorId(id);

        if (pedido == null) {
            throw new NoEncontradoException( "Pedido con ID " + id + " no encontrado o eliminado.");
        }

        return pedido;
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
}