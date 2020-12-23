package com.jmoe.recipes.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jmoe.recipes.converters.IngredientToIngredientPayload;
import com.jmoe.recipes.model.Ingredient;
import com.jmoe.recipes.model.Recipe;
import com.jmoe.recipes.payloads.IngredientPayload;
import com.jmoe.recipes.repositories.IngredientRepository;
import com.jmoe.recipes.repositories.RecipeRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    IngredientRepository ingredientRepository;
    @Mock
    IngredientToIngredientPayload converter;

    @InjectMocks
    IngredientServiceImpl ingredientService;

    @Test
    public void getIngredientsForRecipe() {
        // Given
        Set<Ingredient> ingredientSet = new HashSet<>();
        ingredientSet.add(new Ingredient());

        // When
        when(recipeRepository.findById(anyLong()))
            .thenReturn(Optional.of(Recipe.builder().id(1L).build()));
        when(ingredientRepository.findByRecipe(any(Recipe.class))).thenReturn(ingredientSet);
        when(converter.convert(any(Ingredient.class))).thenReturn(new IngredientPayload());

        // Then
        Set<IngredientPayload> ingredients = ingredientService.getIngredientsForRecipe(1L);
        assertEquals(1, ingredients.size());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(ingredientRepository, times(1)).findByRecipe(any(Recipe.class));
    }

    @Test
    public void testGetIngredient() {
        // Given
        Ingredient ingredient = Ingredient.builder().id(1L).build();

        // When
        when(recipeRepository.findById(anyLong()))
            .thenReturn(Optional.of(Recipe.builder()
                .id(1L)
                .ingredients(Set.of(ingredient))
                .build()));
        when(converter.convert(any(Ingredient.class)))
            .thenReturn(IngredientPayload.builder()
                .id(1L)
                .build());

        // Then
        Optional<IngredientPayload> actual = ingredientService.getIngredient(1L, 1L);
        assertEquals(1L, actual.get().getId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

}
