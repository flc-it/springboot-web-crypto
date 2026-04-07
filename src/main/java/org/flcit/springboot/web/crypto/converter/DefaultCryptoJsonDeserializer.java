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

package org.flcit.springboot.web.crypto.converter;

import org.flcit.springboot.commons.crypto.service.CryptoService;
import org.flcit.springboot.web.crypto.deserializer.BaseCryptoJsonDeserializer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.jackson.JacksonComponent;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
@JacksonComponent
public class DefaultCryptoJsonDeserializer extends BaseCryptoJsonDeserializer<Void> implements BeanFactoryAware {

    public DefaultCryptoJsonDeserializer() {
        super(null, Void.class);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public DefaultCryptoJsonDeserializer(CryptoService cryptoService) {
        super(cryptoService, Void.class);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("TEST");
    }

}
