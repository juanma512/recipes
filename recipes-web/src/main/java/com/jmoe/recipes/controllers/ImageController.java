package com.jmoe.recipes.controllers;

import com.jmoe.recipes.payloads.RecipePayload;
import com.jmoe.recipes.services.ImageUploadService;
import com.jmoe.recipes.services.RecipeService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
@RequestMapping("/recipe/{recipeId}")
public class ImageController {

    private final ImageUploadService imageUploadService;
    private final RecipeService recipeService;

    @GetMapping("/image")
    public String showForm(@PathVariable(name = "recipeId") Long recipeId, Model model) {
        model.addAttribute("recipeId", recipeId);
        return "/images/create";
    }

    @PostMapping("/image")
    public String saveImage(@PathVariable(name = "recipeId") Long recipeId,
        @RequestParam(name = "file") MultipartFile file) {
        RecipePayload recipePayload = imageUploadService.uploadImageForRecipe(recipeId, file);
        return "redirect:/recipe/" + recipePayload.getId() + "/show";
    }

    @GetMapping("/image/show")
    public void showImage(@PathVariable(name = "recipeId") Long recipeId,
        HttpServletResponse response) {
        Optional<RecipePayload> recipePayloadOptional = recipeService.getRecipe(recipeId);
        recipePayloadOptional.ifPresent(
            recipePayload -> {
                Byte[] image = recipePayload.getImage();
                if (image != null) {
                    byte[] byteArray = new byte[image.length];
                    int i = 0;

                    for (Byte wrappedByte : image) {
                        byteArray[i++] = wrappedByte; //auto unboxing
                    }

                    response.setContentType("image/jpeg");
                    InputStream is = new ByteArrayInputStream(byteArray);
                    try {
                        IOUtils.copy(is, response.getOutputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        );
    }
}
