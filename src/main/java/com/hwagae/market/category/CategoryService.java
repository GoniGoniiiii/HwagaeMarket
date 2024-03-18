package com.hwagae.market.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryEntity getCategoryByCategoryNum(Integer postCategoryNum) {
        return categoryRepository.findByCategoryNum(postCategoryNum);
    }
}
