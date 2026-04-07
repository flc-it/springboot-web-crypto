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

package org.flcit.springboot.web.crypto.serializer;

import java.util.HashMap;
import java.util.Map;

import org.flcit.springboot.commons.crypto.service.CryptoService;
import org.flcit.springboot.web.crypto.annotation.Crypto;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JacksonComponent;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.BeanProperty;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

/**
 * @param <T>
 * @since 
 * @author Florian Lestic
 */
@JacksonComponent
public class BaseCryptoJsonSerializer<T> extends ValueSerializer<T> implements BeanFactoryAware {

    private static final Map<Class<?>, BaseCryptoJsonSerializer<?>> SERIALIZER = new HashMap<>(1);

    @Autowired
    private CryptoService cryptoService;

    private final Class<T> handledType;
    private final Long timeValidity;

    @SuppressWarnings("unchecked")
    public BaseCryptoJsonSerializer(CryptoService cryptoService) {
        this(cryptoService, (Class<T>) Void.class, null);
    }

    /**
     * @param cryptoService
     * @param handledType
     */
    public BaseCryptoJsonSerializer(CryptoService cryptoService, Class<T> handledType) {
        this(cryptoService, handledType, null);
    }

    private BaseCryptoJsonSerializer(CryptoService cryptoService, Class<T> handledType, Long timeValidity) {
        this.cryptoService = cryptoService;
        this.handledType = handledType;
        this.timeValidity = timeValidity;
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
    public void serialize(T value, JsonGenerator gen, SerializationContext serializers) {
        gen.writeString(timeValidity != null ? cryptoService.encryptToString(toString(value), timeValidity) : cryptoService.encryptToString(toString(value)));
    }

    private String toString(T value) {
        return String.valueOf(value);
    }

    /**
     *
     */
    @Override
    public ValueSerializer<?> createContextual(SerializationContext ctxt, BeanProperty property) {
        final long dureeValidityInMilliseconds = property.getAnnotation(Crypto.class).timeValidity();
        if (dureeValidityInMilliseconds <= 0) {
            BaseCryptoJsonSerializer<?> serializer = SERIALIZER.get(Void.class);
            if (serializer == null) {
                serializer = new BaseCryptoJsonSerializer<>(cryptoService, Void.class);
                SERIALIZER.put(Void.class, serializer);
            }
            return serializer;
        } else {
            return new BaseCryptoJsonSerializer<>(cryptoService, Void.class, dureeValidityInMilliseconds);
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("TEST");
    }

}