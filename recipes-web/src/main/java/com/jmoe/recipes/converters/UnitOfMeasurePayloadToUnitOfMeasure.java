package com.jmoe.recipes.converters;

import com.jmoe.recipes.model.UnitOfMeasure;
import com.jmoe.recipes.payloads.UnitOfMeasurePayload;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasurePayloadToUnitOfMeasure implements
    Converter<UnitOfMeasurePayload, UnitOfMeasure> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(@Nullable UnitOfMeasurePayload unitOfMeasurePayload) {
        if (unitOfMeasurePayload == null) {
            return null;
        }

        return UnitOfMeasure.builder()
            .id(unitOfMeasurePayload.getId())
            .uom(unitOfMeasurePayload.getUom())
            .build();
    }
}
