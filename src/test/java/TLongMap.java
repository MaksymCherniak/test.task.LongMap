import org.junit.Before;
import org.junit.Test;
import task.longmap.LongMap;
import task.longmap.TestMap;

import java.util.HashMap;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class TLongMap {
    private static final String TEST = "test";
    private static TestMap<String> map;

    @Before
    public void init() {
        map = new LongMap<>();
        map.put(1, TEST);
    }

    @Test
    public void putOperationTest() {
        assertThat(map.put(2, "second"), is(nullValue()));

        map.remove(2);
    }

    @Test
    public void putDuplicateDataImpossible() {
        assertThat(map.put(1, TEST), is(TEST));
    }

    @Test
    public void getOperationTest() {
        assertThat(map.get(1), is(TEST));
    }

    @Test
    public void containsKeyOperationTest() {
        assertThat(map.containsKey(1), is(true));
    }

    @Test
    public void containsValueOperationTest() {
        assertThat(map.containsValue(TEST), is(true));
    }

    @Test
    public void sizeOperationTest() {
        assertThat((int) map.size(), is(1));
    }

    @Test
    public void removeOperationTest() {
        assertThat(map.remove(1), is(TEST));
    }

    @Test
    public void isEmptyOperationTest() {
        assertThat(map.isEmpty(), is(false));
    }

    @Test
    public void keysOperationTest() {
        assertThat(map.keys().length, is(1));
    }

    @Test
    public void valuesOperationTest() {
        assertThat(map.values(String.class).length, is(1));
    }

    @Test
    public void clearOperationTest() {
        map.clear();

        assertThat((int) map.size(), is(0));
    }

    @Test
    public void canNotRemoveIfNotExist() {
        assertThat(map.remove(3), is(nullValue()));
    }

    @Test
    public void canNotGetIfNotExist() {
        assertThat(map.get(3), is(nullValue()));
    }

    @Test
    public void returnFalseIfNotEmpty() {
        assertThat(map.isEmpty(), is(false));
    }

    @Test
    public void returnFalseIfDoesNotContainKey() {
        assertThat(map.containsKey(3), is(false));
    }

    @Test
    public void returnFalseIfDoesNotContainValue() {
        assertThat(map.containsValue("newTest"), is(false));
    }
}
