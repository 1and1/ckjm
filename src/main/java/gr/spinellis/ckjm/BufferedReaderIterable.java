/*
 * (C) Copyright 2016 Stephan Fuhrmann
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
package gr.spinellis.ckjm;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

/**
 * Iterable iterating over a buffered readers lines.
 * @author Stephan Fuhrmann
 */
public class BufferedReaderIterable implements Iterable<String> {
    private final BufferedReader bufferedReader;

    public BufferedReaderIterable(BufferedReader bufferedReader) {
        this.bufferedReader = Objects.requireNonNull(bufferedReader);
    }

    @Override
    public Iterator<String> iterator() {
        return new IteratorImpl();
    }

    private class IteratorImpl implements Iterator<String> {

        private boolean endOfFile;
        private String nextLine;
        private boolean hasLookAhead;
        
        public IteratorImpl() {
            hasLookAhead = false;
        }
        
        private void lookAhead() {
            if (hasLookAhead) {
                return;
            }
            try {
                nextLine = bufferedReader.readLine();
                endOfFile = nextLine == null;
                hasLookAhead = true;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            
        }

        @Override
        public boolean hasNext() {
            lookAhead();
            return !endOfFile;
        }

        @Override
        public String next() {
            lookAhead();
            String line = nextLine;
            hasLookAhead = false;
            return line;
        }
    }
}
