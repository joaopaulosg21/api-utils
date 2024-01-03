package projeto.api.utils.dto;

public record ExceptionResponseDTO(int status, String message, String error, String path) {
}
