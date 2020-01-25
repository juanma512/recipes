package com.jmoe.recipes.services;

import com.jmoe.recipes.payloads.CategoryPayload;
import java.util.Set;

public interface CategoryService {

    Set<CategoryPayload> getCategories();

}
