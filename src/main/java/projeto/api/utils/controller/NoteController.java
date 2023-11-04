package projeto.api.utils.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projeto.api.utils.DTO.DefaultResponseDTO;
import projeto.api.utils.model.Note;
import projeto.api.utils.model.User;
import projeto.api.utils.service.NoteService;

@RestController
@RequestMapping("/api/notes/")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<DefaultResponseDTO> create(@Valid @RequestBody Note note, @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.create(note,user));
    }

    @GetMapping
    public ResponseEntity<List<Note>> findAll(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(noteService.findAll(user));
    }
}
