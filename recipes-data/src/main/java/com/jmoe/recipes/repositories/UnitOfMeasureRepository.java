package com.jmoe.recipes.repositories;

import com.jmoe.recipes.model.UnitOfMeasure;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

    Optional<UnitOfMeasure> findByUom(String unitOfMeasure);
}
