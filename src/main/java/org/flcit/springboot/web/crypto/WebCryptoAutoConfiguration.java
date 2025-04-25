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

import javax.servlet.Servlet;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;
import org.flcit.springboot.commons.crypto.service.CryptoService;
import org.flcit.springboot.web.crypto.configuration.CryptoWebConfiguration;
import org.flcit.springboot.web.crypto.configuration.WebCryptoWebMvcConfiguration;
import org.flcit.springboot.web.crypto.service.GenerationUrlFrontService;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
@AutoConfiguration(before = WebMvcAutoConfiguration.class)
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class })
@EnableConfigurationProperties(CryptoWebConfiguration.class)
public class WebCryptoAutoConfiguration {

    /**
     * @param cryptoService
     * @param cryptoWebConfiguration
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "crypto.web", name = "generation-url")
    public GenerationUrlFrontService getGenerationUrlFrontService(CryptoService cryptoService, CryptoWebConfiguration cryptoWebConfiguration) {
        return new GenerationUrlFrontService(cryptoService, cryptoWebConfiguration);
    }

    /**
     * @param cryptoService
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "crypto.web", name = "annotation")
    public WebCryptoWebMvcConfiguration getWebCryptoWebMvcConfiguration(CryptoService cryptoService) {
        return new WebCryptoWebMvcConfiguration(this.getCryptoAnnotationFormatterFactory(cryptoService));
    }

    /**
     * @param cryptoService
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "crypto.web", name = "annotation")
    public CryptoAnnotationFormatterFactory getCryptoAnnotationFormatterFactory(CryptoService cryptoService) {
        return new CryptoAnnotationFormatterFactory(cryptoService);
    }

}
