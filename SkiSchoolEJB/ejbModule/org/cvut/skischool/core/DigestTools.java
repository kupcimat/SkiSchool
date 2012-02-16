package org.cvut.skischool.core;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matej
 */
public class DigestTools {

    public static String SHA1(String text) {
        MessageDigest md = null;
        byte[] digestOutput = null;

        try {
            md = MessageDigest.getInstance("SHA1");
            md.update(text.getBytes("utf8"), 0, text.length());

            digestOutput = new byte[40];
            digestOutput = md.digest();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DigestTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DigestTools.class.getName()).log(Level.SEVERE, null, ex);
        }

        return convertToHex(digestOutput);
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }

        return buf.toString();
    }
}
