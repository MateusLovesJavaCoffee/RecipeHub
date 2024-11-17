package br.com.mateusulrich.recipeservice.common.utils;

import com.amazonaws.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5ChecksumUtils {

    private MD5ChecksumUtils() {}

    public static String calculateMD5Checksum(final byte[] content) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(content);
            return Base64.encodeAsString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }


}
