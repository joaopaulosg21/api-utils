package projeto.api.utils.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projeto.api.utils.model.DailyTask;
import projeto.api.utils.model.User;
import projeto.api.utils.repository.DailyTaskRepository;

@Service
@RequiredArgsConstructor
public class DailyTaskService {
    private final DailyTaskRepository dailyTaskRepository;

    public DailyTask create(User user, DailyTask dailyTask) {
        dailyTask.setUser(user);
        return dailyTaskRepository.save(dailyTask);
    }
}
