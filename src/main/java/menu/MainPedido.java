package menu;

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
//    public void mostrar() {
//        int opcion = 0;
//        switch (opcion) {
//            case 1 -> listar();
//            case 2 -> listarPorUsuario();
//            case 3 -> crear();
//            case 4 -> actualizar();
//            case 5 -> eliminar();
//            case 0 -> {
//            }
//            default -> System.out.println("Opción inválida.");
//        }
//    }
//
//    private void listar() {
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
//            Long uid = leerLong();
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
//    private void crear() {
//        Pedido pedido = null;
//        try {
//            usuarioService.listar().forEach(System.out::println);
//            System.out.println("ID de usuario: ");
//            Long uid = leerLong();
//
//            System.out.println("Forma de pago: 1=TARJETA  2=TRANSFERENCIA  3=EFECTIVO");
//            System.out.print("Seleccione: ");
//            FormaPago fp = seleccionarFormaPago(leerInt());
//
//            pedido = pedidoService.iniciarPedido(uid, fp);
//
//            boolean agregraMas = true;
//            while (agregraMas) {
//                productoService.listar().forEach(System.out::println);
//                System.out.print("ID de producto (0 para terminar): ");
//                Long pid = leerLong();
//                if (pid == 0) break;
//
//                Producto producto = productoService.buscarPorId(pid);
//                System.out.print("Cantidad: ");
//                int cantidad = leerInt();
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
//    private void actualizar() {
//        try {
//            listar();
//            System.out.print("ID de pedido a actualizar: ");
//            Long id = leerLong();
//
//            System.out.println("Nuevo estado: (0=sin cambio 1=PENDIENTE  2=CONFIRMADO  3=TERMINADO  4=CANCELADO):");
//            Estado estado = seleccionarEstado(leerInt());
//
//            System.out.println("Nueva forma de pago (0=sin cambio 1=TARJETA 2=TRANSFERENCIA 3=EFECTIVO):");
//            FormaPago fp = seleccionarFormaPago(leerInt());
//
//            pedidoService.actualizarEstadoYPago(id, estado, fp);
//            System.out.println("Pedido actualizado.");
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//
//        }
//    }
//
//    private void eliminar() {
//        try {
//            listar();
//            System.out.print("ID de pedido a eliminar: ");
//            Long id = leerLong();
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
//    private int leerInt() {
//        try { return Integer.parseInt(scanner.nextLine().trim()); }
//        catch (NumberFormatException e) { return -1; }
//    }
//
//    private Long leerLong() {
//        try { return Long.parseLong(scanner.nextLine().trim()); }
//        catch (NumberFormatException e) { return -1L; }
//    }
//}


