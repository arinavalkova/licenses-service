package ru.shift.baldezh.licenses.service.service.impl;

import ru.shift.baldezh.licenses.service.repository.ProductRepository;
import ru.shift.baldezh.licenses.service.repository.model.Product;
import ru.shift.baldezh.licenses.service.service.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> getAllProducts() {
        return StreamSupport.stream(
                repository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @Override
    public void addProduct(Product product) {
        repository.save(product);
    }

    @Override
    public void deleteProductById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Product> findProductById(Long id) {
        return repository.findById(id);
    }
}
