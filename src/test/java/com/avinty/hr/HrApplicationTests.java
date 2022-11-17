package com.avinty.hr;

import com.avinty.hr.integration.AuthControllerTests;
import com.avinty.hr.integration.MainControllerTests;
import com.avinty.hr.unit.DepartmentTests;
import com.avinty.hr.unit.EmployeeTests;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	DepartmentTests.class,
	EmployeeTests.class,
	AuthControllerTests.class,
	MainControllerTests.class
})
@SpringBootTest
class HrApplicationTests {

	@Test
	void contextLoads() {}

}
