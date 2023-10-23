package projeto.api.utils.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import projeto.api.utils.DTO.ExceptionResponseDTO;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandle extends ResponseEntityExceptionHandler {
    private final HttpHeaders headers = new HttpHeaders();
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String,String> erros = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(item -> {
            String field = item.getDefaultMessage().split(" ")[0];
            String message = item.getDefaultMessage();
            erros.put(field,message);
        });
        return handleExceptionInternal(ex, erros, headers, status, request);
    }

    @ExceptionHandler({EmailAlreadyUsedException.class, InvalidDateException.class})
    public ResponseEntity<Object> unprocessableEntityHandle(RuntimeException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        String message = ex.getMessage();
        String error = status.name();
        String path = request.getDescription(true).split("[=;]")[1];
        ExceptionResponseDTO response = new ExceptionResponseDTO(status.value(),message,error,path);
        return handleExceptionInternal(ex,response,headers,status,request);
    }

    @ExceptionHandler({LoginException.class})
    public ResponseEntity<Object> unauthorizedExceptionHandle(RuntimeException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = ex.getMessage();
        String error = status.name();
        String path = request.getDescription(true).split("[=;]")[1];
        ExceptionResponseDTO response = new ExceptionResponseDTO(status.value(),message,error,path);
        return handleExceptionInternal(ex,response,headers,status,request);
    }

    @ExceptionHandler({ShoppingListNotFoundException.class,DailyTaskNotFoundException.class})
    public ResponseEntity<Object> notFoundExceptionHandle(RuntimeException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = ex.getMessage();
        String error = status.name();
        String path = request.getDescription(true).split("[=;]")[1];
        ExceptionResponseDTO response = new ExceptionResponseDTO(status.value(),message,error,path);
        return handleExceptionInternal(ex,response,headers,status,request);
    }
}
