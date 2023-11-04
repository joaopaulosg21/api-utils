package projeto.api.utils.service;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import projeto.api.utils.DTO.DefaultResponseDTO;
import projeto.api.utils.model.Note;
import projeto.api.utils.model.User;
import projeto.api.utils.repository.NoteRepository;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public DefaultResponseDTO create(Note note, User user) {
        note.setUser(user);
        noteRepository.save(note);
        return new DefaultResponseDTO("Note created successfully");
    }

    public List<Note> findAll(User user) {
        return noteRepository.findAllByUserId(user.getId());
    }
}
