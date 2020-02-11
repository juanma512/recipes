package com.jmoe.recipes.repositories;

import com.jmoe.recipes.model.Ingredient;
import com.jmoe.recipes.model.Recipe;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

    Set<Ingredient> findByRecipe(Recipe recipe);
}
