package com.jmoe.recipes.services;

import com.jmoe.recipes.payloads.IngredientPayload;
import java.util.Optional;
import java.util.Set;

public interface IngredientService {

    Set<IngredientPayload> getIngredientsForRecipe(Long recipeId);

    Optional<IngredientPayload> getIngredient(Long recipeId, Long id);

    IngredientPayload saveIngredientPayload(IngredientPayload ingredient);

    void deleteIngredientById(Long recipeId, Long ingredientId);

}
