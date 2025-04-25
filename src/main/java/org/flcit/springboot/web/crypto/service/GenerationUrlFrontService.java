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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.flcit.commons.core.util.UriUtils;
import org.flcit.springboot.commons.crypto.service.CryptoService;
import org.flcit.springboot.web.crypto.configuration.CryptoWebConfiguration;
import org.flcit.springboot.web.crypto.dto.GeneratedUrlResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
@Service
public class GenerationUrlFrontService {

    private static final long TIME_VALIDITY_DEFAULT = 3 * 60 * 1000L;
    private final String domain;
    private final String protocol;
    private final boolean removePort;

    private final CryptoService cryptoService;

    /**
     * @param cryptoService
     * @param cryptoConfiguration
     */
    public GenerationUrlFrontService(CryptoService cryptoService, CryptoWebConfiguration cryptoConfiguration) {
        this.cryptoService = cryptoService;
        this.protocol = "https";
        this.domain = StringUtils.hasLength(cryptoConfiguration.getDomain()) ? cryptoConfiguration.getDomain() : null;
        this.removePort = true;
    }

    private String changeUrl(String url) {
        return UriUtils.changeUrl(url, protocol, domain, null, removePort);
    }

    private String getGeneratedUrlAppend(StringBuilder url, String id) {
        return changeUrl(url
                .append('/')
                .append(cryptoService.encryptToString(id != null ? decode(id) : id, TIME_VALIDITY_DEFAULT)).toString()
                .replace("/generate/", "/generated/"));
    }

    private String getGeneratedUrlReplace(StringBuilder url, String id) {
        return changeUrl(url.toString()
                .replace("/generate/", "/generated/")
                .replace(id, cryptoService.encryptToString(id, TIME_VALIDITY_DEFAULT)));
    }

    /**
     * @param url
     * @param id
     * @param exist
     * @return
     */
    public String getGeneratedUrl(StringBuilder url, String id, boolean exist) {
        return exist ? getGeneratedUrlReplace(url, id) : getGeneratedUrlAppend(url, id);
    }

    /**
     * @param url
     * @return
     */
    public GeneratedUrlResponse getGeneratedUrlObject(StringBuilder url) {
        return getGeneratedUrlObject(url, null, false);
    }

    /**
     * @param url
     * @param id
     * @param exist
     * @return
     */
    public GeneratedUrlResponse getGeneratedUrlObject(StringBuilder url, String id, boolean exist) {
        return new GeneratedUrlResponse(getGeneratedUrl(url, id, exist));
    }

    private static final String decode(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

}
