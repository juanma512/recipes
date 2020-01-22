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
import com.jmoe.recipes.payloads.CategoryPayload;
import com.jmoe.recipes.payloads.IngredientPayload;
import com.jmoe.recipes.payloads.RecipePayload;
import com.jmoe.recipes.payloads.UnitOfMeasurePayload;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RecipeToRecipePayloadTest {

    @Mock
    private CategoryToCategoryPayload categoryToCategoryPayload;
    @Mock
    private IngredientToIngredientPayload ingredientToIngredientPayload;
    @InjectMocks
    private RecipeToRecipePayload converter;

    @Test
    void convertNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyObject() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    void convert() {
        // Given
        CategoryPayload category = CategoryPayload.builder()
            .id(1L)
            .description("Category")
            .build();
        UnitOfMeasurePayload unitOfMeasurePayload = UnitOfMeasurePayload.builder()
            .id(1L)
            .uom("Cup")
            .build();
        IngredientPayload ingredientPayload = IngredientPayload.builder()
            .id(1L)
            .description("Ingredient")
            .amount(BigDecimal.valueOf(1))
            .unitOfMeasure(unitOfMeasurePayload)
            .build();

        Set<Category> categorySet = new HashSet<>();
        categorySet.add(Category.builder().build());
        Set<Ingredient> ingredientSet = new HashSet<>();
        ingredientSet.add(Ingredient.builder().build());
        Recipe recipe = Recipe.builder()
            .id(1L)
            .categories(categorySet)
            .ingredients(ingredientSet)
            .notes(null)
            .build();

        // When
        when(categoryToCategoryPayload.convert(any(Category.class))).thenReturn(category);
        when(ingredientToIngredientPayload.convert(any(Ingredient.class)))
            .thenReturn(ingredientPayload);

        // Then
        RecipePayload recipePayload = converter.convert(recipe);
        assertNotNull(recipePayload);
        assertEquals(1L, recipePayload.getId());
        verify(categoryToCategoryPayload, times(1)).convert(any(Category.class));
        verify(ingredientToIngredientPayload, times(1)).convert(any(Ingredient.class));
    }
}