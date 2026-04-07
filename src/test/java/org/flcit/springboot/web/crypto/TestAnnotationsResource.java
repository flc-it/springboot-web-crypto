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

import java.math.BigInteger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.flcit.springboot.web.crypto.annotation.Crypto;

@RestController
@RequestMapping
class TestAnnotationsResource {

    private static final int VALUE = 5;
    static final String VALUE_STRING = String.valueOf(VALUE);

    static final String GET_CRYPTO_VALUE_PATH = "/get-crypto-value";
    static final String GET_CRYPTO_VALIDITY_VALUE_PATH = "/get-crypto-validity-value";
    static final String GET_CRYPTO_NULL_VALUE_PATH = "/get-crypto-null-value";

    static final String TEST_ANNOTATION_PATH_VARIABLE_PATH = "/test-annotation-path-variable";
    static final String TEST_ANNOTATION_PATH_VARIABLE_REQUEST_PATH = TEST_ANNOTATION_PATH_VARIABLE_PATH +"/{idString}/{idInt}/{idInteger}/{idLongObject}/{idLong}/{idBigInteger}";
    static final String TEST_ANNOTATION_VALIDITY_PATH_VARIABLE_PATH = "/test-annotation-validity-path-variable";
    static final String TEST_ANNOTATION_VALIDITY_PATH_VARIABLE_REQUEST_PATH = TEST_ANNOTATION_VALIDITY_PATH_VARIABLE_PATH +"/{idString}/{idInt}/{idInteger}/{idLongObject}/{idLong}/{idBigInteger}";

    static final String TEST_ANNOTATION_REQUEST_PARAM_PATH = "/test-annotation-request-param";
    static final String TEST_ANNOTATION_REQUEST_PARAM_REQUEST_PATH = TEST_ANNOTATION_REQUEST_PARAM_PATH + "?idString={idString}&idInt={idInt}&idInteger={idInteger}&idLongObject={idLongObject}&idLong={idLong}&idBigInteger={idBigInteger}";
    static final String TEST_ANNOTATION_VALIDITY_REQUEST_PARAM_PATH = "/test-annotation-validity-request-param";
    static final String TEST_ANNOTATION_VALIDITY_REQUEST_PARAM_REQUEST_PATH = TEST_ANNOTATION_VALIDITY_REQUEST_PARAM_PATH + "?idString={idString}&idInt={idInt}&idInteger={idInteger}&idLongObject={idLongObject}&idLong={idLong}&idBigInteger={idBigInteger}";

    static final String TEST_ANNOTATION_BODY_PATH = "/test-annotation-body";
    static final String TEST_ANNOTATION_VALIDITY_BODY_PATH = "/test-annotation-validity-body";

    @GetMapping(GET_CRYPTO_VALUE_PATH)
    public CryptoResponse getCryptoValue() {
        return new CryptoResponse(VALUE);
    }

    @GetMapping(GET_CRYPTO_VALIDITY_VALUE_PATH)
    public CryptoValidityResponse getCryptoValidityValue() {
        return new CryptoValidityResponse(VALUE);
    }

    @GetMapping(GET_CRYPTO_NULL_VALUE_PATH)
    public CryptoResponse getCryptoNullValue() {
        return new CryptoResponse();
    }

    @GetMapping(TEST_ANNOTATION_PATH_VARIABLE_REQUEST_PATH)
    public void testAnnotationPathVariable(
            @PathVariable("idString") @Crypto String idString,
            @PathVariable("idInt") @Crypto int idInt,
            @PathVariable("idInteger") @Crypto Integer idInteger,
            @PathVariable("idLongObject") @Crypto Long idLongObject,
            @PathVariable("idLong") @Crypto long idLong,
            @PathVariable("idBigInteger") @Crypto BigInteger idBigInteger) {
    }

    @GetMapping(TEST_ANNOTATION_VALIDITY_PATH_VARIABLE_REQUEST_PATH)
    public void testAnnotationValidityPathVariable(
            @PathVariable("idString") @Crypto(withValidity = true) String idString,
            @PathVariable("idInt") @Crypto(withValidity = true) int idInt,
            @PathVariable("idInteger") @Crypto(withValidity = true) Integer idInteger,
            @PathVariable("idLongObject") @Crypto(withValidity = true) Long idLongObject,
            @PathVariable("idLong") @Crypto(withValidity = true) long idLong,
            @PathVariable("idBigInteger") @Crypto(withValidity = true) BigInteger idBigInteger) {
    }

    @GetMapping(TEST_ANNOTATION_REQUEST_PARAM_PATH)
    public void testAnnotationRequestParam(
            @RequestParam("idString") @Crypto String idString,
            @RequestParam("idInt") @Crypto int idInt,
            @RequestParam("idInteger") @Crypto Integer idInteger,
            @RequestParam("idLongObject") @Crypto Long idLongObject,
            @RequestParam("idLong") @Crypto long idLong,
            @RequestParam("idBigInteger") @Crypto BigInteger idBigInteger) {
    }

    @GetMapping(TEST_ANNOTATION_VALIDITY_REQUEST_PARAM_PATH)
    public void testAnnotationValidityRequestParam(
            @RequestParam("idString") @Crypto(withValidity = true) String idString,
            @RequestParam("idInt") @Crypto(withValidity = true) int idInt,
            @RequestParam("idInteger") @Crypto(withValidity = true) Integer idInteger,
            @RequestParam("idLongObject") @Crypto(withValidity = true) Long idLongObject,
            @RequestParam("idLong") @Crypto(withValidity = true) long idLong,
            @RequestParam("idBigInteger") @Crypto(withValidity = true) BigInteger idBigInteger) {
    }

    @PostMapping(TEST_ANNOTATION_BODY_PATH)
    public void testAnnotationBody(@RequestBody CryptoResponse cryptoResponse) {
    }

    @PostMapping(TEST_ANNOTATION_VALIDITY_BODY_PATH)
    public void testAnnotationValidityBody(@RequestBody CryptoValidityResponse cryptoValidityResponse) {
    }

}
