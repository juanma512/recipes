package com.jmoe.recipes.services.impl;

import com.jmoe.recipes.model.Recipe;
import com.jmoe.recipes.repositories.RecipeRepository;
import com.jmoe.recipes.services.RecipeService;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.info("Fetching recipes");
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Optional<Recipe> getRecipe(Long id) {
        log.info(String.format("Fetching recipe %s", id));
        return recipeRepository.findById(id);
    }


}
