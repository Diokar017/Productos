package productos.controllers;

import productos.products.Producto;
import productos.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    private final CrudService productoService;

    @Autowired
    public ProductController(CrudService<Producto> productoService) {
        this.productoService = productoService;
    }


    @GetMapping("/api/funkos")
    public ResponseEntity<List<Producto>> getProducto() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/api/productos/{id}")
    public ResponseEntity<Optional> getProducto(@PathVariable Long id) {
        Optional producto = productoService.findById(id);
        if (producto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(producto);
    }

    @PostMapping("/api/productos")
    public ResponseEntity<Object> createProduct(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.save(producto));
    }

    @PutMapping("/api/productos/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        Optional<Producto> existingProduct = productoService.findById(id);

        if (existingProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Producto productoExistente = existingProduct.get();
        if (productoActualizado.getNomPlato() != null) {
            productoExistente.setNomPlato(productoActualizado.getNomPlato());
        }
        if (productoActualizado.getPrecio() != 0) {
            productoExistente.setPrecio(productoActualizado.getPrecio());
        }
        Producto productoActualizadoGuardado = (Producto) productoService.save(productoExistente);
        return ResponseEntity.ok(productoActualizadoGuardado);
    }
}