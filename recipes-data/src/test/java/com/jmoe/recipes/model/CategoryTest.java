package com.jmoe.recipes.model;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class CategoryTest {

    Category category;

    @Before
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