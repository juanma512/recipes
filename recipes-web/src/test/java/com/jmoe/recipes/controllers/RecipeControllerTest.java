package com.jmoe.recipes.controllers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.jmoe.recipes.payloads.RecipePayload;
import com.jmoe.recipes.services.RecipeService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @InjectMocks
    private RecipeController recipeController;

    @Mock
    private RecipeService recipeService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    void showRecipe() throws Exception {
        // Given
        RecipePayload recipe = RecipePayload.builder().id(1L).build();

        // When
        when(recipeService.getRecipePayload(anyLong())).thenReturn(Optional.of(recipe));

        // Then
        mockMvc.perform(get("/recipe/show/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("/recipes/show"))
            .andExpect(model().attributeExists("recipe"));
    }
}