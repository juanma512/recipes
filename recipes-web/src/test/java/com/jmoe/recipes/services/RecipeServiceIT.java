package com.jmoe.recipes.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jmoe.recipes.converters.RecipeToRecipePayload;
import com.jmoe.recipes.model.Recipe;
import com.jmoe.recipes.payloads.RecipePayload;
import com.jmoe.recipes.repositories.RecipeRepository;
import com.jmoe.recipes.services.impl.RecipeServiceImpl;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = com.jmoe.recipes.RecipesApplication.class)
public class RecipeServiceIT {

    @Autowired
    private RecipeServiceImpl recipeService;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeToRecipePayload recipeToRecipePayload;

    @Test
    @Transactional
    public void testSaveOfDescription() {
        // Given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe recipe = recipes.iterator().next();
        RecipePayload recipePayload = recipeToRecipePayload.convert(recipe);

        // When
        RecipePayload clone = RecipePayload.builder()
            .id(recipePayload.getId())
            .description("New description")
            .categories(recipePayload.getCategories())
            .ingredients(recipePayload.getIngredients())
            .notes(recipePayload.getNotes())
            .cookTime(recipePayload.getCookTime())
            .prepTime(recipePayload.getPrepTime())
            .difficulty(recipePayload.getDifficulty())
            .url(recipePayload.getUrl())
            .source(recipePayload.getSource())
            .servings(recipePayload.getServings())
            .directions(recipePayload.getDirections())
            .build();
        RecipePayload actual = recipeService.saveRecipe(clone);

        // Then
        assertEquals("New description", actual.getDescription());
        assertEquals(recipePayload.getId(), actual.getId());
        assertEquals(recipePayload.getCategories().size(), actual.getCategories().size());
        assertEquals(recipePayload.getIngredients().size(), actual.getIngredients().size());
    }

}
