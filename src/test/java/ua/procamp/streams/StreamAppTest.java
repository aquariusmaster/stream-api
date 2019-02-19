package ua.procamp.streams;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import ua.procamp.streams.stream.AsIntStream;
import ua.procamp.streams.stream.IntStream;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class StreamAppTest {
    
    private IntStream intStream;

    @Before
    public void init() {
        int[] intArr = {-1, 0, 1, 2, 3};
        intStream = AsIntStream.of(intArr);
    }

    @Test
    public void testStreamOperations() {
        System.out.println("streamOperations");
        int expResult = 14;
        int result = StreamApp.streamOperations(intStream);
        assertEquals(expResult, result);        
    }

    @Test
    public void testStreamToArray() {
        System.out.println("streamToArray");
        int[] expResult = {-1, 0, 1, 2, 3};
        int[] result = StreamApp.streamToArray(intStream);
        assertArrayEquals(expResult, result);        
    }

    @Test
    public void testStreamForEach() {
        System.out.println("streamForEach");
        String expResult = "-10123";
        String result = StreamApp.streamForEach(intStream);
        assertEquals(expResult, result);        
    }

    @Test
    public void testFilterMap() {
        System.out.println("testFilterMap");
        int expResult = 14;
        IntStream stream = AsIntStream.of(-2,0,1,2,3);
        int result = StreamApp.streamOperations(intStream);
        assertEquals(expResult, result);
    }

    @Test
    public void testMin() {
        System.out.println("testMin");
        int res = AsIntStream.of(0,2,-3,4,5)
                .filter(x -> x > 0)
                .min();

        assertEquals(2, res);

        int res2 = AsIntStream.of(0,2,-3,4,5)
                .min();

        assertEquals(-3, res2);
    }

    @Test
    public void testMax() {
        System.out.println("testMax");
        int res = AsIntStream.of(0,2,-3,4,5)
                .filter(x -> x < 5)
                .max();

        assertEquals(4, res);

        int res2 = AsIntStream.of(0,2,-3,4,5)
                .max();

        assertEquals(5, res2);
    }

    @Test
    public void testAverage() {
        System.out.println("testAverage");

        Double res = AsIntStream.of(0,2,-3,4,5)
                .average();

        assertEquals(Double.valueOf("1.6"), res);
    }

    
}
