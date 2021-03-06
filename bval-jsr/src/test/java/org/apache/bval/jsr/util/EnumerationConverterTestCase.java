/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.bval.jsr.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.beanutils.Converter;

/**
 * EnumerationConverter tester.
 *
 * $Id: EnumerationConverterTestCase.java 1161648 2011-08-25 17:14:15Z romanstumm $
 */
public final class EnumerationConverterTestCase extends TestCase {

    public EnumerationConverterTestCase(String name) {
        super(name);
    }

    public void testEnum() {
        Converter converter = EnumerationConverter.getInstance();

        Thread.State expected = Thread.State.TERMINATED;
        Thread.State actual = (Thread.State) converter.convert(Thread.State.class,
                Thread.State.TERMINATED.name());
        assertEquals(expected, actual);
    }

    public static Test suite() {
        return new TestSuite(EnumerationConverterTestCase.class);
    }

}
