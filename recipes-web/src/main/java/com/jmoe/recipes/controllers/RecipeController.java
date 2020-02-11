package com.jmoe.recipes.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmoe.recipes.payloads.CategoryPayload;
import com.jmoe.recipes.payloads.RecipePayload;
import com.jmoe.recipes.services.CategoryService;
import com.jmoe.recipes.services.RecipeService;
import java.util.Optional;
import java.util.Set;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;
    private final CategoryService categoryService;
    private final ObjectMapper objectMapper;

    public RecipeController(RecipeService recipeService,
        CategoryService categoryService,
        ObjectMapper objectMapper) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/{id}/show")
    public String showRecipe(@PathVariable(name = "id") String id, Model model) {
        Optional<RecipePayload> recipe = recipeService.getRecipe(Long.valueOf(id));
        recipe.ifPresent(r -> model.addAttribute("recipe", r));
        return "/recipes/show";
    }

    @ModelAttribute("allCategories")
    public Set<CategoryPayload> getCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("/new")
    public String newRecipe(Model model) {
        log.debug("Display form ...");
        model.addAttribute("recipe", new RecipePayload());
        return "/recipes/create";
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable(name = "id") String id, Model model) {
        Optional<RecipePayload> recipe = recipeService.getRecipe(Long.valueOf(id));
        recipe.ifPresent(r -> model.addAttribute("recipe", r));
        return "/recipes/create";
    }

    @SneakyThrows
    @PostMapping("/save")
    public String saveRecipe(@ModelAttribute RecipePayload recipe) {
        log.debug(objectMapper.writeValueAsString(recipe));
        RecipePayload savedRecipePayload = recipeService.saveRecipe(recipe);
        return "redirect:/recipe/" + savedRecipePayload.getId() + "/show";
    }

    @GetMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable(name = "id") Long id) {
        log.debug("Deleting recipe " + id);
        recipeService.deleteRecipeById(id);
        return "redirect:/";
    }

}
