package com.jmoe.recipes.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmoe.recipes.payloads.IngredientPayload;
import com.jmoe.recipes.payloads.RecipePayload;
import com.jmoe.recipes.payloads.UnitOfMeasurePayload;
import com.jmoe.recipes.services.IngredientService;
import com.jmoe.recipes.services.RecipeService;
import com.jmoe.recipes.services.UnitOfMeasureService;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Controller
@RequestMapping({"/recipe/{recipeId}/ingredients"})
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;
    private final ObjectMapper objectMapper;

    @GetMapping("")
    public String getIngredients(@PathVariable(name = "recipeId") Long recipeId, Model model) {
        log.debug("Controller -- Getting ingredients");
        Set<IngredientPayload> ingredients = ingredientService
            .getIngredientsForRecipe(recipeId);
        model.addAttribute("ingredients", ingredients);
        return "/ingredients/list";
    }

    @GetMapping("/{ingredientId}/show")
    public String getIngredient(@PathVariable Long recipeId,
        @PathVariable Long ingredientId, Model model) {
        log.debug("Controller -- Get ingredient " + ingredientId + " for recipe " + recipeId);
        IngredientPayload ingredient = ingredientService
            .getIngredientsForRecipe(recipeId)
            .stream()
            .filter(
                ingredientPayload -> ingredientPayload.getId().equals(ingredientId))
            .findFirst()
            .orElse(null);
        model.addAttribute("ingredient", ingredient);
        return "/ingredients/show";
    }

    @ModelAttribute("allUnitsOfMeasure")
    public Set<UnitOfMeasurePayload> getUnitOfMeasure() {
        return unitOfMeasureService.getUnitOfMeasures();
    }

    @GetMapping("/new")
    public String newIngredient(@PathVariable(name = "recipeId") Long recipeId, Model model) {
        Optional<RecipePayload> recipePayload = recipeService.getRecipe(recipeId);
        model.addAttribute("ingredient", IngredientPayload.builder()
            .recipeId(recipePayload.map(RecipePayload::getId).orElse(null))
            .build());
        return "/ingredients/create";
    }

    @GetMapping("/{ingredientId}/update")
    public String updateIngredient(@PathVariable(name = "recipeId") Long recipeId,
        @PathVariable(name = "ingredientId") Long ingredientId,
        Model model) {
        Optional<IngredientPayload> ingredientOptional = ingredientService
            .getIngredient(recipeId, ingredientId);
        ingredientOptional.ifPresent(ingredient -> model.addAttribute("ingredient", ingredient));
        return "/ingredients/create";
    }

    @SneakyThrows
    @PostMapping("/save")
    public String saveOrUpdateIngredient(@ModelAttribute IngredientPayload ingredient) {
        log.debug(objectMapper.writeValueAsString(ingredient));
        IngredientPayload savedIngredientPayload = ingredientService
            .saveIngredientPayload(ingredient);
        return "redirect:/recipe/" + savedIngredientPayload.getRecipeId() + "/ingredients/"
            + savedIngredientPayload.getId()
            + "/show";
    }

    @GetMapping("/{ingredientId}/delete")
    public String deleteRecipe(@PathVariable(name = "recipeId") Long recipeId,
        @PathVariable(name = "ingredientId") Long ingredientId) {
        log.debug("Deleting ingredient " + ingredientId);
        ingredientService.deleteIngredientById(recipeId, ingredientId);
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

}
