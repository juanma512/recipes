package com.jmoe.recipes.converters;

import com.jmoe.recipes.model.Ingredient;
import com.jmoe.recipes.model.Recipe;
import com.jmoe.recipes.payloads.IngredientPayload;
import java.util.Optional;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientPayload implements Converter<Ingredient, IngredientPayload> {

    private final UnitOfMeasureToUnitOfMeasurePayload unitOfMeasureToUnitOfMeasurePayload;

    public IngredientToIngredientPayload(
        UnitOfMeasureToUnitOfMeasurePayload unitOfMeasureToUnitOfMeasurePayload) {
        this.unitOfMeasureToUnitOfMeasurePayload = unitOfMeasureToUnitOfMeasurePayload;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientPayload convert(@Nullable Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }

        return IngredientPayload.builder()
            .id(ingredient.getId())
            .recipeId(Optional.ofNullable(ingredient.getRecipe())
                .map(Recipe::getId)
                .orElse(null))
            .amount(ingredient.getAmount())
            .description(ingredient.getDescription())
            .unitOfMeasure(
                unitOfMeasureToUnitOfMeasurePayload.convert(ingredient.getUnitOfMeasure()))
            .build();
    }
}
