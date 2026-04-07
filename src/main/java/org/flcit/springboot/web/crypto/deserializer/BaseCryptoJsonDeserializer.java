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

package org.flcit.springboot.web.crypto.deserializer;

import java.util.HashMap;
import java.util.Map;

import org.flcit.commons.core.util.ReflectionUtils;
import org.flcit.springboot.commons.crypto.service.CryptoService;
import org.flcit.springboot.web.crypto.annotation.Crypto;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JacksonComponent;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.BeanProperty;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

/**
 * @param <T>
 * @since 
 * @author Florian Lestic
 */
@JacksonComponent
public class BaseCryptoJsonDeserializer<T> extends ValueDeserializer<T> implements BeanFactoryAware {

    private static final Map<Class<?>, ValueDeserializer<?>> DESERIALIZER = new HashMap<>(1);
    private static final Map<Class<?>, ValueDeserializer<?>> DESERIALIZER_VALIDITY = new HashMap<>(1);

    @Autowired
    private CryptoService cryptoService;

    private final Class<T> handledType;
    private final boolean validity;

    @SuppressWarnings("unchecked")
    public BaseCryptoJsonDeserializer(CryptoService cryptoService) {
        this(cryptoService, (Class<T>) Void.class, false);
    }

    /**
     * @param cryptoService
     * @param handledType
     */
    public BaseCryptoJsonDeserializer(CryptoService cryptoService, Class<T> handledType) {
        this(cryptoService, handledType, false);
    }

    private BaseCryptoJsonDeserializer(CryptoService cryptoService, Class<T> handledType, boolean validity) {
        this.cryptoService = cryptoService;
        this.handledType = handledType;
        this.validity = validity;
    }

    /**
     *
     */
    @Override
    public Class<T> handledType() {
        return this.handledType;
    }

    /**
     *
     */
    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) {
        return ReflectionUtils.convert(validity ? cryptoService.decryptWithValidity(p.getString()) : cryptoService.decrypt(p.getString()), handledType);
    }

    /**
     *
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public ValueDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        final Class<?> classResponse = property.getType().getRawClass();
        final long timeValidity = property.getAnnotation(Crypto.class).timeValidity();
        boolean hasValidity = property.getAnnotation(Crypto.class).withValidity();
        hasValidity |= timeValidity > 0;
        ValueDeserializer<?> deserializer = (hasValidity ? DESERIALIZER_VALIDITY : DESERIALIZER).get(classResponse);
        if (deserializer == null) {
            deserializer = new BaseCryptoJsonDeserializer(this.cryptoService, classResponse, hasValidity);
            (hasValidity ? DESERIALIZER_VALIDITY : DESERIALIZER).put(classResponse, deserializer);
        }
        return deserializer;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("TEST");
    }

}