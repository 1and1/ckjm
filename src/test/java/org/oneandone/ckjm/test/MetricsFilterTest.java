package org.oneandone.ckjm.test;

import gr.spinellis.ckjm.ClassMetrics;
import gr.spinellis.ckjm.MetricsFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 * Integration test using predefined classes that are processed.
 * 
 * @author Stephan Fuhrmann
 */
public class MetricsFilterTest {
    
    static List<String> getTestNames() {
        return Arrays.asList("Test", "SSTest", "Test7", "Test6", "STest", "Test5", "Test4", "Test3", "Test2");
    }
    
    @Test
    public void testTest() throws IOException {
        testResultsMatch("Test 3 1 1 0 7 1 6 0", "Test");
    }    
    @Test
    public void testTest7() throws IOException {
        testResultsMatch("Test7 2 1 0 1 3 1 0 1", "Test7");
    }
    @Test
    public void testTest6() throws IOException {
        testResultsMatch("Test6 2 1 0 1 3 1 0 0", "Test6");
    }
    
    // This test is broken, probably due to loading issues
    // of the required classes
    //@Test
    public void testSTest() throws IOException {
        testResultsMatch("STest 1 0 1 1 2 0 2 0", "STest");
    }
    
    @Test
    public void testTest5() throws IOException {
        testResultsMatch("Test5 2 1 0 1 3 1 0 0", "Test5");
    }
    @Test
    public void testTest4() throws IOException {
        testResultsMatch("Test4 2 1 0 2 5 1 0 0", "Test4");
    }
    @Test
    public void testTest3() throws IOException {
        testResultsMatch("Test3 2 1 0 1 4 1 0 0", "Test3");
    }

    @Test
    public void testTest2() throws IOException {
        testResultsMatch("Test2 1 1 0 1 2 0 0 0", "Test2");
    }
    
    @BeforeClass
    public static void parseAll() {
        CapturingOutputHandler capturingOutputHandler = new CapturingOutputHandler();
        
        String testNames[] = getTestNames().
            stream().
            map(f -> (MetricsFilterTest.class.getResource(".")+"samples/"+f+".class").replaceAll("file:", "")).
            collect(Collectors.toList()).toArray(new String[0]);
        MetricsFilter.runMetrics(testNames, capturingOutputHandler);
        allMetrics = capturingOutputHandler.getMetrics();
    }
    
    private static Map<String, ClassMetrics> allMetrics;
    
    private void testResultsMatch(String expected, String resource) throws IOException {
        ClassMetrics classMetrics = allMetrics.get("org.oneandone.ckjm.test.samples."+resource);
        ClassMetrics expectedMetrics = constructFrom(expected);
        assertEquals(expectedMetrics, classMetrics);
    }
    
    protected static ClassMetrics constructFrom(String in) {
        String parts[] = in.split(" ");
        Set<String> hashStrings = new HashSet<>();
        int aff = Integer.parseInt(parts[7]);
        for (int i=0; i < aff; i++) {
            hashStrings.add(Integer.toString(i));
        }
        return new ClassMetrics(
                Integer.parseInt(parts[1]), 
                Integer.parseInt(parts[2]), 
                Integer.parseInt(parts[3]), 
                Integer.parseInt(parts[4]), 
                Integer.parseInt(parts[5]), 
                Integer.parseInt(parts[6]), 
                hashStrings,
                Integer.parseInt(parts[8]),
                true, false);
    }
}
