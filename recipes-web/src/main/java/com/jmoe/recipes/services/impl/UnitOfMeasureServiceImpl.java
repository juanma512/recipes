package com.jmoe.recipes.services.impl;

import com.jmoe.recipes.converters.UnitOfMeasureToUnitOfMeasurePayload;
import com.jmoe.recipes.payloads.UnitOfMeasurePayload;
import com.jmoe.recipes.repositories.UnitOfMeasureRepository;
import com.jmoe.recipes.services.UnitOfMeasureService;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasurePayload unitOfMeasureToUnitOfMeasurePayload;

    public UnitOfMeasureServiceImpl(
        UnitOfMeasureRepository unitOfMeasureRepository,
        UnitOfMeasureToUnitOfMeasurePayload unitOfMeasureToUnitOfMeasurePayload) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasurePayload = unitOfMeasureToUnitOfMeasurePayload;
    }

    @Override
    public Set<UnitOfMeasurePayload> getUnitOfMeasures() {
        Set<UnitOfMeasurePayload> unitOfMeasureSet = new HashSet<>();
        unitOfMeasureRepository.findAll().forEach(unitOfMeasure -> unitOfMeasureSet
            .add(unitOfMeasureToUnitOfMeasurePayload.convert(unitOfMeasure)));
        return unitOfMeasureSet;
    }
}
