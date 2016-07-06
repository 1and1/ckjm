/*
 * (C) Copyright 2005 Diomidis Spinellis
 *
 * Permission to use, copy, and distribute this software and its
 * documentation for any purpose and without fee is hereby granted,
 * provided that the above copyright notice appear in all copies and that
 * both that copyright notice and this permission notice appear in
 * supporting documentation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 * MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package gr.spinellis.ckjm;

import gr.spinellis.ckjm.output.CkjmOutputHandler;
import java.util.*;

/**
 * A container of class metrics mapping class names to their metrics. This class
 * contains the the metrics for all class's during the filter's operation. Some
 * metrics need to be updated as the program processes other classes, so the
 * class's metrics will be recovered from this container to be updated.
 *
 * @version $Revision: 1.9 $
 * @author <a href="http://www.spinellis.gr">Diomidis Spinellis</a>
 */
class ClassMetricsContainer {

    /**
     * The map from class names to the corresponding metrics
     */
    private final Map<String, ClassMetrics> m = new HashMap<>();

    /**
     * Return a class's metrics
     */
    public ClassMetrics getMetrics(String name) {
        ClassMetrics cm = m.get(name);
        if (cm == null) {
            cm = new ClassMetrics();
            m.put(name, cm);
        }
        return cm;
    }

    /**
     * Print the metrics of all the visited classes.
     */
    public void printMetrics(CkjmOutputHandler handler) {
        Set<Map.Entry<String, ClassMetrics>> entries = m.entrySet();
        Iterator<Map.Entry<String, ClassMetrics>> i;

        for (i = entries.iterator(); i.hasNext();) {
            Map.Entry<String, ClassMetrics> e = i.next();
            ClassMetrics cm = e.getValue();
            if (cm.isVisited() && (MetricsFilter.includeAll() || cm.isPublic())) {
                handler.handleClass(e.getKey(), cm);
            }
        }
    }
}
