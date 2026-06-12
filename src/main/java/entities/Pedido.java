package entities;


import enums.Estado;
import enums.FormaPago;
import interfaces.Calculable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido extends Base implements Calculable {

    private LocalDate fecha;
    private Estado estado;
    private double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private List<DetallePedido> detalles;

    public Pedido() {
        super();
        this.detalles = new ArrayList<>();
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
    }

    public Pedido(Usuario usuario, FormaPago formaPago) {
        super();
        this.usuario = usuario;
        this.formaPago = formaPago;
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.detalles = new ArrayList<>();
        this.total = 0.0;
    }

    public Pedido(Long id, LocalDate fecha, Estado estado, double total,  FormaPago formaPago, Usuario usuario,  boolean eliminado, LocalDateTime createdAt) {
        super(id, eliminado, createdAt);
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.detalles = new ArrayList<>();
    }

    public void addDetallePedido(int cantidad, double precioUnitario, Producto producto) {
        double subtotal = cantidad * precioUnitario;
        DetallePedido detalle = new DetallePedido(cantidad, subtotal, producto);
        this.detalles.add(detalle);
    }

    public DetallePedido findeDetallePedidoByProducto(Producto producto) {
        return detalles.stream().filter(d -> d.getProducto() != null && d.getProducto().equals(producto)).findFirst().orElse(null);
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        detalles.removeIf(d -> d.getProducto() != null && d.getProducto().equals(producto));
    }


    @Override
    public double calcularTotal() {
        this.total = detalles.stream()
                .mapToDouble(DetallePedido::getSubtotal)
                .sum();
        return this.total;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> d) {
        this.detalles = d;
    }


    @Override
    public String toString() {
        String detalleStr="";
        for(DetallePedido d:detalles){
            detalleStr=d.toString()+'\n';
        }
        return "\nPEDIDO: #id " +this.getId()+
                "\n| Fecha: " + fecha +
                "\n| Usuario: " + usuario.getNombre()+ " " +usuario.getApellido()+
                "\n| Email: " + usuario.getMail()+
                "\n| Detalles: " + (detalleStr.isEmpty()?"Agregar productos a la compra": detalleStr)+
                "\n|Forma de Pago: " + formaPago +
                "\n| Total: " + total ;
    }
}


