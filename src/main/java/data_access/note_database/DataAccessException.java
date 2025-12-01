package data_access.note_database;

import use_case.UseCaseDataAccessException;

public class DataAccessException extends UseCaseDataAccessException {
    private final int code;

    public DataAccessException(String message, int code) {
        super(message);
        this.code = code;
    }

    public DataAccessException(String message) {
        this(message, -1);
    }

    public int getCode() {
        return code;
    }
}
