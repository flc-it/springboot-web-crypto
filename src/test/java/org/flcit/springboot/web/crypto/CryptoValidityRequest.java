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

import org.flcit.springboot.web.crypto.annotation.Crypto;

class CryptoValidityRequest {

    @Crypto(withValidity = true)
    private String idString;

    @Crypto(withValidity = true)
    private int idInt;

    @Crypto(withValidity = true)
    private Integer idInteger;

    @Crypto(withValidity = true)
    private Long idLongObject;

    @Crypto(withValidity = true)
    private long idLong;

    @Crypto(withValidity = true)
    private BigInteger idBigInteger;

    CryptoValidityRequest() { }

    CryptoValidityRequest(int id) {
        this.idString = String.valueOf(id);
        this.idInt = id;
        this.idInteger = id;
        this.idLongObject = (long) id;
        this.idLong = (long) id;
        this.idBigInteger = BigInteger.valueOf(id);
    }

    public String getIdString() {
        return idString;
    }
    public void setIdString(String idString) {
        this.idString = idString;
    }
    public int getIdInt() {
        return idInt;
    }
    public void setIdInt(int idInt) {
        this.idInt = idInt;
    }
    public Integer getIdInteger() {
        return idInteger;
    }
    public void setIdInteger(Integer idInteger) {
        this.idInteger = idInteger;
    }
    public Long getIdLongObject() {
        return idLongObject;
    }
    public void setIdLongObject(Long idLongObject) {
        this.idLongObject = idLongObject;
    }
    public long getIdLong() {
        return idLong;
    }
    public void setIdLong(long idLong) {
        this.idLong = idLong;
    }
    public BigInteger getIdBigInteger() {
        return idBigInteger;
    }
    public void setIdBigInteger(BigInteger idBigInteger) {
        this.idBigInteger = idBigInteger;
    }

}
