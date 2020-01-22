package com.jmoe.recipes.services.impl;

import com.jmoe.recipes.converters.RecipePayloadToRecipe;
import com.jmoe.recipes.converters.RecipeToRecipePayload;
import com.jmoe.recipes.model.Recipe;
import com.jmoe.recipes.payloads.RecipePayload;
import com.jmoe.recipes.repositories.RecipeRepository;
import com.jmoe.recipes.services.RecipeService;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipePayloadToRecipe recipePayloadToRecipe;
    private final RecipeToRecipePayload recipeToRecipePayload;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
        RecipePayloadToRecipe recipePayloadToRecipe,
        RecipeToRecipePayload recipeToRecipePayload) {
        this.recipeRepository = recipeRepository;
        this.recipePayloadToRecipe = recipePayloadToRecipe;
        this.recipeToRecipePayload = recipeToRecipePayload;
    }

    @Override
    public Set<RecipePayload> getRecipesPayloads() {
        log.info("Fetching recipes");
        Set<RecipePayload> recipes = new HashSet<>();
        recipeRepository.findAll().iterator()
            .forEachRemaining(recipe -> recipes.add(recipeToRecipePayload.convert(recipe)));
        return recipes;
    }

    @Override
    public Optional<RecipePayload> getRecipePayload(Long id) {
        log.info(String.format("Fetching recipe %s", id));
        Optional<Recipe> recipe = recipeRepository.findById(id);
        return recipe.map(recipeToRecipePayload::convert);
    }

    @Override
    @Transactional
    public RecipePayload saveRecipePayload(RecipePayload recipePayload) {
        Recipe recipe = recipePayloadToRecipe.convert(recipePayload);
        Recipe savedRecipe = recipeRepository.save(recipe);
        log.info("RecipePayload saved");
        return recipeToRecipePayload.convert(savedRecipe);
    }

}
