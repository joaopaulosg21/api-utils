package projeto.api.utils.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import projeto.api.utils.model.DailyTask;
import projeto.api.utils.model.User;
import projeto.api.utils.service.DailyTaskService;

@RestController
@RequestMapping("/api/tasks/")
@RequiredArgsConstructor
public class DailyTaskController {
    private final DailyTaskService dailyTaskService;

    @PostMapping
    public ResponseEntity<DailyTask> create(@AuthenticationPrincipal User user, @Valid @RequestBody DailyTask dailyTask) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dailyTaskService.create(user,dailyTask));
    }

    @GetMapping("find/all")
    public ResponseEntity<List<DailyTask>> findAll(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(dailyTaskService.findAll(user));
    }
}
