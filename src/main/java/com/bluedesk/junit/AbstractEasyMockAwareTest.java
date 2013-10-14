package com.bluedesk.junit;

import static org.easymock.EasyMock.and;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createControl;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.isA;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections15.CollectionUtils;
import org.easymock.IAnswer;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;

public abstract class AbstractEasyMockAwareTest extends AbstractUnitTest {

    private IMocksControl mocksControl;

    protected final IMocksControl mocksControl() {
        return mocksControl;
    }

    @Before
    public final void initMockControl() {
        mocksControl = createControl();
    }

    @After
    public final void destoryMockControl() {
        mocksControl = null;
    }

    protected final void reset() {
        mocksControl.reset();
    }

    protected final void replay() {
        mocksControl.replay();
    }

    protected final void verify() {
        mocksControl.verify();
    }

    protected final <T> T mock(final Class<T> type) {
        return mocksControl().createMock(type);
    }

    @SuppressWarnings("unchecked")
    protected final <T> List<T> mockList(final Class<T> type) {
        return mock(List.class);
    }

    @SuppressWarnings("unchecked")
    protected final <T> Set<T> mockSet(final Class<T> type) {
        return mock(Set.class);
    }

    @SuppressWarnings("unchecked")
    protected final <T> Collection<T> mockCollection(final Class<T> type) {
        return mock(Collection.class);
    }

    @SuppressWarnings("unchecked")
    protected final <K, V> Map<K, V> mockMap(final Class<K> key, final Class<V> value) {
        return mock(Map.class);
    }

    @SuppressWarnings("unchecked")
    protected final <T> Collection<T> anyCollection(final Class<T> type) {
        return and(isA(Collection.class), anyObject(Collection.class));
    }

    @SuppressWarnings("unchecked")
    protected final <T> List<T> anyList(final Class<T> type) {
        return and(isA(List.class), anyObject(List.class));
    }

    @SuppressWarnings("unchecked")
    protected final <T> Set<T> anySet(final Class<T> type) {
        return and(isA(Set.class), anyObject(Set.class));
    }

    @SuppressWarnings("unchecked")
    protected final <K, V> Map<K, V> anyMap(final Class<K> key, final Class<V> value) {
        return and(isA(Map.class), anyObject(Map.class));
    }

    protected final IAnswer<Void> expectCollection(final Collection<?> expectedCollection) {
        return expectCollectionAndAnswer(expectedCollection, null);
    }

    protected final <R> IAnswer<R> expectCollectionAndAnswer(final Collection<?> expectedCollection, final R returnValue) {
        return new IAnswer<R>() {
            @Override
            public R answer() throws Throwable {
                final Object[] arguments = getCurrentArguments();
                Collection<?> collectionArg = Arrays.asList("No matching collection");
                for (final Object arg : arguments) {
                    if (Collection.class.isAssignableFrom(arg.getClass())) {
                        collectionArg = (Collection<?>) arg;
                        break;
                    }
                }
                assertTrue("expecting " + expectedCollection + " but got " + collectionArg,
                        CollectionUtils.isEqualCollection(expectedCollection, collectionArg));

                return returnValue;
            }
        };
    }
}
