package com.jmoe.recipes.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jmoe.recipes.model.UnitOfMeasure;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Test
    public void findByUomTeaspoon() {
        Optional<UnitOfMeasure> teaspoon = unitOfMeasureRepository.findByUom("Teaspoon");
        assertEquals("Teaspoon", teaspoon.get().getUom());
    }

    @Test
    public void findByUomCup() {
        Optional<UnitOfMeasure> cup = unitOfMeasureRepository.findByUom("Cup");
        assertEquals("Cup", cup.get().getUom());
    }
}