/*package menu;

import entities.Pedido;
import entities.Usuario;
import enums.Estado;
import enums.FormaPago;
//import service.PedidoService;
import entities.Producto;
import service.ProductoService;

import java.util.List;
import java.util.Scanner;

import static utilidades.Utilidades.leerInt;
import static utilidades.Utilidades.leerLong;
//
//public class MainPedido {
//    private final PedidoService pedidoService;
//    private final ProductoService productoService;
//    private final UsuarioService usuarioService;
//    private final Scanner scanner;
//
//    public MainPedido(PedidoService pedidoService, ProductoService productoService,
//                      UsuarioService usuarioService, Scanner scanner) {
//        this.pedidoService = pedidoService;
//        this.productoService = productoService;
//        this.usuarioService = usuarioService;
//        this.scanner = scanner;
//    }
//
 public static void menuPedido(int opcion, Scanner sc) {
        switch (opcion) {
            case 1:
                listarPedidos();
                break;

            case 2:
                listarPorUsuario(sc);
                break;

            case 3:
                crearPedido(sc);
                break;

            case 4:
                 actualizarPedido(sc);
                break;
            case 5:
                 eliminarPedido(sc);
                break;

            default:
                System.out.println("Opcion invalida. Ingrese una opcion del 1 al 4.");
        }
    }
//    private void listarPedidos() {
//        List<Pedido> lista = pedidoService.listar();
//        if (lista.isEmpty()) {
//            System.out.println("No hay pedido registrados.");
//        } else {
//            for (Pedido p : lista) {
//                System.out.println(p);
//                p.getDetalles().stream().filter(d -> !d.isEliminado()).forEach(System.out::println);
//            }
//        }
//    }
//
//    private void listarPorUsuario() {
//        try {
//            usuarioService.listar().forEach(System.out::println);
//            System.out.println("ID de usuario: ");
//            Long uid = Utilidades.leerLong();
//            List<Pedido> lista = pedidoService.listarPorUsuario(uid);
//            if(lista.isEmpty()) System.out.println("No hay usuario para ese pedido.");
//            else lista.forEach(p -> {
//                System.out.println(p);
//                p.getDetalles().stream().filter(d -> !d.isEliminado()).forEach(System.out::println);
//            });
//        }catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//    }
//
//    private void crearPedido() {
//        Pedido pedido = null;
//        try {
//            usuarioService.listar().forEach(System.out::println);
//            System.out.println("ID de usuario: ");
//            Long uid = Utilidades.leerLong();
//
//            System.out.println("Forma de pago: 1=TARJETA  2=TRANSFERENCIA  3=EFECTIVO");
//            System.out.print("Seleccione: ");
//            FormaPago fp = seleccionarFormaPago(Utilidades.leerInt());
//
//            pedido = pedidoService.iniciarPedido(uid, fp);
//
//            boolean agregraMas = true;
//            while (agregraMas) {
//                productoService.listar().forEach(System.out::println);
//                System.out.print("ID de producto (0 para terminar): ");
//                Long pid = Utilidades.leerLong();
//                if (pid == 0) break;
//
//                Producto producto = productoService.buscarPorId(pid);
//                System.out.print("Cantidad: ");
//                int cantidad = Utilidades.leerInt();
//                pedidoService.agregarDetalle(pedido, producto, cantidad);
//                System.out.println("Detalle agregado. Subtotal parcial: $\"" + pedido.getTotal());
//
//                System.out.print("¿Agregar otro producto? (S/N): ");
//                agregraMas = scanner.nextLine().trim().equalsIgnoreCase("S");
//            }
//
//            pedidoService.guardar(pedido);
//            System.out.println("Pedido creado. ID: " + pedido.gerId() + " | total: $" + pedido.getTotal());
//        } catch (Exception e) {
//            System.out.println("Error al crear pedido: " + e.getMessage());
//            System.out.println("El pedido fue cancelado y no fue guardado.");
//        }
//    }
//
//    private void actualizarPedido() {
//        try {
//            listar();
//            System.out.print("ID de pedido a actualizar: ");
//            Long id = Utilidades.leerLong();
//
//            System.out.println("Nuevo estado: (0=sin cambio 1=PENDIENTE  2=CONFIRMADO  3=TERMINADO  4=CANCELADO):");
//            Estado estado = seleccionarEstado(Utilidades.leerInt());
//
//            System.out.println("Nueva forma de pago (0=sin cambio 1=TARJETA 2=TRANSFERENCIA 3=EFECTIVO):");
//            FormaPago fp = seleccionarFormaPago(Utilidades.leerInt());
//
//            pedidoService.actualizarEstadoYPago(id, estado, fp);
//            System.out.println("Pedido actualizado.");
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//
//        }
//    }
//
//    private void eliminarPedido() {
//        try {
//            listar();
//            System.out.print("ID de pedido a eliminar: ");
//            Long id = Utilidades.leerLong();
//            System.out.print("¿Confirmar eliminación? (S/N): ");
//            if (scanner.nextLine().trim().equalsIgnoreCase("S")) {
//                pedidoService.eliminar(id);
//                System.out.println("✔ Pedido eliminado.");
//            } else {
//                System.out.println("Operación cancelada.");
//            }
//        } catch (Exception e) {
//            System.out.println("✖ Error: " + e.getMessage());
//        }
//    }
//
//    private Estado seleccionarEstado(int op) {
//        return switch (op) {
//            case 1 -> Estado.PENDIENTE;
//            case 2 -> Estado.CONFIRMADO;
//            case 3 -> Estado.TERMINADO;
//            case 4 -> Estado.CANCELADO;
//            default -> null;
//        };
//    }
//
//    private FormaPago seleccionarFormaPago(int op) {
//        return switch (op) {
//            case 1 -> FormaPago.TARJETA;
//            case 2 -> FormaPago.TRANSFERENCIA;
//            case 3 -> FormaPago.EFECTIVO;
//            default -> null;
//        };
//    }
//

//}

*/
