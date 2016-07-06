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
package org.oneandone.ckjm.test.samples;

/*
 * ckjm test data
 *
 * $Id: \\dds\\src\\Research\\ckjm.RCS\\tests\\Test.java,v 1.2 2005/11/05 08:37:28 dds Exp $
 *
 */

import java.util.*;

/* NOC = 1, LCOM = 2 - 1 = 1 */
class Test {
    int fieldname_a;
    int fieldname_b;
    int fieldname_c;
    int fieldname_d;
    int fieldname_e;
    int fieldname_x;
    int fieldname_y;
    int fieldname_z;
    String fieldname_s1;
    String fieldname_s2[];
    Set<Integer> fieldname_s3;
    static int sa;

    Test() {
	System.out.println(
		fieldname_a + fieldname_b +
		fieldname_c + fieldname_d +
		fieldname_e);
    }

    int methodname_2(int i) {
	System.out.println("hi");
	return (fieldname_a + fieldname_b + fieldname_e);
    }

    AbstractCollection methodname_3(AbstractCollection a, ArrayList b[]) {
	System.out.println("hi");
	Integer i = new Integer(fieldname_x + fieldname_y + fieldname_z);
	return a;
    }
}
