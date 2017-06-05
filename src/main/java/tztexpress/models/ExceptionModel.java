package tztexpress.models;

/**
 * The exceptionmodel to return in the generic responses
 */
public class ExceptionModel extends BaseModel {
    public String message;
    public String exception;
    public String stacktrace;
}
