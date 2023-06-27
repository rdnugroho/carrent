package id.co.indivara.jdt12.miniproject;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Suite.class)
@Suite.SuiteClasses({ CustomerControllerTest.class, DriverControllerTest.class, RentControllerTest.class, TransactionControllerTest.class, VehicleControllerTest.class, })
public class TestSuite {

}
