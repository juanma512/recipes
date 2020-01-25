package com.jmoe.recipes.services;

import com.jmoe.recipes.payloads.RecipePayload;
import java.util.Optional;
import java.util.Set;

public interface RecipeService {

    Set<RecipePayload> getRecipes();

    Optional<RecipePayload> getRecipe(Long id);

    RecipePayload saveRecipe(RecipePayload recipePayload);

    void deleteRecipeById(Long id);

}
