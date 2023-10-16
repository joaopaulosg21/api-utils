package projeto.api.utils.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import projeto.api.utils.exceptions.DailyTaskNotFoundException;
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

    public List<DailyTask> findAll(User user) {
        return dailyTaskRepository.findAllByUserId(user.getId());
    }

    public DailyTask complete(User user, String taskId) {
        DailyTask task = dailyTaskRepository.findByIdAndUserId(taskId, user.getId()).orElseThrow(DailyTaskNotFoundException::new);
        task.setCompleted(true);
        dailyTaskRepository.save(task);
        return task;
    }
}
