/*
 * Copyright 2002-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.flcit.springboot.web.crypto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.lang.annotation.Annotation;

import org.junit.jupiter.api.Test;

import org.flcit.springboot.web.crypto.annotation.Crypto;

class CryptoAnnotationFormatterFactoryTest {

    @Test
    void getPrinterIsNull() {
        assertNull(new CryptoAnnotationFormatterFactory(null).getPrinter(null, null));
    }

    @Test
    void getParserTest() {
        final CryptoAnnotationFormatterFactory factory = new CryptoAnnotationFormatterFactory(null);
        final Crypto crypto = new Crypto() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return Crypto.class;
            }

            @Override
            public boolean withValidity() {
                return false;
            }

            @Override
            public long timeValidity() {
                return 0;
            }
        };
        assertEquals(factory.getParser(crypto, Void.class), factory.getParser(crypto, Void.class));
    }

}
