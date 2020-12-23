package com.jmoe.recipes.controllers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.jmoe.recipes.model.UnitOfMeasure;
import com.jmoe.recipes.payloads.IngredientPayload;
import com.jmoe.recipes.payloads.UnitOfMeasurePayload;
import com.jmoe.recipes.services.IngredientService;
import com.jmoe.recipes.services.UnitOfMeasureService;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class IngredientControllerTest {

    @Mock
    private IngredientService ingredientService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    @InjectMocks
    private IngredientController ingredientController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    public void getIngredients() throws Exception {
        mockMvc.perform(get("/recipe/1/ingredients"))
            .andExpect(status().isOk())
            .andExpect(view().name("/ingredients/list"))
            .andExpect(model().attributeExists("ingredients"));

        verify(ingredientService, times(1)).getIngredientsForRecipe(anyLong());
    }

    @Test
    public void getIngredient() throws Exception {
        // Given
        Set<IngredientPayload> ingredientPayloadSet = new HashSet<>();
        ingredientPayloadSet.add(IngredientPayload.builder().id(1L).build());
        Set<UnitOfMeasurePayload> unitOfMeasurePayloadSet = new HashSet<>();
        unitOfMeasurePayloadSet.add(UnitOfMeasurePayload.builder().id(1L).build());

        // When
        when(ingredientService.getIngredientsForRecipe(anyLong())).thenReturn(ingredientPayloadSet);
        when(unitOfMeasureService.getUnitOfMeasures()).thenReturn(unitOfMeasurePayloadSet);

        mockMvc.perform(get("/recipe/1/ingredients/1/show"))
            .andExpect(status().isOk())
            .andExpect(view().name("/ingredients/show"))
            .andExpect(model().attributeExists("ingredient"));

        verify(ingredientService, times(1)).getIngredientsForRecipe(anyLong());
    }

}
