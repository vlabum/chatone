package ru.vlabum.lessons;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ArrayFunctionsPositiveTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            { new int[]{4, 7},                      new int[]{7} },
            { new int[]{1, 2, 4, 1, 4},             new int[]{} },
            { new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}, new int[]{1, 7} },
            { new int[]{1, 2, 4, 4, 2, 3, 0, 1, 7}, new int[]{2, 3, 0, 1, 7} }
        });
    }

    private int[] sourceArray;

    private int[] expectedArray;

    public ArrayFunctionsPositiveTest(final int[] sourceArray, final int[] expectedArray) {
        this.sourceArray= sourceArray;
        this.expectedArray = expectedArray;
    }

    @Before
    public void before() {
        System.out.println("Begin");
        ArrayFunctions.printArray(sourceArray);
        ArrayFunctions.printArray(expectedArray);
        ArrayFunctions.printArray(ArrayFunctions.getTail4(sourceArray));
    }

    @After
    public void after() {
        System.out.println("End");
    }

    @Test
    public void testGetTail4(){
        Assert.assertArrayEquals(expectedArray, ArrayFunctions.getTail4(sourceArray));
    }

}
