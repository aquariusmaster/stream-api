package ua.procamp.streams;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import ua.procamp.streams.stream.AsIntStream;
import ua.procamp.streams.stream.IntStream;

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
        int expResult = 42;
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
    public void testStream() {
        System.out.println("testFilterMap");

        int reduce = java.util.stream.IntStream.of(1, 2, 3, 4, 5).filter(e -> e > 2).reduce(0, (l, r) -> {
            System.out.println("l=" + l + ", r=" + r);
            return l + r;
        });

        assertEquals(12, reduce);
    }

    
}
