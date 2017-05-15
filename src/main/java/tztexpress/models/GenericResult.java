package tztexpress.models;

/**
 * Created by Ewout on 14-5-2017.
 */
public class GenericResult<TModel> {
    public boolean IsSuccess;
    public TModel Model;
    public ExceptionModel Exception;
}
