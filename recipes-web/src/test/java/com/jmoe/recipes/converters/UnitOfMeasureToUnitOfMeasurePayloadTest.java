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
class UnitOfMeasureToUnitOfMeasurePayloadTest {

    @InjectMocks
    private UnitOfMeasureToUnitOfMeasurePayload converter;

    @Test
    void convertNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    void convert() {
        // Given
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder()
            .id(1L)
            .uom("Cup")
            .build();

        // Then
        UnitOfMeasurePayload unitOfMeasurePayload = converter.convert(unitOfMeasure);
        assertNotNull(unitOfMeasurePayload);
        assertEquals(1L, unitOfMeasurePayload.getId());
        assertEquals("Cup", unitOfMeasurePayload.getUom());
    }
}