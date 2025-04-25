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
import org.flcit.springboot.web.crypto.serializer.BaseCryptoJsonSerializer;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
public final class DefaultCryptoJsonConverter {

    private DefaultCryptoJsonConverter() { }

    /**
     * 
     * @since 
     * @author Florian Lestic
     */
    public static class DefaultCryptoJsonSerializer extends BaseCryptoJsonSerializer<Void> {

        protected DefaultCryptoJsonSerializer(CryptoService cryptoService) {
            super(cryptoService, Void.class);
        }

    }

    /**
     * 
     * @since 
     * @author Florian Lestic
     */
    public static class DefaultCryptoJsonDeserializer extends BaseCryptoJsonDeserializer<Void> {

        protected DefaultCryptoJsonDeserializer(CryptoService cryptoService) {
            super(cryptoService, Void.class);
        }

    }

}
