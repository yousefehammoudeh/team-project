package data_access;

public class HTTPCode {
    public static final int SUCCESS_CODE = 200;
    public static final int CREDENTIAL_ERROR = 401;
    public static final int NOT_FOUND_ERROR = 404;
    public static final int CONFLICT_ERROR = 409;
    public static final int TOO_MANY_REQUESTS = 429;

    private HTTPCode() {}
}
