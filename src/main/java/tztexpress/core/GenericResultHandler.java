package tztexpress.core;

import tztexpress.models.BaseModel;
import tztexpress.models.ExceptionModel;
import tztexpress.models.GenericResult;

/**
 * Generic result handlers - the messages that are returned in JSON should have a general format, for easier
 * handling on client side.
 */
public class GenericResultHandler {

    /**
     * Returns the generic result for the given class, this is usually inferred from the method that is called
     * @param model the model (inherited from BaseModel)
     * @param <T> the model type
     * @return a generic result with success-boolean and the created model
     */
    public static <T> GenericResult<T> GenericResult(T model) {
        GenericResult<T> returnValue = new GenericResult<>();
        returnValue.IsSuccess = true;
        returnValue.Model = model;
        return returnValue;
    }

    /**
     * Overload of the GenericExceptionResult method, with a string as input instead of the full exception
     * This way, exceptions can be thrown easier if a check does not succeed
     * @param exceptionString the message for the Exception
     * @param <T> type of the model that would have been returned if it was successful
     * @return the exceptionmessage, with included stacktrace and exception if in debugmode.
     */
    public static <T> GenericResult<T> GenericExceptionResult(String exceptionString) {
        // TODO: Create Tzt-Specific Invalid Operation/Invalid Data exception
        Exception ex = new Exception(exceptionString);
        return GenericExceptionResult(ex);
    }

    /**
     * Returns a GenericResult with the correct model to satisfy the returnvalue of controllermethods
     * Returns the exception in its own model, with an IsSuccess = false. In debugmode the full stacktrace
     * and the exception are added. Otherwise, a message is shown.
     * @param ex the thrown exception
     * @param <T> the type of the model that would have been returned if it was successful.
     * @return The exceptionmessage, with included stacktrace and exception if in debugmode.
     */
    public static <T> GenericResult<T> GenericExceptionResult(Exception ex) {
        GenericResult<T> returnValue = new GenericResult<>();
        returnValue.IsSuccess = false;

        ExceptionModel exModel = new ExceptionModel();

        // Check if the application is in debugging mode - if not, do not include full exception/stacktrace
        // TODO: Refactor this to a solution-specific option of adding debugparameters, for remote debugging.
        if(java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0) {
            exModel.Exception = ex.toString();
            exModel.StackTrace = ex.getStackTrace().toString();
            exModel.Message = ex.getMessage();
        } else {
            exModel.Message = "An error has occurred, please contact support.";
        }

        returnValue.Exception = exModel;

        return returnValue;
    }
}
