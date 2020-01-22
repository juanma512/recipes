package com.jmoe.recipes.converters;

import com.jmoe.recipes.model.Ingredient;
import com.jmoe.recipes.payloads.IngredientPayload;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientPayloadToIngredient implements Converter<IngredientPayload, Ingredient> {

    private final UnitOfMeasurePayloadToUnitOfMeasure unitOfMeasurePayloadToUnitOfMeasure;

    public IngredientPayloadToIngredient(
        UnitOfMeasurePayloadToUnitOfMeasure unitOfMeasurePayloadToUnitOfMeasure) {
        this.unitOfMeasurePayloadToUnitOfMeasure = unitOfMeasurePayloadToUnitOfMeasure;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(@Nullable IngredientPayload ingredientPayload) {
        if (ingredientPayload == null) {
            return null;
        }

        return Ingredient.builder()
            .id(ingredientPayload.getId())
            .amount(ingredientPayload.getAmount())
            .description(ingredientPayload.getDescription())
            .unitOfMeasure(
                unitOfMeasurePayloadToUnitOfMeasure.convert(ingredientPayload.getUnitOfMeasure()))
            .build();
    }
}
