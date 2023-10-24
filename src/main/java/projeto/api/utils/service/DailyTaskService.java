package projeto.api.utils.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

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
            task.setUser(user);
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

    private List<DailyTask> findAllByDate(User user, String date) {
        String[] values = date.split("-");
        LocalDate localDate = LocalDate.of(Integer.parseInt(values[2]),Integer.parseInt(values[1]),Integer.parseInt(values[0]));
        return this.findAll(user)
                .stream().filter(item -> item.getTime().toLocalDate().isEqual(localDate)).toList();
    }

    private List<DailyTask> findAllByDescription(User user, String description) {
        return dailyTaskRepository.findAllByUserIdAndDescription(user.getId(),description);
    }

    public List<DailyTask> findAllByParam(User user, String... params) {
        Pattern pattern = Pattern.compile("^[0-9\\\\-]+$");
        for(String param : params) {
            if(Objects.nonNull(param)) {
                if(pattern.matcher(param).find()) {
                    return this.findAllByDate(user,param);
                }
                return this.findAllByDescription(user,param.replaceAll("-"," "));
            }
        }

        throw new RuntimeException("Invalid Param");
    }
}
