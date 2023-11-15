package devoscar.Restaurante.service;
import devoscar.Restaurante.products.Producto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductService implements CrudService<Producto> {

    private List<Producto> productos = new ArrayList<>();

    @Cacheable("productos")
    @Override
    public List<Producto> findAll() {
        return new ArrayList<>(productos);
    }

    @Cacheable("productos")
    @Override
    public Optional<Producto> findById(Long id) {
        return productos.stream()
                .filter(producto -> producto.getId().equals(id))
                .findFirst();
    }

    @CacheEvict(value = "productos", allEntries = true)
    @Override
    public Producto save(Producto producto) {
        if (producto.getId() == null) {
            producto.setId(generateNextId());
        } else {
            // Actualiza el producto existente si ya tiene un ID
            int index = productos.indexOf(producto);
            if (index != -1) {
                productos.set(index, producto);
            }
        }
        productos.add(producto);
        return producto;
    }

    @CacheEvict(value = "productos", allEntries = true)
    @Override
    public void deleteById(Long id) {
        productos.removeIf(producto -> producto.getId().equals(id));
    }

    @CacheEvict(value = "productos", allEntries = true)
    @Override
    public void deleteAll() {
        productos.clear();
    }


    private Long generateNextId() {
        long maxId = productos.stream()
                .mapToLong(Producto::getId)
                .max()
                .orElse(0);
        return maxId + 1;
    }
}
