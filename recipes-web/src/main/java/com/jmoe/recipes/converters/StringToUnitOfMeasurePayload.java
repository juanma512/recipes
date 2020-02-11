package com.jmoe.recipes.converters;

import com.jmoe.recipes.payloads.UnitOfMeasurePayload;
import com.jmoe.recipes.repositories.UnitOfMeasureRepository;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StringToUnitOfMeasurePayload implements Converter<String, UnitOfMeasurePayload> {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasurePayload converter;

    @Synchronized
    @Override
    public UnitOfMeasurePayload convert(String source) {
        return unitOfMeasureRepository.findByUom(source).map(converter::convert).orElse(null);
    }
}
