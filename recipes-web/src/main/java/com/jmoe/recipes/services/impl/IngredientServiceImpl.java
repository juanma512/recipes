package com.jmoe.recipes.services.impl;

import com.jmoe.recipes.converters.IngredientPayloadToIngredient;
import com.jmoe.recipes.converters.IngredientToIngredientPayload;
import com.jmoe.recipes.model.Ingredient;
import com.jmoe.recipes.model.Recipe;
import com.jmoe.recipes.payloads.IngredientPayload;
import com.jmoe.recipes.repositories.IngredientRepository;
import com.jmoe.recipes.repositories.RecipeRepository;
import com.jmoe.recipes.repositories.UnitOfMeasureRepository;
import com.jmoe.recipes.services.IngredientService;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final IngredientToIngredientPayload ingredientToIngredientPayload;
    private final IngredientPayloadToIngredient ingredientPayloadToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Override
    @Transactional
    public Set<IngredientPayload> getIngredientsForRecipe(Long recipeId) {
        log.debug("Getting ingredients for recipe ... " + recipeId);
        Set<IngredientPayload> ingredients = new HashSet<>();
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        recipeOptional.ifPresent(
            recipe -> ingredientRepository.findByRecipe(recipe).forEach(
                ingredient -> ingredients.add(ingredientToIngredientPayload.convert(ingredient)))
        );
        return ingredients;
    }

    @Override
    @Transactional
    public Optional<IngredientPayload> getIngredient(Long recipeId, Long id) {
        log.debug("Getting ingredient " + id + " for recipe " + recipeId);

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isEmpty()) {
            //todo impl error handling
            log.error("recipe id not found. Id: " + recipeId);
        }

        Optional<IngredientPayload> ingredientPayloadOptional = recipeOptional
            .flatMap(recipe -> recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .findFirst())
            .map(ingredientToIngredientPayload::convert);

        if (ingredientPayloadOptional.isEmpty()) {
            //todo impl error handling
            log.error("Ingredient id not found: " + id);
        }

        return ingredientPayloadOptional;
    }

    @Override
    @Transactional
    public IngredientPayload saveIngredientPayload(IngredientPayload ingredientPayload) {
        log.debug("Saving ingredient ... ");

        Optional<Recipe> recipeOptional = recipeRepository
            .findById(ingredientPayload.getRecipeId());

        if (recipeOptional.isEmpty()) {
            //todo toss error if not found!
            log.error("Recipe not found for id: " + ingredientPayload.getRecipeId());
            return new IngredientPayload();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredientPayload.getId() != null &&
                    ingredient.getId().equals(ingredientPayload.getId()))
                .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientPayload.getDescription());
                ingredientFound.setAmount(ingredientPayload.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository
                    .findById(ingredientPayload.getUnitOfMeasure().getId())
                    .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //add new Ingredient
                recipe.addIngredient(Objects
                    .requireNonNull(ingredientPayloadToIngredient.convert(ingredientPayload)));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Ingredient ingredient = savedRecipe.getIngredients().stream()
                .filter(recipeIngredient -> recipeIngredient.getId()
                    .equals(ingredientPayload.getId()) ||
                    (
                        recipeIngredient.getDescription().equals(ingredientPayload.getDescription())
                            &&
                            recipeIngredient.getAmount().equals(ingredientPayload.getAmount()) &&
                            recipeIngredient.getUnitOfMeasure().getId()
                                .equals(ingredientPayload.getUnitOfMeasure().getId())
                    )
                )
                .findFirst()
                .orElse(null);

            return ingredientToIngredientPayload.convert(ingredient);
        }
    }

    @Override
    @Transactional
    public void deleteIngredientById(Long recipeId, Long ingredientId) {
        log.debug("Deleting ingredient ... ");
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        recipeOptional.flatMap(
            recipe -> recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findFirst()
        ).ifPresent(ingredient -> {
            ingredient.setRecipe(null);
            ingredientRepository.deleteById(ingredient.getId());
        });
    }
}
