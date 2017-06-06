import tztexpress.core.GenericResultHandler;
import tztexpress.models.CourierModel;
import tztexpress.models.GenericResult;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Ewout on 14-5-2017.
 */
public class GenericResultHandlerTests {
    @Test
    public void GenericResultTest() {
        // arrange
        CourierModel testModel = new CourierModel();
        testModel.available = true;
        testModel.cost = 5.0;
        testModel.ischeapestoption = true;

        // act
        GenericResult<CourierModel> testResult = GenericResultHandler.GenericResult(testModel);
        // assert
        Assert.assertEquals(true, testResult.issuccess);
        Assert.assertEquals(testModel, testResult.model);
        Assert.assertNull(testResult.exception);

    }

    @Test
    public void GenericExceptionStringResultTest() {
        // arrange
        String exceptionMessage = "exceptionStringTest";

        // act
        GenericResult<CourierModel> testResult = GenericResultHandler.<CourierModel>GenericExceptionResult(exceptionMessage);
        // assert
        Assert.assertEquals(false, testResult.issuccess);
        Assert.assertNull(testResult.model);

        // Debugmode should return more information:
        if(java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0) {
            Assert.assertEquals(exceptionMessage, testResult.exception.message);
            Assert.assertNotNull(testResult.exception.exception);
            Assert.assertNotNull(testResult.exception.stacktrace);
        } else {
            Assert.assertEquals("An error has occurred, please contact support.", testResult.exception.message);
            Assert.assertNull(testResult.exception.exception);
            Assert.assertNull(testResult.exception.stacktrace);
        }

    }

    @Test
    public void GenericExceptionResultTest() {
        // arrange
        Exception ex = new Exception("exceptionStringTest");

        // act
        GenericResult<CourierModel> testResult = GenericResultHandler.<CourierModel>GenericExceptionResult(ex);
        // assert
        Assert.assertEquals(false, testResult.issuccess);
        Assert.assertNull(testResult.model);

        // Debugmode should return more information:
        if(java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0) {
            Assert.assertEquals(ex.getMessage(), testResult.exception.message);
            Assert.assertNotNull(testResult.exception.exception);
            Assert.assertNotNull(testResult.exception.stacktrace);
        } else {
            Assert.assertEquals("An error has occurred, please contact support.", testResult.exception.message);
            Assert.assertNull(testResult.exception.exception);
            Assert.assertNull(testResult.exception.stacktrace);
        }

    }
}
