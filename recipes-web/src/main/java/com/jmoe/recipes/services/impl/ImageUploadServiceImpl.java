package com.jmoe.recipes.services.impl;

import com.jmoe.recipes.converters.RecipeToRecipePayload;
import com.jmoe.recipes.model.Recipe;
import com.jmoe.recipes.payloads.RecipePayload;
import com.jmoe.recipes.repositories.RecipeRepository;
import com.jmoe.recipes.services.ImageUploadService;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ImageUploadServiceImpl implements ImageUploadService {

    private final RecipeRepository recipeRepository;
    private final RecipeToRecipePayload converter;

    @Override
    public RecipePayload uploadImageForRecipe(Long recipeId, MultipartFile file) {
        return recipeRepository.findById(recipeId).map(recipe -> {
            recipe.setImage(getBytes(file));
            Recipe savedRecipe = recipeRepository.save(recipe);
            return converter.convert(savedRecipe);
        }).orElse(null);
    }

    @SneakyThrows
    private Byte[] getBytes(MultipartFile file) {
        byte[] fileBytes = file.getBytes();
        Byte[] imageBytes = new Byte[fileBytes.length];
        Arrays.setAll(imageBytes, index -> fileBytes[index]);
        return imageBytes;
    }
}
