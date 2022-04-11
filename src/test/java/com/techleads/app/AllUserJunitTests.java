package com.techleads.app;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.techleads.app.service.UserServiceTest;

//@SelectPackages({"com.techleads.app"})
//@SuiteClasses(value = { UserServiceTest.class })
@RunWith(JUnitPlatform.class)
//@ExtendWith(SpringExtension.class)
//@ExtendWith(JUnitPlatform.class)
@SelectPackages("com.techleads.app")
//@Suite
//@IncludeTags("production")
@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = { SpringTestConfiguration.class })
public class AllUserJunitTests {

}
