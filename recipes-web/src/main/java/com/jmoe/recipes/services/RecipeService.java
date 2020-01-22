package com.jmoe.recipes.services;

import com.jmoe.recipes.payloads.RecipePayload;
import java.util.Optional;
import java.util.Set;

public interface RecipeService {

    Set<RecipePayload> getRecipesPayloads();

    Optional<RecipePayload> getRecipePayload(Long id);

    RecipePayload saveRecipePayload(RecipePayload recipePayload);

}
