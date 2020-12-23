package com.jmoe.recipes.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jmoe.recipes.model.Ingredient;
import com.jmoe.recipes.model.Recipe;
import com.jmoe.recipes.model.UnitOfMeasure;
import com.jmoe.recipes.payloads.IngredientPayload;
import com.jmoe.recipes.payloads.UnitOfMeasurePayload;
import com.jmoe.recipes.repositories.RecipeRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IngredientPayloadToIngredientTest {

    @Mock
    private UnitOfMeasurePayloadToUnitOfMeasure unitOfMeasurePayloadToUnitOfMeasure;

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private IngredientPayloadToIngredient converter;

    @Test
    void convertNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyObject() {
        assertNotNull(converter.convert(new IngredientPayload()));
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
        IngredientPayload ingredientPayload = IngredientPayload.builder()
            .id(1L)
            .amount(BigDecimal.valueOf(1))
            .description("Ingredient")
            .unitOfMeasure(unitOfMeasurePayload)
            .build();

        // When
        when(unitOfMeasurePayloadToUnitOfMeasure.convert(any(UnitOfMeasurePayload.class)))
            .thenReturn(unitOfMeasure);

        // Then
        Ingredient ingredient = converter.convert(ingredientPayload);
        assertNotNull(ingredient);
        assertEquals(1L, ingredient.getId());
        assertEquals("Ingredient", ingredient.getDescription());
        assertEquals(BigDecimal.valueOf(1L), ingredient.getAmount());
        assertEquals(unitOfMeasure, ingredient.getUnitOfMeasure());
        verify(unitOfMeasurePayloadToUnitOfMeasure, times(1))
            .convert(any(UnitOfMeasurePayload.class));
    }
}