package tztexpress.models;

/**
 * The generic result that gets returned for all api calls
 */
public class GenericResult<T> {
    public boolean IsSuccess;
    public T Model;
    public ExceptionModel Exception;
}
