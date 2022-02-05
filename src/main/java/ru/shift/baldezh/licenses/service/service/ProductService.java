package ru.shift.baldezh.licenses.service.service;

import ru.shift.baldezh.licenses.service.repository.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();
    void addProduct(Product product);
    void deleteProductById(Long id);
    Optional<Product> findProductById(Long id);
}
