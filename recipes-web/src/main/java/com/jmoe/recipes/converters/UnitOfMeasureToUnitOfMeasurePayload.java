package com.jmoe.recipes.converters;

import com.jmoe.recipes.model.UnitOfMeasure;
import com.jmoe.recipes.payloads.UnitOfMeasurePayload;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasurePayload implements
    Converter<UnitOfMeasure, UnitOfMeasurePayload> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasurePayload convert(@Nullable UnitOfMeasure unitOfMeasure) {
        if (unitOfMeasure == null) {
            return null;
        }

        return UnitOfMeasurePayload.builder()
            .id(unitOfMeasure.getId())
            .uom(unitOfMeasure.getUom())
            .build();
    }
}
