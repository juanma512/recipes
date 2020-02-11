package com.jmoe.recipes.converters;

import com.jmoe.recipes.payloads.CategoryPayload;
import com.jmoe.recipes.repositories.CategoryRepository;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCategoryPayload implements Converter<String, CategoryPayload> {

    private final CategoryToCategoryPayload categoryToCategoryPayload;
    private final CategoryRepository categoryRepository;

    public StringToCategoryPayload(
        CategoryToCategoryPayload categoryToCategoryPayload,
        CategoryRepository categoryRepository) {
        this.categoryToCategoryPayload = categoryToCategoryPayload;
        this.categoryRepository = categoryRepository;
    }

    @Synchronized
    @Override
    public CategoryPayload convert(String description) {
        return categoryRepository.findByDescription(description)
            .map(categoryToCategoryPayload::convert).orElse(null);
    }
}
