package projeto.api.utils.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.api.utils.model.DailyTask;
import java.util.List;

public interface DailyTaskRepository extends JpaRepository<DailyTask,String> {
    List<DailyTask> findAllByUserId(String id);
}
