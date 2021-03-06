package com.jmoe.recipes.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmoe.recipes.exceptions.NotFoundException;
import com.jmoe.recipes.payloads.CategoryPayload;
import com.jmoe.recipes.payloads.RecipePayload;
import com.jmoe.recipes.services.CategoryService;
import com.jmoe.recipes.services.RecipeService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
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

    @GetMapping("/recipe/{id}/show")
    public String showRecipe(@PathVariable(name = "id") String id, Model model) {
        Optional<RecipePayload> recipe = recipeService.getRecipe(Long.valueOf(id));
        recipe.ifPresent(r -> model.addAttribute("recipe", r));
        return "recipes/show";
    }

    @ModelAttribute("allCategories")
    public Set<CategoryPayload> getCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model) {
        log.debug("Display form ...");
        model.addAttribute("recipe", new RecipePayload());
        return "recipes/create";
    }

    @GetMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable(name = "id") String id, Model model) {
        Optional<RecipePayload> recipe = recipeService.getRecipe(Long.valueOf(id));
        recipe.ifPresent(r -> model.addAttribute("recipe", r));
        return "recipes/create";
    }

    @SneakyThrows
    @PostMapping("/recipe/save")
    public String saveRecipe(@Valid @ModelAttribute("recipe") RecipePayload recipe, BindingResult bindingResult,
        Model model) {
        log.debug(objectMapper.writeValueAsString(recipe));

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                log.error(error.toString());
            });
            model.addAttribute("recipe", recipe);
            return "recipes/create";
        }

        RecipePayload savedRecipePayload = recipeService.saveRecipe(recipe);
        return "redirect:/recipe/" + savedRecipePayload.getId() + "/show";
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable(name = "id") Long id) {
        log.debug("Deleting recipe " + id);
        recipeService.deleteRecipeById(id);
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception ex) {
        log.error("Handling not found exception");
        Map<String, Object> model = new HashMap<>();
        model.put("exception", ex);
        return new ModelAndView("error404", model);
    }

}
