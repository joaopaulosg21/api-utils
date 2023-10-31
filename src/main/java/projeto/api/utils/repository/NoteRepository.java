package projeto.api.utils.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.api.utils.model.Note;

public interface NoteRepository extends JpaRepository<Note,String> {
}
