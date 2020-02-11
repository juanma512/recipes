package com.jmoe.recipes.services;

import com.jmoe.recipes.payloads.RecipePayload;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {

    RecipePayload uploadImageForRecipe(Long recipeId, MultipartFile file);

}
