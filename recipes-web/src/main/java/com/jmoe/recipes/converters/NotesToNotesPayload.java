package com.jmoe.recipes.converters;

import com.jmoe.recipes.model.Notes;
import com.jmoe.recipes.payloads.NotesPayload;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesPayload implements Converter<Notes, NotesPayload> {

    @Synchronized
    @Nullable
    @Override
    public NotesPayload convert(@Nullable Notes notes) {
        if (notes == null) {
            return null;
        }

        return NotesPayload.builder()
            .id(notes.getId())
            .recipeNotes(notes.getRecipeNotes())
            .build();
    }
}
