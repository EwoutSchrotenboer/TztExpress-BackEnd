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
        testModel.Available = true;
        testModel.Cost = 5.0;
        testModel.IsCheapestOption = true;

        // act
        GenericResult<CourierModel> testResult = GenericResultHandler.GenericResult(testModel);
        // assert
        Assert.assertEquals(true, testResult.IsSuccess);
        Assert.assertEquals(testModel, testResult.Model);
        Assert.assertNull(testResult.Exception);

    }

    @Test
    public void GenericExceptionStringResultTest() {
        // arrange
        String exceptionMessage = "exceptionStringTest";

        // act
        GenericResult<CourierModel> testResult = GenericResultHandler.<CourierModel>GenericExceptionResult(exceptionMessage);
        // assert
        Assert.assertEquals(false, testResult.IsSuccess);
        Assert.assertNull(testResult.Model);

        // Debugmode should return more information:
        if(java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0) {
            Assert.assertEquals(exceptionMessage, testResult.Exception.Message);
            Assert.assertNotNull(testResult.Exception.Exception);
            Assert.assertNotNull(testResult.Exception.StackTrace);
        } else {
            Assert.assertEquals("An error has occurred, please contact support.", testResult.Exception.Message);
            Assert.assertNull(testResult.Exception.Exception);
            Assert.assertNull(testResult.Exception.StackTrace);
        }

    }

    @Test
    public void GenericExceptionResultTest() {
        // arrange
        Exception ex = new Exception("exceptionStringTest");

        // act
        GenericResult<CourierModel> testResult = GenericResultHandler.<CourierModel>GenericExceptionResult(ex);
        // assert
        Assert.assertEquals(false, testResult.IsSuccess);
        Assert.assertNull(testResult.Model);

        // Debugmode should return more information:
        if(java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0) {
            Assert.assertEquals(ex.getMessage(), testResult.Exception.Message);
            Assert.assertNotNull(testResult.Exception.Exception);
            Assert.assertNotNull(testResult.Exception.StackTrace);
        } else {
            Assert.assertEquals("An error has occurred, please contact support.", testResult.Exception.Message);
            Assert.assertNull(testResult.Exception.Exception);
            Assert.assertNull(testResult.Exception.StackTrace);
        }

    }
}
