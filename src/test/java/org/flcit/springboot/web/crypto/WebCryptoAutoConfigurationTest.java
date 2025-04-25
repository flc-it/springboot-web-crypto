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

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.test.web.servlet.ResultActions;

import org.flcit.springboot.commons.crypto.CommonsCryptoAutoConfiguration;
import org.flcit.springboot.commons.test.util.ContextRunnerUtils;
import org.flcit.springboot.commons.test.util.MvcUtils;
import org.flcit.springboot.commons.test.util.PropertyTestUtils;
import org.flcit.springboot.commons.test.util.ResultActionsUtils;
import org.flcit.springboot.web.crypto.configuration.CryptoWebConfiguration;
import org.flcit.springboot.web.crypto.configuration.WebCryptoWebMvcConfiguration;
import org.flcit.springboot.web.crypto.service.GenerationUrlFrontService;

class WebCryptoAutoConfigurationTest {

    private static final String PATTERN_VALUE_CRYPTE_STRING = "[0-9a-zA-Z-_=]+";

    private static final String PREFIX_PROPERTY = "crypto.web.";
    private static final String DOMAIN = "domain";
    private static final String ENVIRONMENT = "test";

    private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(
                    JacksonAutoConfiguration.class,
                    WebMvcAutoConfiguration.class,
                    CommonsCryptoAutoConfiguration.class,
                    WebCryptoAutoConfiguration.class))
             .withPropertyValues(PropertyTestUtils.getValue("crypto.", "key-string", "web-crypto-lib@test-environment"));

    @Test
    void webCryptoAutoConfigurationDefaultOk() {
        ContextRunnerUtils.assertHasSingleBean(this.contextRunner, CryptoWebConfiguration.class);
        ContextRunnerUtils.assertDoesNotHaveBean(this.contextRunner, GenerationUrlFrontService.class, WebCryptoWebMvcConfiguration.class, CryptoAnnotationFormatterFactory.class);
    }

    @Test
    void webCryptoAutoConfigurationDefaultAnnotationOk() {
        ContextRunnerUtils.assertHasSingleBean(
                this.contextRunner.withPropertyValues(
                        PropertyTestUtils.getValue(PREFIX_PROPERTY, "annotation", "true")
                ),
                WebCryptoWebMvcConfiguration.class, CryptoAnnotationFormatterFactory.class);
    }

    @Test
    void webCryptoAutoConfigurationDefaultGenerationUrlNoDomainOk() {
        ContextRunnerUtils.assertHasSingleBean(
                this.contextRunner.withPropertyValues(
                        PropertyTestUtils.getValue(PREFIX_PROPERTY, "generation-url", "true")
                )
        );
    }

    @Test
    void webCryptoAutoConfigurationDefaultGenerationUrlOk() {
        ContextRunnerUtils.assertHasSingleBean(
                this.contextRunner.withPropertyValues(
                        PropertyTestUtils.getValue(PREFIX_PROPERTY, "generation-url", "true"),
                        PropertyTestUtils.getValue(PREFIX_PROPERTY, "domain", DOMAIN)
                ),
                GenerationUrlFrontService.class);
    }

    @Test
    void webCryptoAutoConfigurationTestAnnotationsOk() {
            this.contextRunner
            .withPropertyValues(
                    PropertyTestUtils.getValue(PREFIX_PROPERTY, "annotation", "true")
            )
            .withUserConfiguration(TestAnnotationsResource.class).run(
                    context -> {
                        final String[] fields = new String[] { "idString", "idInt", "idInteger", "idLongObject", "idLong", "idBigInteger" };
                        ResultActions result = MvcUtils.assertGetJsonResponse(context, TestAnnotationsResource.GET_CRYPTO_VALUE_PATH);
                        ResultActionsUtils.assertNotEmptyOrNullAndNotEqual(result, TestAnnotationsResource.VALUE_STRING, fields);
                        final Response response = MvcUtils.convert(context, result, Response.class);
                        MvcUtils.assertGetResponse(context, TestAnnotationsResource.TEST_ANNOTATION_PATH_VARIABLE_REQUEST_PATH, response.getIdString(), response.getIdInt(), response.getIdInteger(), response.getIdLongObject(), response.getIdLong(), response.getIdBigInteger());
                        MvcUtils.assertGetResponse(context, TestAnnotationsResource.TEST_ANNOTATION_REQUEST_PARAM_REQUEST_PATH, response.getIdString(), response.getIdInt(), response.getIdInteger(), response.getIdLongObject(), response.getIdLong(), response.getIdBigInteger());
                        MvcUtils.assertPostResponse(context, TestAnnotationsResource.TEST_ANNOTATION_BODY_PATH, response);

                        result = MvcUtils.assertGetJsonResponse(context, TestAnnotationsResource.GET_CRYPTO_VALIDITY_VALUE_PATH);
                        ResultActionsUtils.assertNotEmptyOrNullAndNotEqual(result, TestAnnotationsResource.VALUE_STRING, fields);
                        final Response responseValidity = MvcUtils.convert(context, result, Response.class);
                        MvcUtils.assertGetResponse(context, TestAnnotationsResource.TEST_ANNOTATION_VALIDITY_PATH_VARIABLE_REQUEST_PATH, responseValidity.getIdString(), responseValidity.getIdInt(), responseValidity.getIdInteger(), responseValidity.getIdLongObject(), responseValidity.getIdLong(), responseValidity.getIdBigInteger());
                        MvcUtils.assertGetResponse(context, TestAnnotationsResource.TEST_ANNOTATION_VALIDITY_REQUEST_PARAM_REQUEST_PATH, responseValidity.getIdString(), responseValidity.getIdInt(), responseValidity.getIdInteger(), responseValidity.getIdLongObject(), responseValidity.getIdLong(), responseValidity.getIdBigInteger());
                        MvcUtils.assertPostResponse(context, TestAnnotationsResource.TEST_ANNOTATION_VALIDITY_BODY_PATH, responseValidity);
                    }
            );
    }

    @Test
    void webCryptoAutoConfigurationTestNullValueAnnotationsOk() {
            this.contextRunner
            .withPropertyValues(
                    PropertyTestUtils.getValue(PREFIX_PROPERTY, "annotation", "true")
            )
            .withUserConfiguration(TestAnnotationsResource.class).run(
                    context -> {
                        final String[] fieldsNull = new String[] { "idString", "idInteger", "idLongObject", "idBigInteger" };
                        final String[] fieldsNotNull = new String[] { "idInt", "idLong" };
                        final ResultActions result = MvcUtils.assertGetJsonResponse(context, TestAnnotationsResource.GET_CRYPTO_NULL_VALUE_PATH);
                        ResultActionsUtils.assertEmptyOrNull(result, fieldsNull);
                        ResultActionsUtils.assertNotEmptyOrNullAndNotEqual(result, TestAnnotationsResource.VALUE_STRING, fieldsNotNull);
                    }
            );
    }

    @Test
    void webCryptoAutoConfigurationTestGenerationUrlOk() {
            this.contextRunner
            .withPropertyValues(
                    PropertyTestUtils.getValue(PREFIX_PROPERTY, "generation-url", "true"),
                    PropertyTestUtils.getValue(PREFIX_PROPERTY, "domain", DOMAIN),
                    PropertyTestUtils.getValue(PREFIX_PROPERTY, "environment", ENVIRONMENT)
            )
            .withUserConfiguration(TestGenerationUrlResource.class).run(
                    context -> {
                        ResultActionsUtils.assertNotEmptyOrNullAndMatchRegex(
                                MvcUtils.assertGetJsonResponse(context, TestGenerationUrlResource.TEST_GENERATE_URL_PATH),
                                getGeneratedUrl(TestGenerationUrlResource.TEST_GENERATE_URL_PATH),
                                "url");
                        ResultActionsUtils.assertNotEmptyOrNullAndMatchRegex(
                                MvcUtils.assertGetJsonResponse(context, TestGenerationUrlResource.TEST_GENERATE_URL_VALUE_PATH),
                                getGeneratedUrl(TestGenerationUrlResource.TEST_GENERATE_URL_PATH),
                                "url");
                        ResultActionsUtils.assertNotEmptyOrNullAndMatchRegex(
                                MvcUtils.assertGetJsonResponse(context, TestGenerationUrlResource.TEST_GENERATE_URL_NULL_PATH),
                                getGeneratedUrl(TestGenerationUrlResource.TEST_GENERATE_URL_NULL_PATH),
                                "url");
                    }
            );
    }

    private static final String getGeneratedUrl(String path) {
        return String.format("https://%s%s/%s", DOMAIN, path.replace("/generate/", "/generated/"), PATTERN_VALUE_CRYPTE_STRING);
    }

}
