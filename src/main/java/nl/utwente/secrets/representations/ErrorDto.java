package nl.utwente.secrets.representations;

public class ErrorDto {
    private final String path;
    private final String message;

    public ErrorDto(String path, String message) {
        this.path = path;
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }
}
