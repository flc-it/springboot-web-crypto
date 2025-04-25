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

package org.flcit.springboot.web.crypto.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
@ConfigurationProperties("crypto.web")
public class CryptoWebConfiguration {

    private String domain;
    private String environment;

    /**
     * @return
     */
    public String getDomain() {
        return domain;
    }
    /**
     * @param domain
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }
    /**
     * @return
     */
    public String getEnvironment() {
        return environment;
    }
    /**
     * @param environment
     */
    public void setEnvironment(String environment) {
        this.environment = environment;
    }
}
