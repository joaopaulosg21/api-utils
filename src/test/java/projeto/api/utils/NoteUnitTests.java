package projeto.api.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projeto.api.utils.DTO.DefaultResponseDTO;
import projeto.api.utils.model.Note;
import projeto.api.utils.model.User;
import projeto.api.utils.repository.NoteRepository;
import projeto.api.utils.service.NoteService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class NoteUnitTests {

    @Mock
    private NoteRepository noteRepository;

    private NoteService noteService;

    @BeforeEach
    void setup() {
        this.noteService = new NoteService(noteRepository);
    }

    @Test
    void createNoteTest() {
        Note note = new Note("test description",LocalDateTime.now());
        User user = new User("test", "test@email.com", "123");
        note.setUser(user);
        when(noteRepository.save(any(Note.class))).thenReturn(note);

        DefaultResponseDTO response = noteService.create(note,user);

        assertEquals("Note created successfully",response.message());
    }
}
