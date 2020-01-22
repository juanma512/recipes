package com.jmoe.recipes.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jmoe.recipes.model.Category;
import com.jmoe.recipes.model.Ingredient;
import com.jmoe.recipes.model.Recipe;
import com.jmoe.recipes.model.UnitOfMeasure;
import com.jmoe.recipes.payloads.CategoryPayload;
import com.jmoe.recipes.payloads.IngredientPayload;
import com.jmoe.recipes.payloads.RecipePayload;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RecipePayloadToRecipeTest {

    @Mock
    private CategoryPayloadToCategory categoryPayloadToCategory;
    @Mock
    private IngredientPayloadToIngredient ingredientPayloadToIngredient;
    @InjectMocks
    private RecipePayloadToRecipe converter;

    @Test
    void convertNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyObject() {
        assertNotNull(converter.convert(new RecipePayload()));
    }

    @Test
    void convert() {
        // Given
        Category category = Category.builder()
            .id(1L)
            .description("Category")
            .build();
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder()
            .id(1L)
            .uom("Cup")
            .build();
        Ingredient ingredient = Ingredient.builder()
            .id(1L)
            .description("Ingredient")
            .amount(BigDecimal.valueOf(1))
            .unitOfMeasure(unitOfMeasure)
            .build();

        Set<CategoryPayload> categoryPayloadSet = new HashSet<>();
        categoryPayloadSet.add(CategoryPayload.builder().build());
        Set<IngredientPayload> ingredientPayloadSet = new HashSet<>();
        ingredientPayloadSet.add(IngredientPayload.builder().build());
        RecipePayload recipePayload = RecipePayload.builder()
            .id(1L)
            .categories(categoryPayloadSet)
            .ingredients(ingredientPayloadSet)
            .notes(null)
            .build();

        // When
        when(categoryPayloadToCategory.convert(any(CategoryPayload.class))).thenReturn(category);
        when(ingredientPayloadToIngredient.convert(any(IngredientPayload.class)))
            .thenReturn(ingredient);

        // Then
        Recipe recipe = converter.convert(recipePayload);
        assertNotNull(recipe);
        assertEquals(1L, recipe.getId());
        verify(categoryPayloadToCategory, times(1)).convert(any(CategoryPayload.class));
        verify(ingredientPayloadToIngredient, times(1)).convert(any(IngredientPayload.class));
    }
}