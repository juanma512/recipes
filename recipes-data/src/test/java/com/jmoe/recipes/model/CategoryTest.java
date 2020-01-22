package com.jmoe.recipes.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    private static final Long CATEGORY_ID = 4L;
    private static final String DESCRIPTION = "Description";
    private static Set<Recipe> recipes = new HashSet<>();

    static {
        recipes.add(new Recipe());
    }

    private Category category;

    @BeforeEach
    public void setUp() {
        category = Category.builder()
            .id(CATEGORY_ID)
            .description(DESCRIPTION)
            .recipes(recipes)
            .build();
    }

    @Test
    public void getId() {
        assertEquals(CATEGORY_ID, category.getId());
    }

    @Test
    public void getDescription() {
        assertEquals(DESCRIPTION, category.getDescription());
    }

    @Test
    public void getRecipes() {
        assertEquals(1, category.getRecipes().size());
    }
}