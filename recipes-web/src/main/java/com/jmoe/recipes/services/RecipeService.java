package com.jmoe.recipes.services;

import com.jmoe.recipes.model.Recipe;
import java.util.Optional;
import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Optional<Recipe> getRecipe(Long id);

}
