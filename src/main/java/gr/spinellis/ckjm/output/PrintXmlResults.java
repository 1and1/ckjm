/*
 * (C) Copyright 2005 Diomidis Spinellis, Julien Rentrop
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

import java.io.PrintStream;

/**
 * XML output formatter
 *
 * @author Julien Rentrop
 */
public class PrintXmlResults implements CkjmOutputHandler {

    private final PrintStream p;

    public PrintXmlResults(PrintStream p) {
        this.p = p;
    }

    @Override
    public void printHeader() {
        p.println("<?xml version=\"1.0\"?>");
        p.println("<ckjm>");
    }

    @Override
    public void handleClass(String name, ClassMetrics c) {
        p.print("<class>\n"
            + "<name>" + name + "</name>\n"
            + "<wmc>" + c.getWmc() + "</wmc>\n"
            + "<dit>" + c.getDit() + "</dit>\n"
            + "<noc>" + c.getNoc() + "</noc>\n"
            + "<cbo>" + c.getCbo() + "</cbo>\n"
            + "<rfc>" + c.getRfc() + "</rfc>\n"
            + "<lcom>" + c.getLcom() + "</lcom>\n"
            + "<ca>" + c.getCa() + "</ca>\n"
            + "<npm>" + c.getNpm() + "</npm>\n"
            + "</class>\n");
    }

    @Override
    public void printFooter() {
        p.println("</ckjm>");
    }
}
