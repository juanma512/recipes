package com.jmoe.recipes.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    public void getId() {
        Long aLong = 4L;
        category.setId(aLong);
        assertEquals(Long.valueOf(4L), category.getId());
    }

    @Test
    public void getDescription() {
        String aDescription = "Description";
        category.setDescription(aDescription);
        assertEquals("Description", category.getDescription());
    }

    @Test
    public void getRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());
        category.setRecipes(recipes);
        assertEquals(1, category.getRecipes().size());
    }
}