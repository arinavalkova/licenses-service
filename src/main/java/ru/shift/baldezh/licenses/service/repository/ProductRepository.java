package ru.shift.baldezh.licenses.service.repository;

import org.springframework.data.repository.CrudRepository;
import ru.shift.baldezh.licenses.service.repository.model.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
