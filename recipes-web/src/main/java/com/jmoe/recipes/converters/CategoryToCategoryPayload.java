package com.jmoe.recipes.converters;

import com.jmoe.recipes.model.Category;
import com.jmoe.recipes.payloads.CategoryPayload;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryPayload implements Converter<Category, CategoryPayload> {

    @Synchronized
    @Nullable
    @Override
    public CategoryPayload convert(@Nullable Category category) {
        if (category == null) {
            return null;
        }

        return CategoryPayload.builder()
            .id(category.getId())
            .description(category.getDescription())
            .build();
    }
}
