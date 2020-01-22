package com.jmoe.recipes.converters;

import com.jmoe.recipes.model.Recipe;
import com.jmoe.recipes.payloads.CategoryPayload;
import com.jmoe.recipes.payloads.IngredientPayload;
import com.jmoe.recipes.payloads.NotesPayload;
import com.jmoe.recipes.payloads.RecipePayload;
import java.util.HashSet;
import java.util.Set;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipePayload implements Converter<Recipe, RecipePayload> {

    private final CategoryToCategoryPayload categoryToCategoryPayload;
    private final NotesToNotesPayload notesToNotesPayload;
    private final IngredientToIngredientPayload ingredientToIngredientPayload;

    public RecipeToRecipePayload(
        CategoryToCategoryPayload categoryToCategoryPayload,
        NotesToNotesPayload notesToNotesPayload,
        IngredientToIngredientPayload ingredientToIngredientPayload) {
        this.categoryToCategoryPayload = categoryToCategoryPayload;
        this.notesToNotesPayload = notesToNotesPayload;
        this.ingredientToIngredientPayload = ingredientToIngredientPayload;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipePayload convert(@Nullable Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        Set<CategoryPayload> categoryPayloadSet = new HashSet<>();
        recipe.getCategories().forEach(
            category -> categoryPayloadSet.add(categoryToCategoryPayload.convert(category))
        );

        Set<IngredientPayload> ingredientPayloadSet = new HashSet<>();
        recipe.getIngredients().forEach(
            ingredient -> ingredientPayloadSet
                .add(ingredientToIngredientPayload.convert(ingredient))
        );

        NotesPayload notesPayload = null;
        if (recipe.getNotes() != null) {
            notesPayload = notesToNotesPayload.convert(recipe.getNotes());
        }

        return RecipePayload.builder()
            .id(recipe.getId())
            .description(recipe.getDescription())
            .ingredients(ingredientPayloadSet)
            .cookTime(recipe.getCookTime())
            .prepTime(recipe.getPrepTime())
            .servings(recipe.getServings())
            .source(recipe.getSource())
            .url(recipe.getUrl())
            .directions(recipe.getDirections())
            .difficulty(recipe.getDifficulty())
            .notes(notesPayload)
            .categories(categoryPayloadSet)
            .build();
    }
}
