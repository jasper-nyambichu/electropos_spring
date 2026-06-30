package com.electropro.electropos.repository;

import com.electropro.electropos.entity.Category;
import com.electropro.electropos.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByNameContaining(String name);

    List<Product> findAllByCategory(Category category);

    List<Product> findAllByQuantityLessThanEqual(Integer threshold);
}
