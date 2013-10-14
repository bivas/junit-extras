package com.bluedesk.junit;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.Description;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class ExtraInfoAwareRunner extends BlockJUnit4ClassRunner {

    public static final class ExtraInfoRunListener extends RunListener {

        @Override
        public void testStarted(final Description description) throws Exception {
            final Class<?> testClass = description.getTestClass();
            final Log logger = LogFactory.getLog(testClass);
            logger.info("******************************************************************");
            logger.info("  Running test case: " + testClass.getSimpleName() + "#" + description.getMethodName());
            logger.info("******************************************************************");
            super.testStarted(description);
        }

    }

    private boolean extraInfoEnabled = true;

    public ExtraInfoAwareRunner(final Class<?> testClass) throws InvocationTargetException, InitializationError {
        super(testClass);
        final PrintExtraInfo printExtraInfo = testClass.getAnnotation(PrintExtraInfo.class);
        if (printExtraInfo == null || !printExtraInfo.value()) {
            extraInfoEnabled = false;
        }
    }

    @Override
    public void run(final RunNotifier notifier) {
        if (extraInfoEnabled) {
            final RunListener listener = new ExtraInfoRunListener();
            notifier.addListener(listener);
        }
        super.run(notifier);
    }

}
