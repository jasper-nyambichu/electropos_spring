package com.electropro.electropos.repository;

import com.electropro.electropos.entity.Category;
import com.electropro.electropos.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByNameContaining(String name);

    List<Product> findAllByCategory(Category category);

    List<Product> findAllByQuantityLessThanEqual(Integer threshold);
}
