package entities;

import java.time.LocalDateTime;

public class DetallePedido extends Base{
   private int cantidad;
   private double subtotal;
   private Producto producto;

   public DetallePedido() {
       super();
   }

   public DetallePedido(int cantidad, double subtotal, Producto producto) {
       super();
       this.cantidad = cantidad;
       this.subtotal = subtotal;
       this.producto = producto;
   }

   public DetallePedido(Long id, int cantidad, double subtotal, boolean eliminado, LocalDateTime createdAt, Producto producto) {
       super(id, eliminado, createdAt);
       this.cantidad = cantidad;
       this.subtotal = subtotal;
       this.producto = producto;
   }

    public int getCantidad() {return cantidad;}
    public void setCantidad(int cantidad) {this.cantidad = cantidad;}

    public double getSubtotal() {return subtotal;}
    public void setSubtotal(double subtotal) {this.subtotal = subtotal;}

    public Producto getProducto() {return producto;}
    public void setProducto(Producto producto) {this.producto = producto;}

    @Override
    public String toString() {
        return "| DetallePedido: " +
                "\n| Cantidad: " + cantidad +
                "\n| Subtotal: " + subtotal +
                "\n| Producto: " + producto;
    }
}
