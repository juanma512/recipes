package com.jmoe.recipes.payloads;

import com.jmoe.recipes.model.Difficulty;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipePayload {

    private Long id;
    private Set<IngredientPayload> ingredients = new HashSet<>();
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Difficulty difficulty;
    private Byte[] image;
    private NotesPayload notes;
    private Set<CategoryPayload> categories = new HashSet<>();

}
