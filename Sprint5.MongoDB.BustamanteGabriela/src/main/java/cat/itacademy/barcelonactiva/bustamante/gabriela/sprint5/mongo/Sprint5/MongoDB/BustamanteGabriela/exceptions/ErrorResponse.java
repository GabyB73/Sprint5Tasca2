package cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.exceptions;

public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
