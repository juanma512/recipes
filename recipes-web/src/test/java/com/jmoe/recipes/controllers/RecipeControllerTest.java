package com.jmoe.recipes.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmoe.recipes.exceptions.NotFoundException;
import com.jmoe.recipes.payloads.CategoryPayload;
import com.jmoe.recipes.payloads.RecipePayload;
import com.jmoe.recipes.services.CategoryService;
import com.jmoe.recipes.services.RecipeService;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @InjectMocks
    private RecipeController recipeController;

    @Mock
    private RecipeService recipeService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
            .setControllerAdvice(new ControllerExceptionHandler())
            .build();
    }

    @Test
    void showRecipe() throws Exception {
        // Given
        RecipePayload recipe = RecipePayload.builder().id(1L).build();

        // When
        when(recipeService.getRecipe(anyLong())).thenReturn(Optional.of(recipe));

        // Then
        mockMvc.perform(get("/recipe/1/show"))
            .andExpect(status().isOk())
            .andExpect(view().name("recipes/show"))
            .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void newRecipe() throws Exception {
        // Given
        Set<CategoryPayload> categoryPayloadSet = new HashSet<>();

        // When
        when(categoryService.getCategories()).thenReturn(categoryPayloadSet);

        // Then
        mockMvc.perform(get("/recipe/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("recipes/create"))
            .andExpect(model().attributeExists("recipe", "allCategories"));
    }

    @Test
    void updateRecipe() throws Exception {
        // Given
        RecipePayload recipe = RecipePayload.builder().id(1L).build();
        Set<CategoryPayload> categoryPayloadSet = new HashSet<>();

        // When
        when(recipeService.getRecipe(anyLong())).thenReturn(Optional.of(recipe));
        when(categoryService.getCategories()).thenReturn(categoryPayloadSet);

        // Then
        mockMvc.perform(get("/recipe/1/update"))
            .andExpect(status().isOk())
            .andExpect(view().name("recipes/create"))
            .andExpect(model().attributeExists("recipe", "allCategories"));
    }

    @Test
    void saveRecipe() throws Exception {
        // Given
        RecipePayload recipe = RecipePayload.builder().id(2L).build();

        // When
        when(recipeService.saveRecipe(any(RecipePayload.class))).thenReturn(recipe);
        when(objectMapper.writeValueAsString(any(RecipePayload.class))).thenReturn("");

        // Then
        mockMvc.perform(post("/recipe/save").contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("id", "")
            .param("description", "some string")
            .param("directions", "some directions"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/recipe/2/show"));
    }

    @Test
    void saveRecipeValidationFail() throws Exception {
        // Then
        mockMvc.perform(post("/recipe/save").contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("id", ""))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("recipe"))
            .andExpect(view().name("recipes/create"));
    }

    @Test
    void deleteRecipe() throws Exception {
        // Then
        mockMvc.perform(get("/recipe/1/delete"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/"));
        verify(recipeService, times(1)).deleteRecipeById(anyLong());
    }

    @Test
    void getRecipePayloadByIdTestNotFound() throws Exception {
        when(recipeService.getRecipe(anyLong())).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/recipe/1/show"))
            .andExpect(status().isNotFound())
            .andExpect(view().name("error404"));
    }

    @Test
    void getRecipePayloadByIdTestBadRequest() throws Exception {
        mockMvc.perform(get("/recipe/asdf/show"))
            .andExpect(status().isBadRequest())
            .andExpect(view().name("error400"));
    }
}