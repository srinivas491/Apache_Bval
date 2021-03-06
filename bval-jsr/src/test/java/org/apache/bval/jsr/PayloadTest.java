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
package org.apache.bval.jsr;

import junit.framework.TestCase;
import org.apache.bval.jsr.util.TestUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Payload;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import java.util.Locale;
import java.util.Set;

/**
 * Description: test that payload information can be retrieved
 * from error reports via the ConstraintDescriptor either accessed
 * through the ConstraintViolation objects<br/>
 */
public class PayloadTest extends TestCase {
    static ValidatorFactory factory;

    static {
        factory = Validation.buildDefaultValidatorFactory();
        ((DefaultMessageInterpolator) factory.getMessageInterpolator()).setLocale(Locale.ENGLISH);
    }

    /**
     * Validator instance to test
     */
    protected Validator validator;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        validator = createValidator();
    }

    /**
     * Create the validator instance.
     * 
     * @return Validator
     */
    protected Validator createValidator() {
        return factory.getValidator();
    }

    static class Severity {
        static class Info implements Payload {
        }

        static class Error implements Payload {
        }
    }

    static class Address {
        private String zipCode;
        private String city;

        Address(String zipCode, String city) {
            this.zipCode = zipCode;
            this.city = city;
        }

        @NotNull(message = "would be nice if we had one", payload = Severity.Info.class)
        public String getZipCode() {
            return zipCode;
        }

        @NotNull(message = "the city is mandatory", payload = Severity.Error.class)
        public String getCity() {
            return city;
        }
    }

    public void testPayload() {
        Set<ConstraintViolation<Address>> violations;
        Address address = new Address(null, null);
        violations = validator.validate(address);
        assertEquals(2, violations.size());
        ConstraintViolation<?> vio;
        vio = TestUtils.getViolation(violations, "zipCode");
        assertNotNull(vio);
        assertEquals(1, vio.getConstraintDescriptor().getPayload().size());
        assertTrue(
              vio.getConstraintDescriptor().getPayload().contains(Severity.Info.class));

        vio = TestUtils.getViolation(violations, "city");
        assertNotNull(vio);
        assertEquals(1, vio.getConstraintDescriptor().getPayload().size());
        assertTrue(
              vio.getConstraintDescriptor().getPayload().contains(Severity.Error.class));
    }
}
