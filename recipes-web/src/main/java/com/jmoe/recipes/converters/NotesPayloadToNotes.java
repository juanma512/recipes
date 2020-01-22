package com.jmoe.recipes.converters;

import com.jmoe.recipes.model.Notes;
import com.jmoe.recipes.payloads.NotesPayload;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesPayloadToNotes implements Converter<NotesPayload, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(@Nullable NotesPayload notesPayload) {
        if (notesPayload == null) {
            return null;
        }

        return Notes.builder()
            .id(notesPayload.getId())
            .recipeNotes(notesPayload.getRecipeNotes())
            .build();
    }
}
