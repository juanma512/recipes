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
class NotesPayloadToNotesTest {

    @InjectMocks
    private NotesPayloadToNotes converter;

    @Test
    void convertNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyObject() {
        assertNotNull(converter.convert(new NotesPayload()));
    }

    @Test
    void convert() {
        // Given
        NotesPayload notesPayload = NotesPayload.builder()
            .id(1L)
            .recipeNotes("My notes")
            .build();

        // Then
        Notes notes = converter.convert(notesPayload);
        assertNotNull(notes);
        assertEquals(1L, notes.getId());
        assertEquals("My notes", notes.getRecipeNotes());
    }
}