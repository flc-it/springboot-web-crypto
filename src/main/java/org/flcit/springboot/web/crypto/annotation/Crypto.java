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

package org.flcit.springboot.web.crypto.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.flcit.springboot.web.crypto.converter.DefaultCryptoJsonConverter;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
@Retention(RUNTIME)
@Target({ TYPE, FIELD, PARAMETER, METHOD })
@JacksonAnnotationsInside
@JsonSerialize(using = DefaultCryptoJsonConverter.DefaultCryptoJsonSerializer.class)
@JsonDeserialize(using = DefaultCryptoJsonConverter.DefaultCryptoJsonDeserializer.class)
public @interface Crypto {

    /**
     * @return
     */
    boolean withValidity() default false;
    /**
     * @return
     */
    long timeValidity() default 0;

}
