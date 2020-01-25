package com.jmoe.recipes.services.impl;

import com.jmoe.recipes.converters.CategoryToCategoryPayload;
import com.jmoe.recipes.payloads.CategoryPayload;
import com.jmoe.recipes.repositories.CategoryRepository;
import com.jmoe.recipes.services.CategoryService;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryToCategoryPayload categoryToCategoryPayload;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
        CategoryToCategoryPayload categoryToCategoryPayload) {
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryPayload = categoryToCategoryPayload;
    }

    @Override
    public Set<CategoryPayload> getCategories() {
        log.debug("Getting categories ...");
        Set<CategoryPayload> categories = new HashSet<>();
        categoryRepository.findAll().iterator().forEachRemaining(category ->
            categories.add(categoryToCategoryPayload.convert(category)));
        return categories;
    }
}
