package com.techleads.app;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.techleads.app.controller.UserControllerTest;
import com.techleads.app.service.UserServiceTest;

@RunWith(JUnitPlatform.class)
//@SelectClasses( { UserControllerTest.class } )
@SelectPackages("com.techleads.app")
public class AllUserJunitTests {

}
