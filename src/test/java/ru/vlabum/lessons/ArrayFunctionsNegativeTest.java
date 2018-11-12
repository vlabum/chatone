package ru.vlabum.lessons;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ArrayFunctionsNegativeTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { new int[]{0, 7} },
                { new int[]{1, 2, 1, 1, 3} },
                { new int[]{1, 2, 2, 8, 2, 3, 7, 1, 7} },
                { new int[]{1, 2, 1, 1, 2, 3, 0, 1, 7} }
        });
    }

    private int[] sourceArray;

    public ArrayFunctionsNegativeTest(final int[] sourceArray) {
        this.sourceArray = sourceArray;
    }

    @Before
    public void before() {
        System.out.println("Begin");
        ArrayFunctions.printArray(sourceArray);
    }

    @After
    public void after() {
        System.out.println("End");
    }

    @Test(expected = RuntimeException.class)
    public void testTail4() {
        ArrayFunctions.getTail4(sourceArray);
    }

}
