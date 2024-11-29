package org.example;

import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;


import java.util.Base64;

public class HmacUtils {
    private static final int KEY_LENGTH = 16; // Length of the key in bytes

    public static String generateHmac(String key, String message) {
        try {
            // Convert the key and message to bytes
            byte[] keyBytes = key.getBytes();
            byte[] messageBytes = message.getBytes();

            // Create HMAC instance with SHA3-256
            HMac hmac = new HMac(new SHA3Digest(256));
            hmac.init(new KeyParameter(keyBytes));

            // Process the message
            hmac.update(messageBytes, 0, messageBytes.length);
            byte[] result = new byte[hmac.getMacSize()];
            hmac.doFinal(result, 0);

            // Return Base64-encoded HMAC for readability
            return Base64.getEncoder().encodeToString(result);

        } catch (Exception e) {
            throw new RuntimeException("Error generating HMAC", e);
        }
    }
}
