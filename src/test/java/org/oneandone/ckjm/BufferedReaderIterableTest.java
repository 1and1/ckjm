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
package org.oneandone.ckjm;

import gr.spinellis.ckjm.BufferedReaderIterable;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test for {@link BufferedReaderIterable}.
 * @author Stephan Fuhrmann
 */
public class BufferedReaderIterableTest {
    
    @Test
    public void testInit() {
        new BufferedReaderIterable(new BufferedReader(new StringReader("")));
    }
    
    @Test(expected = NullPointerException.class)
    public void testInitWithNull() {
        new BufferedReaderIterable(null);
    }
    
    @Test
    public void testIterator() {
        Iterable<String> iterable = new BufferedReaderIterable(
            new BufferedReader(
                new StringReader("foo\nbar\nbaz\n")));
        assertNotNull(iterable.iterator());
    }
    
    @Test
    public void testIteratorResults() {
        Iterable<String> iterable = new BufferedReaderIterable(
            new BufferedReader(
                new StringReader("foo\nbar\nbaz\n")));
        Iterator<String> iterator = iterable.iterator();
        
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
        assertEquals("foo", iterator.next());
        
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
        assertEquals("bar", iterator.next());
        
        assertTrue(iterator.hasNext());
        assertEquals("baz", iterator.next());        

        assertFalse(iterator.hasNext());
        assertFalse(iterator.hasNext());
        assertFalse(iterator.hasNext());
    }
}
