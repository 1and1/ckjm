/*
 * (C) Copyright 2016 Stephan Fuhrmann, 1und1 Internet SE
 *
 * Permission to use, copy, and distribute this software and its documentation
 * for any purpose and without fee is hereby granted, provided that the above
 * copyright notice appear in all copies and that both that copyright notice and
 * this permission notice appear in supporting documentation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 * MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package gr.spinellis.ckjm.output;

import gr.spinellis.ckjm.ClassMetrics;
import java.io.IOException;
import java.io.PrintStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * CSV text output formatter
 *
 * @author Stephan Fuhrmann
 */
public class PrintCSVResults implements CkjmOutputHandler {

    private final CSVPrinter printer;

    public PrintCSVResults(PrintStream p) {
        try {
            printer = CSVFormat.EXCEL.withHeader("Classname", "CA", "CBO", "DIT", "LCOM", "NOC", "NPM", "RFC", "WMC").print(p);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void handleClass(String name, ClassMetrics c) {
        try {
            printer.printRecord(name, c.getCa(), c.getCbo(), c.getDit(), c.getLcom(), c.getNoc(), c.getNpm(), c.getRfc(), c.getWmc());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void printHeader() {
    }

    @Override
    public void printFooter() {
    }
}
