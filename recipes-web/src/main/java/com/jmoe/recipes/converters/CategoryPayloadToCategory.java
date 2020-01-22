package com.jmoe.recipes.converters;

import com.jmoe.recipes.model.Category;
import com.jmoe.recipes.payloads.CategoryPayload;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryPayloadToCategory implements Converter<CategoryPayload, Category> {

    @Synchronized
    @Nullable
    @Override
    public Category convert(@Nullable CategoryPayload categoryPayload) {
        if (categoryPayload == null) {
            return null;
        }

        return Category.builder()
            .id(categoryPayload.getId())
            .description(categoryPayload.getDescription())
            .build();
    }
}
