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
class CategoryPayloadToCategoryTest {

    @InjectMocks
    private CategoryPayloadToCategory converter;

    @Test
    void convertNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyObject() {
        assertNotNull(converter.convert(new CategoryPayload()));
    }

    @Test
    void convert() {
        // Given
        CategoryPayload categoryPayload = CategoryPayload.builder()
            .id(1L)
            .description("Description")
            .build();

        // Then
        Category category = converter.convert(categoryPayload);
        assertNotNull(category);
        assertEquals(1L, category.getId());
        assertEquals("Description", category.getDescription());
    }
}