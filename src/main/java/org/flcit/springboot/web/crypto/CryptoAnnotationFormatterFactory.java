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

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.flcit.springboot.commons.crypto.service.CryptoService;
import org.flcit.springboot.web.crypto.annotation.Crypto;
import org.flcit.springboot.web.crypto.parser.SimpleCryptoParser;
import org.flcit.springboot.web.crypto.parser.ValidityCryptoParser;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
@Configuration
public class CryptoAnnotationFormatterFactory implements AnnotationFormatterFactory<Crypto> {

    private final CryptoService cryptoService;

    private final Set<Class<?>> fieldTypes = new HashSet<>(Arrays.asList(BigInteger.class, Long.class, long.class, Integer.class, int.class, String.class));

    private final Map<Class<?>, SimpleCryptoParser<?>> simpleParsers = new HashMap<>(1);
    private final Map<Class<?>, ValidityCryptoParser<?>> validityParsers = new HashMap<>(1);

    protected CryptoAnnotationFormatterFactory(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    /**
     *
     */
    @Override
    public Set<Class<?>> getFieldTypes() {
        return fieldTypes;
    }

    /**
     *
     */
    @Override
    public Printer<?> getPrinter(Crypto annotation, Class<?> fieldType) {
        return null;
    }

    /**
     *
     */
    @Override
    public Parser<?> getParser(Crypto annotation, Class<?> fieldType) {
        Parser<?> parser = getParsers(annotation).get(fieldType);
        if (parser == null) {
            parser = annotation.withValidity() ? new ValidityCryptoParser<>(fieldType, cryptoService) : new SimpleCryptoParser<>(fieldType, cryptoService);
            getParsers(annotation).put(fieldType, parser);
        }
        return parser;
    }

    @SuppressWarnings("unchecked")
    private <T extends Parser<?>> Map<Class<?>, T> getParsers(Crypto annotation) {
        return annotation.withValidity() ? (Map<Class<?>, T>) validityParsers : (Map<Class<?>, T>) simpleParsers;
    }

}
