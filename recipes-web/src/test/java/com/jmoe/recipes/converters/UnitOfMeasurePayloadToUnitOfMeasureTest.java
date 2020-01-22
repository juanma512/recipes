package com.jmoe.recipes.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.jmoe.recipes.model.UnitOfMeasure;
import com.jmoe.recipes.payloads.UnitOfMeasurePayload;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasurePayloadToUnitOfMeasureTest {

    @InjectMocks
    private UnitOfMeasurePayloadToUnitOfMeasure converter;

    @Test
    void convertNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasurePayload()));
    }

    @Test
    void convert() {
        // Given
        UnitOfMeasurePayload unitOfMeasurePayload = UnitOfMeasurePayload.builder()
            .id(1L)
            .uom("Cup")
            .build();

        // Then
        UnitOfMeasure unitOfMeasure = converter.convert(unitOfMeasurePayload);
        assertNotNull(unitOfMeasure);
        assertEquals(1L, unitOfMeasure.getId());
        assertEquals("Cup", unitOfMeasure.getUom());
    }
}