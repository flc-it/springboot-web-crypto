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

package org.flcit.springboot.web.crypto.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.flcit.springboot.commons.crypto.configuration.CryptoConfiguration;
import org.flcit.springboot.commons.crypto.service.CryptoService;
import org.flcit.springboot.web.crypto.configuration.CryptoWebConfiguration;

class GenerationUrlFrontServiceTest {

    private final CryptoWebConfiguration WebConfiguration = new CryptoWebConfiguration();
    private final CryptoConfiguration configuration = new CryptoConfiguration();
    private final CryptoService service;

    GenerationUrlFrontServiceTest() {
        WebConfiguration.setDomain("domain");
        WebConfiguration.setEnvironment("test");
        configuration.setKeyString("KEY");
        this.service = new CryptoService(configuration);
    }

    @Test
    void decodeUnsupportedEncodingExceptionTest() throws UnsupportedEncodingException {
        try (MockedStatic<URLDecoder> mock = mockStatic(URLDecoder.class)) {
            when(URLDecoder.decode(anyString(), anyString())).thenThrow(UnsupportedEncodingException.class);
            final GenerationUrlFrontService service = new GenerationUrlFrontService(this.service, this.WebConfiguration);
            final StringBuilder url = new StringBuilder("http://localhost/test/generate/url");
            assertThrows(IllegalStateException.class, () -> service.getGeneratedUrlObject(url, "id", false));
        }
    }

}