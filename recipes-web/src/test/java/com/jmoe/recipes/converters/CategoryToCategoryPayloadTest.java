package com.jmoe.recipes.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.jmoe.recipes.model.Category;
import com.jmoe.recipes.payloads.CategoryPayload;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryToCategoryPayloadTest {

    @InjectMocks
    private CategoryToCategoryPayload converter;

    @Test
    void convertNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyObject() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    void convert() {
        // Given
        Category category = Category.builder()
            .id(1L)
            .description("Description")
            .build();

        // Then
        CategoryPayload categoryPayload = converter.convert(category);
        assertNotNull(categoryPayload);
        assertEquals(1L, categoryPayload.getId());
        assertEquals("Description", categoryPayload.getDescription());
    }
}