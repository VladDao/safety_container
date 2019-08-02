package com.plietnov.task;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestSafetyContainer.class,
        TestWrapperUnmodContainer.class})
public class MainTestSuite {
}
