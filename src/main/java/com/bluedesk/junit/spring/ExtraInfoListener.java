package com.bluedesk.junit.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class ExtraInfoListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestMethod(final TestContext testContext) throws Exception {
        final Class<?> testClass = testContext.getTestInstance().getClass();
        final String testName = testClass.getSimpleName();
        final String testCaseName = testContext.getTestMethod().getName();
        final Log logger = LogFactory.getLog(testClass);
        logger.info("******************************************************************");
        logger.info("  Running test case: " + testName + "#" + testCaseName);
        logger.info("******************************************************************");

        super.beforeTestMethod(testContext);
    }
}
