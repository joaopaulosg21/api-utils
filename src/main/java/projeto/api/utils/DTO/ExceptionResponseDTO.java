package projeto.api.utils.DTO;

public record ExceptionResponseDTO(int status, String message, String error, String path) {
}
