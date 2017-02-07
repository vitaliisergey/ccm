package by.intexsoft.ccm;

import by.intexsoft.ccm.tests.PackControllerTest;
import by.intexsoft.ccm.tests.ProvisionControllerTest;
import by.intexsoft.ccm.tests.SubscriberControllerTest;
import by.intexsoft.ccm.tests.SubscriberServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ProvisionControllerTest.class, PackControllerTest.class, SubscriberControllerTest.class, SubscriberServiceTest.class})
public class AllTests {
}
