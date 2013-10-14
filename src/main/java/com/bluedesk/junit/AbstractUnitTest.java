package com.bluedesk.junit;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.runner.RunWith;

@RunWith(ExtraInfoAwareRunner.class)
public abstract class AbstractUnitTest {

    protected static final <T> void assertCollectionsEquals(final Collection<T> expected, final Collection<T> actual) {
        assertCollectionsEquals("", expected, actual);
    }

    protected static final <T> void assertCollectionsEquals(final String message, final Collection<T> expected,
            final Collection<T> actual) {
        final StringBuilder builder = new StringBuilder();
        if (StringUtils.isNotBlank(message)) {
            builder.append(message).append(", ");
        }
        builder.append("expected ").append(expected);
        builder.append(" but was ").append(actual);
        assertTrue(builder.toString(), CollectionUtils.isEqualCollection(expected, actual));
    }

}