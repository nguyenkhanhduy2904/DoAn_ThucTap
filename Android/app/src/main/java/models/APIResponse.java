package models;

public class APIResponse<T> {
    private String status;
    private int code;
    private String message;
    private T data;

    public APIResponse(String status, int code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public APIResponse() {
    }
}
