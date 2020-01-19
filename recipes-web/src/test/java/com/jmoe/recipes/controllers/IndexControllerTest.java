package com.jmoe.recipes.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.jmoe.recipes.model.Recipe;
import com.jmoe.recipes.services.RecipeService;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

@ExtendWith(MockitoExtension.class)
public class IndexControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    Model model;

    @InjectMocks
    IndexController indexController;

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("index"));
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