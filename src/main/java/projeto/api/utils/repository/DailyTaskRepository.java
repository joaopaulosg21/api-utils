package projeto.api.utils.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.api.utils.model.DailyTask;

public interface DailyTaskRepository extends JpaRepository<DailyTask,String> {
}
