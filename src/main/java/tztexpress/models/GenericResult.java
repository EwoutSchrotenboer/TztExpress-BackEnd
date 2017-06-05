package tztexpress.models;

/**
 * The generic result that gets returned for all api calls
 */
public class GenericResult<T> {
    public boolean issuccess;
    public T model;
    public ExceptionModel exception;
}
