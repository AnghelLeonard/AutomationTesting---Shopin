package org.shopin.admin;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TimeBasedOneTimePassword {

    private static String user;

    public TimeBasedOneTimePassword(final @Value("${admin.email}") String user) {
        TimeBasedOneTimePassword.user = user;
    }

    public static boolean isVerificationCodeValid(int code, String sc) throws VerificationCodeException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        boolean currentPrincipalRole = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRE_AUTH_ADMIN"));

        if (currentPrincipalName.equals(user) && currentPrincipalRole) {

            final String SHARED_SECRET = sc;
            final int VARIANCE = 5;

            boolean result = false;
            try {
                result = verifyCode(SHARED_SECRET, code, getTimeIndex(), VARIANCE);
            } catch (NoSuchAlgorithmException e) {
                throw new VerificationCodeException("Invalid algorithm");
            } catch (InvalidKeyException e) {
                throw new VerificationCodeException("Invalid key");
            }
            return result;
        }
        return false;
    }

    private static boolean verifyCode(final String secret, int code, final long timeIndex, final int variance)
            throws NoSuchAlgorithmException, InvalidKeyException {
        for (int i = -variance; i <= variance; i++) {
            if (getCode(secret, timeIndex + i) == code) {
                return true;
            }
        }
        return false;
    }

    private static long getCode(final String secret, final long timeIndex) throws InvalidKeyException, NoSuchAlgorithmException {
        return getCode(new Base32().decode(secret), timeIndex);
    }

    private static long getCode(final byte[] secret, final long timeIndex) throws NoSuchAlgorithmException, InvalidKeyException {

        SecretKeySpec signKey = new SecretKeySpec(secret, "HmacSHA1");
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(timeIndex);
        byte[] timeBytes = buffer.array();
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(timeBytes);
        int offset = hash[19] & 0xf;
        long truncatedHash = hash[offset] & 0x7f;
        for (int i = 1; i < 4; i++) {
            truncatedHash <<= 8;
            truncatedHash |= hash[offset + i] & 0xff;
        }
        return (truncatedHash %= 1000000);
    }

    private static long getTimeIndex() {
        return System.currentTimeMillis() / 1000 / 30;
    }

}
