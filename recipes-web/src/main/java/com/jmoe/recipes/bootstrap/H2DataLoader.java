package com.jmoe.recipes.bootstrap;

import com.jmoe.recipes.model.Category;
import com.jmoe.recipes.model.Difficulty;
import com.jmoe.recipes.model.Ingredient;
import com.jmoe.recipes.model.Notes;
import com.jmoe.recipes.model.Recipe;
import com.jmoe.recipes.model.UnitOfMeasure;
import com.jmoe.recipes.repositories.CategoryRepository;
import com.jmoe.recipes.repositories.RecipeRepository;
import com.jmoe.recipes.repositories.UnitOfMeasureRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("default")
@RequiredArgsConstructor
public class H2DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final ResourceLoader resourceLoader;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.debug("Loading recipes ... ");

        UnitOfMeasure unit = unitOfMeasureRepository.findByUom("Unit").orElse(null);
        UnitOfMeasure teaspoon = unitOfMeasureRepository.findByUom("Teaspoon").orElse(null);
        UnitOfMeasure tablespoon = unitOfMeasureRepository.findByUom("Tablespoon").orElse(null);
        UnitOfMeasure dash = unitOfMeasureRepository.findByUom("Dash").orElse(null);
        UnitOfMeasure pounds = unitOfMeasureRepository.findByUom("Pound").orElse(null);

        //Guacamole
        String guacamoleDirections =
            "1. Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n"
                + "2. Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n"
                + "3. Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n"
                + "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n"
                + "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n"
                + "4. Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.";

        Notes guacamoleNotes = Notes.builder().recipeNotes(
            "The trick to making perfect guacamole is using ripe avocados that are just the right amount of ripeness. Not ripe enough and the avocado will be hard and tasteless. Too ripe and the taste will be off.\n"
                + "Check for ripeness by gently pressing the outside of the avocado. If there is no give, the avocado is not ripe yet and will not taste good. If there is a little give, the avocado is ripe. If there is a lot of give, the avocado may be past ripe and not good. In this case, taste test first before using.")
            .build();

        Recipe guacamoleRecipe = createRecipe("Perfect Guacamole", 10, 0, 4,
            "Simply Recipes",
            "http://www.simplyrecipes.com", guacamoleDirections, Difficulty.EASY,
            loadImage("/static/images/1.jpg"),
            guacamoleNotes,
            new HashSet<>(
                Collections
                    .singletonList(categoryRepository.findByDescription("Mexican").orElse(null))));

        guacamoleRecipe.addIngredient(
            createIngredient("Ripe avocados", new BigDecimal("2"), unit));
        guacamoleRecipe.addIngredient(
            createIngredient("Salt", new BigDecimal("0.25"), teaspoon));
        guacamoleRecipe.addIngredient(
            createIngredient("Fresh lime juice or lemon juice", new BigDecimal("1"), tablespoon
            ));
        guacamoleRecipe.addIngredient(
            createIngredient("Minced red onion or thinly sliced green onion", new BigDecimal("2"),
                tablespoon));
        guacamoleRecipe.addIngredient(
            createIngredient("Serrano chiles, stems and seeds removed, minced", new BigDecimal("2"),
                unit));
        guacamoleRecipe
            .addIngredient(createIngredient("Cilantro (leaves and tender stems), finely chopped",
                new BigDecimal("2"), tablespoon));
        guacamoleRecipe.addIngredient(
            createIngredient("Freshly grated black pepper", new BigDecimal("1"), dash
            ));
        guacamoleRecipe.addIngredient(
            createIngredient("Ripe tomato, seeds and pulp removed, chopped", new BigDecimal("0.5"),
                unit));
        guacamoleRecipe.addIngredient(
            createIngredient("Red radishes or jicama, to garnish", new BigDecimal("0"), unit
            ));
        guacamoleRecipe.addIngredient(
            createIngredient("Tortilla chips to serve", new BigDecimal("0"), unit
            ));

        recipeRepository.save(guacamoleRecipe);

        //Grilled cilantro chicken
        String grilledChickenDirections =
            "1 Pound chicken breasts to even thickness: Place the chicken breasts between two piece of plastic wrap or wax paper and pound to an even thickness with a meat mallet or rolling pin.\n"
                + "2 Marinate the chicken: Mix the olive oil, lime zest, lime juice, cilantro, sugar, salt, and pepper together in a large bowl. Add the chicken and massage the marinade into the chicken. Cover and chill for at least 30 minutes, and up to 4 hours or overnight.\n"
                + "3 Preheat Grill: Preheat a gas or charcoal grill for medium-high heat grilling, or until you can hold your hand about an inch over the grates for 1 second.\n"
                + "4 Grill the chicken: Remove the chicken breasts from the refrigerator. Remove them from the marinade and pat them dry with paper towels. Coat the chicken breasts with some olive oil.\n"
                + "Soak a paper towel in a little more oil and use tongs to wipe the grill grates.\n"
                + "When the grill is hot, place the chicken breasts on the grill. Grill for a few minutes on each side, until just cooked through.";

        Recipe grilledChickenRecipe = createRecipe(
            "Grilled Cilantro Lime Chicken", 5, 10, 6,
            "Simply Recipes",
            "http://www.simplyrecipes.com", grilledChickenDirections, Difficulty.EASY,
            loadImage("/static/images/2.jpg"),
            null,
            new HashSet<>(
                Collections
                    .singletonList(categoryRepository.findByDescription("Mexican").orElse(null))));

        grilledChickenRecipe.addIngredient(
            createIngredient("Skinless, boneless chicken breasts", new BigDecimal("2"), pounds
            ));
        grilledChickenRecipe.addIngredient(
            createIngredient("Extra virgin olive oil for the marinade, plus more for grilling",
                new BigDecimal("2"), tablespoon));
        grilledChickenRecipe
            .addIngredient(createIngredient("Grated zest from limes", new BigDecimal("2"), unit
            ));
        grilledChickenRecipe.addIngredient(createIngredient("Juice from limes", new BigDecimal("2"),
            unit));
        grilledChickenRecipe.addIngredient(createIngredient("Chopped cilantro", new BigDecimal("3"),
            tablespoon));
        grilledChickenRecipe.addIngredient(createIngredient("Sugar",
            new BigDecimal("0.5"), teaspoon));
        grilledChickenRecipe
            .addIngredient(createIngredient("Salt", new BigDecimal("0.5"), teaspoon));
        grilledChickenRecipe.addIngredient(createIngredient("Black pepper", new BigDecimal("0.25"),
            teaspoon));
        grilledChickenRecipe.addIngredient(createIngredient(
            "Lime wedges, fresh sprigs of cilantro, and slices of avocado, to serve",
            new BigDecimal("0"), unit)
        );

        recipeRepository.save(grilledChickenRecipe);
    }

    private Recipe createRecipe(String description, Integer prepTime,
        Integer cookTime, Integer servings, String source, String url, String directions,
        Difficulty difficulty, Byte[] image, Notes notes, Set<Category> categories) {
        return Recipe.builder()
            .description(description)
            .prepTime(prepTime)
            .cookTime(cookTime)
            .servings(servings)
            .source(source)
            .url(url)
            .directions(directions)
            .difficulty(difficulty)
            .image(image)
            .notes(notes)
            .categories(categories)
            .ingredients(new HashSet<>())
            .build();
    }

    private Ingredient createIngredient(String description, BigDecimal amount,
        UnitOfMeasure unitOfMeasure) {
        return Ingredient.builder()
            .description(description)
            .amount(amount)
            .unitOfMeasure(unitOfMeasure)
            .build();
    }

    @SneakyThrows
    private Byte[] loadImage(String file) {
        log.debug("Creating byte array ... ");
        Resource resource = resourceLoader.getResource("classpath:" + file);
        byte[] resourceBytes = resource.getInputStream().readAllBytes();
        Byte[] image = new Byte[resourceBytes.length];
        Arrays.setAll(image, index -> resourceBytes[index]);
        return image;
    }
}
