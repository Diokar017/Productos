package devoscar.Restaurante.repositories;

import devoscar.Restaurante.products.Producto;

import java.util.List;
import java.util.Optional;
public interface CrudRepository {

    void createProduct(Producto producto);

    Optional<Producto> findById(Long id);

    List<Producto> findAll();

    void save(Long id, Producto producto);

    void deleteById(Long id);

    void deleteAll();
}
