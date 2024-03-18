package com.hwagae.market.category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <CategoryEntity, Integer> {
    CategoryEntity findByCategoryNum(Integer categoryNum);
}
