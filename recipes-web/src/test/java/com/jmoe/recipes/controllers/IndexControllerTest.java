package com.jmoe.recipes.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jmoe.recipes.model.Recipe;
import com.jmoe.recipes.services.RecipeService;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

public class IndexControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    Model model;

    IndexController indexController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    public void getIndex() {
        // Given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(createDummyRecipe(1L));
        recipes.add(createDummyRecipe(2L));

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        // When
        when(recipeService.getRecipes()).thenReturn(recipes);

        // Then
        String viewName = indexController.getIndex(model);

        assertEquals("index", viewName);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipe> captorValue = argumentCaptor.getValue();
        assertEquals(2, captorValue.size());
    }

    private Recipe createDummyRecipe(Long id) {
        Recipe recipe = new Recipe();
        recipe.setId(id);
        return recipe;
    }
}