package com.jmoe.recipes.payloads;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientPayload {

    private Long id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasurePayload unitOfMeasure;

}
