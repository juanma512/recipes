package com.jmoe.recipes.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.jmoe.recipes.model.Notes;
import com.jmoe.recipes.payloads.NotesPayload;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotesToNotesPayloadTest {

    @InjectMocks
    private NotesToNotesPayload converter;

    @Test
    void convertNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyObject() {
        assertNotNull(converter.convert(new Notes()));
    }

    @Test
    void convert() {
        // Given
        Notes notes = Notes.builder()
            .id(1L)
            .recipeNotes("My notes")
            .build();

        // Then
        NotesPayload notesPayload = converter.convert(notes);
        assertNotNull(notesPayload);
        assertEquals(1L, notesPayload.getId());
        assertEquals("My notes", notesPayload.getRecipeNotes());
    }
}