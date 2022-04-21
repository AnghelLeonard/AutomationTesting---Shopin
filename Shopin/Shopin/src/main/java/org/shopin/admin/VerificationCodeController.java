package org.shopin.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.apache.commons.codec.binary.Base32;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/x10qerK0")
public class VerificationCodeController {

    @Value("${admin.email}")
    private String user;

    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationCodeController.class);

    @PreAuthorize("hasRole('PRE_AUTH_ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public String verifyCode(final String code, final String sc) {
        String view = "redirect:/x10qerK0?error";
        try {
            if (TimeBasedOneTimePassword.isVerificationCodeValid(Integer.parseInt(code), sc)) {
                grantAuthority();
                view = "redirect:/x10qerK0/master";
            }
        } catch (NumberFormatException e) {
            // ignore
        } catch (VerificationCodeException e) {
            LOGGER.error("Error while verifying verification code {}", e.getMessage());
        }
        return view;
    }

    @PreAuthorize("hasRole('PRE_AUTH_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public String showVerificationCodeForm(Model model) {

        String secret = secretGenerator();
        model.addAttribute("qr", getQRBarcodeURL(user, secret));
        model.addAttribute("sc", secret);

        return "admin/x10qerK0";
    }

    private void grantAuthority() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities = new ArrayList<>(auth.getAuthorities());
        authorities.add(new SimpleGrantedAuthority("ROLE_FULL_AUTH_ADMIN"));
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    private static String getQRBarcodeURL(final String user, final String secret) {
        return "http://chart.googleapis.com/chart?chs=200x200&chld=M%7C0&cht=qr&chl=otpauth%3A%2F%2Ftotp%2F" + user + "%3Fsecret%3D" + secret;
    }

    private static String secretGenerator() {

        byte[] buffer = new byte[10 + 5 * 8];

        new Random().nextBytes(buffer);

        Base32 codec = new Base32();
        byte[] secretKey = Arrays.copyOf(buffer, 20);
        byte[] bEncodedKey = codec.encode(secretKey);
        String encodedKey = new String(bEncodedKey);
        return encodedKey;
    }
}
