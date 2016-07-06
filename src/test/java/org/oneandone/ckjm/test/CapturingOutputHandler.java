package org.oneandone.ckjm.test;

import gr.spinellis.ckjm.output.CkjmOutputHandler;
import gr.spinellis.ckjm.ClassMetrics;
import java.util.HashMap;
import java.util.Map;

/**
 * Stores the metrics seen in a local map.
 * @author Stephan Fuhrmann
 */
class CapturingOutputHandler implements CkjmOutputHandler {

    private final Map<String, ClassMetrics> metrics = new HashMap<>();

    public Map<String, ClassMetrics> getMetrics() {
        return metrics;
    }
    
    @Override
    public void printHeader() {
    }

    @Override
    public void handleClass(String name, ClassMetrics c) {
        metrics.put(name, c);
    }

    @Override
    public void printFooter() {
    }
    
}
