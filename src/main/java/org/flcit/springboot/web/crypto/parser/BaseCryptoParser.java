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

package org.flcit.springboot.web.crypto.parser;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Parser;

import org.flcit.commons.core.util.ReflectionUtils;
import org.flcit.springboot.commons.crypto.service.CryptoService;

/**
 * @param <T>
 * @since 
 * @author Florian Lestic
 */
abstract class BaseCryptoParser<T> implements Parser<T> {

    private final Class<T> classType;
    protected final CryptoService cryptoService;

    BaseCryptoParser(Class<T> classType, CryptoService cryptoService) {
        this.classType = classType;
        this.cryptoService = cryptoService;
    }

    abstract String decrypt(String text);

    /**
     *
     */
    @Override
    public T parse(String text, Locale locale) throws ParseException {
        return ReflectionUtils.convert(decrypt(text), classType);
    }

}
