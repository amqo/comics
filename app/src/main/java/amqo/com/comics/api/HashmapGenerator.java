package amqo.com.comics.api;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import amqo.com.comics.PrivateConstants;

public class HashmapGenerator {

    public String getHashForTimestamp(String timestamp) {

        String root = timestamp +
                PrivateConstants.MARVEL_PRIVATE_KEY +
                PrivateConstants.MARVEL_PUBLIC_KEY;

        return generateMD5(root);
    }

    private String generateMD5(String root) {

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(root.getBytes());

            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);

            for (int i = 0; i < len; i++) {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
