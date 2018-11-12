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
public class ArrayFunctionPositive14 {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { Boolean.FALSE, new int[]{4, 7} },
                { Boolean.TRUE,  new int[]{1, 2, 4, 1, 4} },
                { Boolean.TRUE,  new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7} },
                { Boolean.FALSE, new int[]{0, 2, 4, 4, 2, 3, 0, 0, 7} }
        });
    }

    private Boolean expect;

    private int[] sourceArray;

    public ArrayFunctionPositive14(final Boolean expect, final int[] sourceArray) {
        this.expect = expect;
        this.sourceArray= sourceArray;
    }

    @Before
    public void before() {
        System.out.println("Begin");
        ArrayFunctions.printArray(sourceArray);
        System.out.println(ArrayFunctions.is14(sourceArray));
    }

    @After
    public void after() {
        System.out.println("End");
    }

    @Test
    public void testIs14(){
        Assert.assertEquals(expect, ArrayFunctions.is14(sourceArray));
    }

}
