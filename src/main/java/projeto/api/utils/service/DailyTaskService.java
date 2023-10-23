package projeto.api.utils.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import projeto.api.utils.DTO.CreateDailyTaskDTO;
import projeto.api.utils.DTO.DefaultResponseDTO;
import projeto.api.utils.exceptions.DailyTaskNotFoundException;
import projeto.api.utils.exceptions.InvalidDateException;
import projeto.api.utils.model.DailyTask;
import projeto.api.utils.model.User;
import projeto.api.utils.repository.DailyTaskRepository;

@Service
@RequiredArgsConstructor
public class DailyTaskService {
    private final DailyTaskRepository dailyTaskRepository;

    public DefaultResponseDTO create(User user, CreateDailyTaskDTO dailyTask) {

        if(Objects.isNull(dailyTask.getEnd_date())) {
            DailyTask task = new DailyTask(dailyTask.getDescription(),dailyTask.getTime(),dailyTask.isEveryDay());
            dailyTaskRepository.save(task);
            return new DefaultResponseDTO("Task successfully created");
        }

        if(dailyTask.getTime().isAfter(dailyTask.getEnd_date())) {
            throw new InvalidDateException();
        }

        List<DailyTask> dailyTasks = new ArrayList<>();
        while(dailyTask.getTime().isBefore(dailyTask.getEnd_date()) || dailyTask.getTime().isEqual(dailyTask.getEnd_date())) {
            DailyTask task = new DailyTask(dailyTask.getDescription(),dailyTask.getTime(),dailyTask.isEveryDay());
            task.setUser(user);
            dailyTasks.add(task);
            dailyTask.setTime(dailyTask.getTime().plusDays(1));
        }

        dailyTaskRepository.saveAll(dailyTasks);
        return new DefaultResponseDTO("Task successfully created");
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
