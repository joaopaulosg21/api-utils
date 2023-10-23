package projeto.api.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import projeto.api.utils.DTO.CreateDailyTaskDTO;
import projeto.api.utils.DTO.DefaultResponseDTO;
import projeto.api.utils.model.DailyTask;
import projeto.api.utils.model.User;
import projeto.api.utils.repository.DailyTaskRepository;
import projeto.api.utils.service.DailyTaskService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class DailyTaskUnitTest {
    
    private DailyTaskService dailyTaskService;

    @Mock
    private DailyTaskRepository dailyTaskRepository;

    @BeforeEach
    void setup() {
        this.dailyTaskService = new DailyTaskService(dailyTaskRepository);
    }

    @Test
    public void findAllDailyTaskTest() {
        LocalDateTime time = LocalDateTime.of(LocalDate.now(),LocalTime.of(12, 00, 00));
        DailyTask dailyTask = new DailyTask("test description",time,true);
        User user = new User("test name", "test@email.com", "123");
        user.setId("id");
        dailyTask.setUser(user);
        List<DailyTask> allTasks = Arrays.asList(dailyTask);

        when(dailyTaskRepository.findAllByUserId(user.getId())).thenReturn(allTasks);

        List<DailyTask> response = dailyTaskService.findAll(user);

        assertEquals(user.getEmail(),response.get(0).getUser().getEmail());
    }
    
    @Test
    public void completeDailyTaskTest() {
        LocalDateTime time = LocalDateTime.of(LocalDate.now(),LocalTime.of(12, 00, 00));
        DailyTask dailyTask = new DailyTask("test description",time,true);
        dailyTask.setId("daily-id");
        User user = new User("test name", "test@email.com", "123");
        user.setId("id");
        dailyTask.setUser(user);

        when(dailyTaskRepository.findByIdAndUserId(anyString(), anyString())).thenReturn(Optional.of(dailyTask));
        
        DailyTask response = dailyTaskService.complete(user, "daily-id");

        assertTrue(response.isCompleted());
    }   
}
