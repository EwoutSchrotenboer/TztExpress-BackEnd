package tztexpress.models;

/**
 * Created by Ewout on 14-5-2017.
 */
public class GenericResult<T> {
    public boolean IsSuccess;
    public T Model;
    public ExceptionModel Exception;
}
