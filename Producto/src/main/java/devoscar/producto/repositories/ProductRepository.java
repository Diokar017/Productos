package productos.repositories;

import productos.products.Producto;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductRepository implements CrudRepository {

    private final Map<Long, Producto> productos;
    private Long nextId;

    public ProductRepository() {
        this.productos = new LinkedHashMap<>();
        productos.put(1L, new Producto(1L, "Gambas al ajillo", 10.50, true));
        productos.put(2L, new Producto(2L, "Arroz con Bogavante", 22.50, false));
        productos.put(3L, new Producto(3L, "Hamburguesa con patatas", 14.00, true));
        productos.put(4L, new Producto(4L, "Croquetas de Jamón", 8.00, true));
        productos.put(5L, new Producto(5L, "Sangria 1 Litro", 2.50, false));
        productos.put(6L, new Producto(6L, "Tarta de Queso casera", 6.50, true));
        productos.put(7L, new Producto(7L, "Escalope con patatas", 15.00, true));
        productos.put(8L, new Producto(8L, "Vino tinto", 7.00, false));
        this.nextId = 9L;
    }

    @Override
    public void createProduct(Producto producto) {
        producto.setId(nextId);
        productos.put(nextId, producto);
        nextId++;
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return Optional.ofNullable(productos.get(id));
    }

    @Override
    public List<Producto> findAll() {
        return new ArrayList<>(productos.values());
    }

    @Override
    public void save(Long id, Producto producto) {
        producto.setId(id);
        productos.put(id, producto);
    }

    @Override
    public void deleteById(Long id) {
        productos.remove(id);
    }

    @Override
    public void deleteAll() {
        productos.clear();
    }
}
