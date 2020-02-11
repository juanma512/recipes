package com.jmoe.recipes.converters;

import com.jmoe.recipes.model.Category;
import com.jmoe.recipes.model.Ingredient;
import com.jmoe.recipes.model.Notes;
import com.jmoe.recipes.model.Recipe;
import com.jmoe.recipes.payloads.RecipePayload;
import java.util.HashSet;
import java.util.Set;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipePayloadToRecipe implements Converter<RecipePayload, Recipe> {

    private final CategoryPayloadToCategory categoryPayloadToCategory;
    private final IngredientPayloadToIngredient ingredientPayloadToIngredient;
    private final NotesPayloadToNotes notesPayloadToNotes;

    public RecipePayloadToRecipe(
        CategoryPayloadToCategory categoryPayloadToCategory,
        IngredientPayloadToIngredient ingredientPayloadToIngredient,
        NotesPayloadToNotes notesPayloadToNotes) {
        this.categoryPayloadToCategory = categoryPayloadToCategory;
        this.ingredientPayloadToIngredient = ingredientPayloadToIngredient;
        this.notesPayloadToNotes = notesPayloadToNotes;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(@Nullable RecipePayload recipePayload) {
        if (recipePayload == null) {
            return null;
        }

        Set<Category> categorySet = new HashSet<>();
        recipePayload.getCategories().forEach(
            category -> categorySet.add(categoryPayloadToCategory.convert(category))
        );

        Set<Ingredient> ingredientSet = new HashSet<>();
        recipePayload.getIngredients().forEach(
            ingredient -> ingredientSet.add(ingredientPayloadToIngredient.convert(ingredient))
        );

        Notes notes = null;
        if (recipePayload.getNotes() != null) {
            notes = notesPayloadToNotes.convert(recipePayload.getNotes());
        }

        return Recipe.builder()
            .id(recipePayload.getId())
            .description(recipePayload.getDescription())
            .ingredients(ingredientSet)
            .cookTime(recipePayload.getCookTime())
            .prepTime(recipePayload.getPrepTime())
            .servings(recipePayload.getServings())
            .source(recipePayload.getSource())
            .url(recipePayload.getUrl())
            .directions(recipePayload.getDirections())
            .difficulty(recipePayload.getDifficulty())
            .image(recipePayload.getImage())
            .notes(notes)
            .categories(categorySet)
            .build();
    }
}
