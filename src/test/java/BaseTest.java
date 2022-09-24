import org.testng.annotations.BeforeSuite;
import tripDemo.dictionaries.IPathEnum;
import tripDemo.model.ConfigQA;

import java.util.Map;

public abstract class BaseTest {
    protected Map<IPathEnum, String> serviceDataMap;

    @BeforeSuite
    public void initBase() {
        serviceDataMap = ConfigQA.getInstance().getServiceDataMap();
    }
}