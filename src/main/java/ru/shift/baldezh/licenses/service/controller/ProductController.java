package ru.shift.baldezh.licenses.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shift.baldezh.licenses.service.repository.model.Product;
import ru.shift.baldezh.licenses.service.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public void createProduct(@RequestBody Product product) {
        productService.addProduct(product);
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable long id) {
        productService.deleteProductById(id);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getProductById(@PathVariable long id) {
        Optional<Product> res = productService.findProductById(id);
        if (res.isPresent())
            return ResponseEntity.ok(res.get());
        return ResponseEntity.notFound().build();
    }

    @GetMapping("list")
    public List<Product> getProductList() {
        return productService.getAllProducts();
    }
}
