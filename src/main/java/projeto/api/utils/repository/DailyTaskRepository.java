package projeto.api.utils.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.api.utils.model.DailyTask;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DailyTaskRepository extends JpaRepository<DailyTask,String> {
    List<DailyTask> findAllByUserId(String userId);

    Optional<DailyTask> findByIdAndUserId(String id, String userId);

    List<DailyTask> findAllByUserIdAndTime(String id, LocalDateTime time);
}
