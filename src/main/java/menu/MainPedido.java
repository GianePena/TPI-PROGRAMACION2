package menu;

import entities.Pedido;
import entities.Producto;
import enums.Estado;
import enums.FormaPago;
import service.PedidoService;
import service.ProductoService;
import service.UsuarioService;
import utilidades.Utilidades;

import java.util.List;
import java.util.Scanner;

public class MainPedido {

    private static final PedidoService pedidoService = new PedidoService();
    private static final ProductoService productoService = new ProductoService();
    private static final UsuarioService usuarioService = new UsuarioService();

    public static void menuPedido(int opcion, Scanner sc) {
        switch (opcion) {
            case 1:
                listar();
                break;

            case 2:
                crear(sc);
                break;

            case 3:
                actualizar(sc);
                break;

            case 4:
                eliminar(sc);
                break;
            case 5:
                actuaizarDetalle(sc);
                break;

            default:
                System.out.println("Opción inválida. Ingrese una opción del 1 al 4.");
        }
    }
    private static void listar() {
        System.out.println("=== TODOS LOS PEDIDOS ===");

        List<Pedido> lista = pedidoService.listar();

        if (lista.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
        } else {
            Utilidades.mostrarLista(lista);
        }
    }

    private static void crear(Scanner sc) {
        Pedido pedido = null;

        try {
            System.out.println("=== CREAR PEDIDO ===");

            System.out.println("Usuarios disponibles: ");


            MainUsuario.listarUsuarios();

            System.out.print("ID de usuario: ");
            Long uid = Utilidades.leerLong(sc);

            System.out.println("Forma de pago:");
            System.out.println("1. TARJETA");
            System.out.println("2. TRANSFERENCIA");
            System.out.println("3. EFECTIVO");
            System.out.print("Seleccione: ");

            FormaPago fp = seleccionarFormaPago(Utilidades.leerInt(sc));

            pedido = pedidoService.iniciarPedido(uid, fp);

            boolean agregarMas = true;

            while (agregarMas) {

                productoService.listarProductos().forEach(System.out::println);

                System.out.print("ID de producto (0 para terminar): ");
                Long pid = Utilidades.leerLong(sc);

                if (pid == 0) {
                    break;
                }

                Producto producto = productoService.buscarPorId(pid);

                System.out.print("Cantidad: ");
                int cantidad = Utilidades.leerInt(sc);

                pedidoService.agregarDetalle(pedido, producto, cantidad);

                System.out.println("Detalle agregado. Total parcial: $" + pedido.getTotal());

                System.out.print("¿Agregar otro producto? (S/N): ");

                String respuesta = Utilidades.leerString(sc);

                agregarMas = respuesta.equalsIgnoreCase("S");
            }

            pedidoService.guardar(pedido);

            System.out.println("Pedido creado. ID: " +
                    pedido.getId() +
                    " | Total: $" +
                    pedido.getTotal());

        } catch (Exception e) {
            System.out.println("Error al crear pedido: " + e.getMessage());
            System.out.println("El pedido fue cancelado.");
        }
    }
    private static void actuaizarDetalle(Scanner sc) {
        try {
            System.out.println("=== GESTIONAR DETALLE DE PEDIDO ===");
            if(pedidoService.listar().isEmpty()){
                System.out.println("No hay pedidos registrados.");
                return;
            }
            listar();

            System.out.print("ID del pedido: ");
            Long pedidoId = Utilidades.leerLong(sc);

            productoService.listarProductos().forEach(System.out::println);

            System.out.print("ID del producto: ");
            Long productoId = Utilidades.leerLong(sc);

            System.out.println("1. BUSCAR");
            System.out.println("2. ELIMINAR");
            System.out.print("Seleccione: ");
            int op = Utilidades.leerInt(sc);

            switch (op) {
                case 1:
                    System.out.println(pedidoService.buscarDetalle(pedidoId, productoId));
                    break;
                case 2:
                    System.out.print("¿Confirma eliminar? (S/N): ");
                    String confirmacion = Utilidades.leerString(sc);
                    if (confirmacion.equalsIgnoreCase("S")) {
                        pedidoService.eliminarDetalle(pedidoId, productoId);
                        System.out.println("Detalle eliminado.");
                    } else {
                        System.out.println("Cancelado.");
                    }
                    break;
                default:
                    System.out.println("Opcion invalida. Ingrese una opcion del 1 al 2.");
                    break;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void actualizar(Scanner sc) {
        try {
            System.out.println("=== ACTUALIZAR PEDIDO ===");

            listar();

            System.out.print("ID del pedido: ");
            Long id = Utilidades.leerLong(sc);

            System.out.println("Nuevo estado:");
            System.out.println("0. Sin cambio");
            System.out.println("1. PENDIENTE");
            System.out.println("2. CONFIRMADO");
            System.out.println("3. TERMINADO");
            System.out.println("4. CANCELADO");

            Estado estado = seleccionarEstado(Utilidades.leerInt(sc));

            System.out.println("Nueva forma de pago:");
            System.out.println("0. Sin cambio");
            System.out.println("1. TARJETA");
            System.out.println("2. TRANSFERENCIA");
            System.out.println("3. EFECTIVO");

            FormaPago fp = seleccionarFormaPago(Utilidades.leerInt(sc));

            pedidoService.actualizarEstadoYPago(id, estado, fp);

            System.out.println("Pedido actualizado.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void eliminar(Scanner sc) {
        try {
            System.out.println("=== ELIMINAR PEDIDO ===");

            listar();

            System.out.print("ID del pedido: ");
            Long id = Utilidades.leerLong(sc);

            System.out.println("¿Seguro quiere eliminar el pedido " + id + "?");
            System.out.print("Ingrese S para confirmar o N para cancelar: ");

            String confirmacion = Utilidades.leerString(sc);

            if (confirmacion.equalsIgnoreCase("N")) {
                System.out.println("Eliminación cancelada.");
                return;
            }

            pedidoService.eliminar(id);

            System.out.println("Pedido eliminado.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static Estado seleccionarEstado(int op) {
        return switch (op) {
            case 1 -> Estado.PENDIENTE;
            case 2 -> Estado.CONFIRMADO;
            case 3 -> Estado.TERMINADO;
            case 4 -> Estado.CANCELADO;
            default -> null;
        };
    }

    private static FormaPago seleccionarFormaPago(int op) {
        return switch (op) {
            case 1 -> FormaPago.TARJETA;
            case 2 -> FormaPago.TRANSFERENCIA;
            case 3 -> FormaPago.EFECTIVO;
            default -> null;
        };
    }
}
