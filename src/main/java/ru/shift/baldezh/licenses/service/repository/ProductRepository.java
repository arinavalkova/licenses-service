package ru.shift.baldezh.licenses.service.repository;

import org.springframework.data.repository.Repository;
import ru.shift.baldezh.licenses.service.repository.model.Product;

public interface ProductRepository extends Repository<Product, Long> {
}
