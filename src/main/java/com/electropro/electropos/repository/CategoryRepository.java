package com.electropro.electropos.repository;

import com.electropro.electropos.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.stereotype.Repository;
@Repository
public interface CategoryRepository  extends JpaRepository<Category, Integer> {
}
