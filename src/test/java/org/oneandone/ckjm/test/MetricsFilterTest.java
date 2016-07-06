package org.oneandone.ckjm.test;

import gr.spinellis.ckjm.ClassMetrics;
import gr.spinellis.ckjm.MetricsFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test basics.
 * 
 * 
SSTest 1 0 0 1 2 0 1 0
Test7 2 1 0 1 3 1 0 1
Test6 2 1 0 1 3 1 0 0
STest 1 0 1 1 2 0 2 0
Test5 2 1 0 1 3 1 0 0
Test4 2 1 0 2 5 1 0 0
Test3 2 1 0 1 4 1 0 0
Test2 1 1 0 1 2 0 0 0

 * 
 * @author Stephan Fuhrmann
 */
public class MetricsFilterTest {
    
    private Path copyToTemp(String resourceName) throws IOException {
        InputStream in = getClass().getResourceAsStream(resourceName);
        Objects.requireNonNull(in, "Resource "+resourceName+" not found");
        Path path = Files.createTempFile("ckjm", "class");
        Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
        return path;
    }
    
    private void testResultsMatch(String expected, String resource) throws IOException {
        CapturingOutputHandler capturingOutputHandler = new CapturingOutputHandler();
        Path resourcePath = copyToTemp(resource);
        
        MetricsFilter.runMetrics(new String [] {resourcePath.toFile().getAbsolutePath()}, capturingOutputHandler);
        Optional<ClassMetrics> classMetrics = capturingOutputHandler.getMetrics().values().stream().findFirst();
        assertTrue(classMetrics.isPresent());
        
        ClassMetrics expectedMetrics = constructFrom(expected);
        
        assertEquals(expectedMetrics, classMetrics.get());
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
    
    @Test
    public void testTest() throws IOException {
        testResultsMatch("Test 3 1 1 0 7 1 6 0", "samples/Test.class");
    }
    @Test
    public void testSSTest() throws IOException {
        testResultsMatch("SSTest 1 0 0 1 2 0 1 0", "samples/SSTest.class");
    }
    @Test
    public void testTest7() throws IOException {
        testResultsMatch("Test7 2 1 0 1 3 1 0 1", "samples/Test7.class");
    }
    @Test
    public void testTest6() throws IOException {
        testResultsMatch("Test6 2 1 0 1 3 1 0 0", "samples/Test6.class");
    }
    @Test
    public void testSTest() throws IOException {
        testResultsMatch("STest 1 0 1 1 2 0 2 0", "samples/STest.class");
    }
    
    @Test
    public void testTest5() throws IOException {
        testResultsMatch("Test5 2 1 0 1 3 1 0 0", "samples/Test5.class");
    }
    @Test
    public void testTest4() throws IOException {
        testResultsMatch("Test4 2 1 0 2 5 1 0 0", "samples/Test4.class");
    }
    @Test
    public void testTest3() throws IOException {
        testResultsMatch("Test3 2 1 0 1 4 1 0 0", "samples/Test3.class");
    }
    @Test
    public void testTest2() throws IOException {
        testResultsMatch("Test2 1 1 0 1 2 0 0 0", "samples/Test2.class");
    }
    
/*    
Test 3 1 1 0 7 1 6 0
SSTest 1 0 0 1 2 0 1 0
Test7 2 1 0 1 3 1 0 1
Test6 2 1 0 1 3 1 0 0
STest 1 0 1 1 2 0 2 0
Test5 2 1 0 1 3 1 0 0
Test4 2 1 0 2 5 1 0 0
Test3 2 1 0 1 4 1 0 0
Test2 1 1 0 1 2 0 0 0
*/
}
