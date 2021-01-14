package com.jmoe.recipes.bootstrap;

import com.jmoe.recipes.model.Category;
import com.jmoe.recipes.model.UnitOfMeasure;
import com.jmoe.recipes.repositories.CategoryRepository;
import com.jmoe.recipes.repositories.UnitOfMeasureRepository;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile({"dev", "prd"})
@RequiredArgsConstructor
public class MySQLDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (unitOfMeasureRepository.count() == 0L) {
            log.debug("Loading unit of measures ... ");
            Arrays.asList("Teaspoon", "Tablespoon", "Cup", "Pinch", "Ounce", "Unit", "Dash", "Pound")
                .forEach(uom -> unitOfMeasureRepository.save(UnitOfMeasure.builder().uom(uom).build()));
        }
        if (categoryRepository.count() == 0L) {
            log.debug("Loading categories ... ");
            Arrays.asList("American", "Italian", "Japanese", "Mexican", "Spanish")
                .forEach(description -> categoryRepository.save(Category.builder().description(description).build()));
        }
    }

}
