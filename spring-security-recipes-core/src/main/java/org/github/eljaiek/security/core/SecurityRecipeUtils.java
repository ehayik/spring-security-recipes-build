package org.github.eljaiek.security.core;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

@UtilityClass
public class SecurityRecipeUtils {

    public static final String BASIC_PREFIX = "Basic ";

    public static final String BEARER_PREFIX = "Bearer ";

    @SneakyThrows
    public static String encode(String plaintext) {
        return Base64.encodeBase64URLSafeString(plaintext.getBytes("UTF-8"));
    }

    public static String decode(String encoded) {
        return StringUtils.newStringUtf8(Base64.decodeBase64(encoded));
    }
}
