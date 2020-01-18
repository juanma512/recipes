package com.jmoe.recipes.repositories;

import static org.junit.Assert.assertEquals;

import com.jmoe.recipes.model.UnitOfMeasure;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() {
    }

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