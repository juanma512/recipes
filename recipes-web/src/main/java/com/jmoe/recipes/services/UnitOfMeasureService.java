package com.jmoe.recipes.services;

import com.jmoe.recipes.payloads.UnitOfMeasurePayload;
import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasurePayload> getUnitOfMeasures();

}
