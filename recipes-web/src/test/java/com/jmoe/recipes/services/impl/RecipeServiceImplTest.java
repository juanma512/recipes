package com.jmoe.recipes.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jmoe.recipes.converters.RecipeToRecipePayload;
import com.jmoe.recipes.model.Recipe;
import com.jmoe.recipes.payloads.RecipePayload;
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
public class RecipeServiceImplTest {

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private RecipeToRecipePayload recipeToRecipePayload;

    @Test
    public void getRecipes() {
        // Given
        Recipe recipe = new Recipe();
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(recipe);

        // When
        when(recipeRepository.findAll()).thenReturn(recipes);
        when(recipeToRecipePayload.convert(any(Recipe.class))).thenReturn(new RecipePayload());

        // Then
        Set<RecipePayload> actual = recipeService.getRecipesPayloads();
        assertEquals(1, actual.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void getRecipe() {
        // Given
        Recipe recipe = Recipe.builder().id(1L).build();

        // When
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(recipeToRecipePayload.convert(any(Recipe.class))).thenReturn(RecipePayload.builder()
            .id(1L)
            .build());

        // Then
        Optional<RecipePayload> actual = recipeService.getRecipePayload(1L);
        assertEquals(1L, actual.get().getId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

}
