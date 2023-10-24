package projeto.api.utils.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import projeto.api.utils.DTO.CreateDailyTaskDTO;
import projeto.api.utils.DTO.DefaultResponseDTO;
import projeto.api.utils.DTO.FindByDateDTO;
import projeto.api.utils.model.DailyTask;
import projeto.api.utils.model.User;
import projeto.api.utils.service.DailyTaskService;

@RestController
@RequestMapping("/api/tasks/")
@RequiredArgsConstructor
public class DailyTaskController {
    private final DailyTaskService dailyTaskService;

    @PostMapping
    public ResponseEntity<DefaultResponseDTO> create(@AuthenticationPrincipal User user, @Valid @RequestBody CreateDailyTaskDTO dailyTask) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dailyTaskService.create(user,dailyTask));
    }

    @GetMapping("find/all")
    public ResponseEntity<List<DailyTask>> findAll(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(dailyTaskService.findAll(user));
    }

    @PatchMapping("complete/{id}")
    public ResponseEntity<DailyTask> completeTask(@AuthenticationPrincipal User user, @PathVariable String id) {
        return ResponseEntity.ok(dailyTaskService.complete(user, id));
    }

    @GetMapping("find/date")
    public ResponseEntity<List<DailyTask>> findAllByDate(@AuthenticationPrincipal User user, @RequestBody FindByDateDTO date) {
        return ResponseEntity.ok(dailyTaskService.findAllByDate(user,date));

    }
}
