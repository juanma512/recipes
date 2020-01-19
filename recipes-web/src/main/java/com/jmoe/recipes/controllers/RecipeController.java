package com.jmoe.recipes.controllers;

import com.jmoe.recipes.model.Recipe;
import com.jmoe.recipes.services.RecipeService;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/show/{id}")
    public String showRecipe(@PathVariable(name = "id") String id, Model model) {
        Optional<Recipe> recipe = recipeService.getRecipe(Long.valueOf(id));
        recipe.ifPresent(r -> model.addAttribute("recipe", r));
        return "/recipes/show";
    }

}
