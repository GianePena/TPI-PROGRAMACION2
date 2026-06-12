
//TODO AGREGAR EN PRODUCTOSERVICE la funcion public Categoria buscarCategoriaPorId(Long id)
//o directamente usar un CategoriaService.buscarPedidoPorId(id).
// Porque ahora en crearProducto() estoy recorriendo la lista de categorías para encontrarla
// Lo ideal es que el CategoriaService tenga un método para buscar una categoría por ID y usarlo directamente.

package service;

import interfaces.ProductoDao;
import dao.ProductoDaoImpl;
import entities.Categoria;
import entities.Producto;
import exceptions.EsExistenteException;
import exceptions.IdInvalidException;
import exceptions.NoEncontradoException;
import exceptions.StringInvalidException;
import utilidades.Utilidades;

import java.util.List;

public class ProductoService {

    private ProductoDao dao = new ProductoDaoImpl();

    public Producto buscarProductoPorId(Long id)throws IdInvalidException{
        Utilidades.validarId(id);
        return dao.buscarPorId(id);
    }

    public List<Producto> listarProductos() {
        return dao.listar();
    }

    public Long agregarProducto(String nombre, double precio, String descripcion,
                                int stock, String imagen, Categoria categoria) throws EsExistenteException, StringInvalidException {
        Utilidades.validarString(nombre);
        if (precio < 0) {throw new IllegalArgumentException("Error: el precio no puede ser negativo");}
        if (stock < 0) {throw new IllegalArgumentException("Error: el stock no puede ser negativo");}
        if (categoria == null) {throw new IllegalArgumentException("Error: debe seleccionar una categoría");}

        Producto existente = dao.buscarPorNombre(nombre);
        if (existente != null) {throw new EsExistenteException("Error: ya existe un producto con nombre " + nombre);}

        Producto producto = new Producto(
                nombre,
                precio,
                descripcion,
                stock,
                imagen,
                categoria
        );
        return dao.guardar(producto);
    }

    public Long actualizarProducto(Long id, String atributo, String valor) throws NoEncontradoException, StringInvalidException, IdInvalidException {
        Utilidades.validarId(id);
        Utilidades.validarString(atributo);
        Utilidades.validarString(valor);
        Producto producto = dao.buscarPorId(id);
        if (producto == null) {throw new NoEncontradoException("Error: producto no encontrado");}
        producto.setUpdatedAt();

        switch (atributo.toLowerCase()) {
            case "nombre":
                if (valor.isBlank()) {throw new StringInvalidException("Error: nombre inválido");}
                producto.setNombre(valor);
                return dao.actualizar(producto);
            case "descripcion":
                producto.setDescripcion(valor);
                return dao.actualizar(producto);
            case "imagen":
                producto.setImagen(valor);
                return dao.actualizar(producto);
            case "precio":
                double precio = Double.parseDouble(valor);
                if (precio < 0) { throw new IllegalArgumentException("Error: el precio no puede ser negativo");}
                producto.setPrecio(precio);
                return dao.actualizar(producto);
            case "stock":
                int stock = Integer.parseInt(valor);
                if (stock < 0) {throw new IllegalArgumentException("Error: el stock no puede ser negativo");}
                producto.setStock(stock);
                return dao.actualizar(producto);

            case "categoria":
                Long id_categoria = Long.parseLong(valor);
                CategoriaService categoriaService = new CategoriaService();

                producto.setCategoria(categoriaService.buscarCategoriaPorId(id_categoria));
                return dao.actualizar(producto);

            default: throw new StringInvalidException("Error: atributo a modificar inválido");
        }
    }

    public Long eliminarProducto(Long id) throws NoEncontradoException, IdInvalidException {
        Utilidades.validarId(id);
        Producto producto = dao.buscarPorId(id);

        if (producto == null) {
            throw new NoEncontradoException(
                    "Error: producto no encontrado"
            );
        }

        producto.setEliminado(true);
        producto.setDeletedAt();

        return dao.eliminar(producto);
    }

    public Producto buscarPorId(Long id)throws IdInvalidException{
        Utilidades.validarId(id);
        return dao.buscarPorId(id);
    }

}