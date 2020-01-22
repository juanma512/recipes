package com.jmoe.recipes.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jmoe.recipes.model.Ingredient;
import com.jmoe.recipes.model.UnitOfMeasure;
import com.jmoe.recipes.payloads.IngredientPayload;
import com.jmoe.recipes.payloads.UnitOfMeasurePayload;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IngredientToIngredientPayloadTest {

    @Mock
    UnitOfMeasureToUnitOfMeasurePayload unitOfMeasureToUnitOfMeasurePayload;

    @InjectMocks
    private IngredientToIngredientPayload converter;

    @Test
    void convertNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyObject() {
        assertNotNull(converter.convert(new Ingredient()));
    }

    @Test
    void convert() {
        // Given
        UnitOfMeasurePayload unitOfMeasurePayload = UnitOfMeasurePayload.builder()
            .id(1L)
            .uom("Cup")
            .build();
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder()
            .id(1L)
            .uom("Cup")
            .build();
        Ingredient ingredient = Ingredient.builder()
            .id(1L)
            .amount(BigDecimal.valueOf(1))
            .description("Ingredient")
            .unitOfMeasure(unitOfMeasure)
            .build();

        // When
        when(unitOfMeasureToUnitOfMeasurePayload.convert(any(UnitOfMeasure.class)))
            .thenReturn(unitOfMeasurePayload);

        // Then
        IngredientPayload ingredientPayload = converter.convert(ingredient);
        assertNotNull(ingredientPayload);
        assertEquals(1L, ingredientPayload.getId());
        assertEquals("Ingredient", ingredientPayload.getDescription());
        assertEquals(BigDecimal.valueOf(1L), ingredientPayload.getAmount());
        assertEquals(unitOfMeasurePayload, ingredientPayload.getUnitOfMeasure());
        verify(unitOfMeasureToUnitOfMeasurePayload, times(1))
            .convert(any(UnitOfMeasure.class));
    }
}