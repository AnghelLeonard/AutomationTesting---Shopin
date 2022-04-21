package org.shopin.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.shopin.util.CryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class NotificationServiceImpl implements INotificationService {

    @Value("${admin.email}")
    private String adminemail;

    @Value("${app.email}")
    private String appemail;

    @Value("${org.error.code}")
    private String code;

    @Value("${org.security.url.activate}")
    private String activate;

    @Value("${org.security.url.newpassword}")
    private String newpassword;

    @Value("${products.image.root}")
    private String path;

    @Value("${number.in.range}")
    private int rnd;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy-HH:mm");

    @Override
    public void sendUserPendingRegistrationEmail(final String address) {

        final String expiration = LocalDateTime.now().plusDays(1).format(formatter);
        final String token = CryptUtils.encrypt(address + " " + expiration);

        if (token != null) {
            final Context ctx = new Context();
            ctx.setVariable("name", address);
            ctx.setVariable("expiration", expiration);
            ctx.setVariable("activationlink", activate + token + "?rnd.=" + rnd);

            final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            try {
                message.setSubject("Shopin - Registration Pending");
                message.setFrom(appemail);
                message.setTo(address);

                final String htmlContent = this.templateEngine.process("mail/html/pending-email", ctx);
                message.setText(htmlContent, true);
            } catch (MessagingException ex) {
                throw new RuntimeException(code + ":Unable to build a message for: "
                        + address + "  |  " + ex.getMessage(), ex);
            }

            try {
                this.javaMailSender.send(mimeMessage);
            } catch (MailSendException e) {
                throw new RuntimeException(code + ":Cannot send e-mail to address: " + address, e);
            }
        }
    }

    @Override
    public void sendUserResetPasswordEmail(final String address, final Object payload) {

        final String expiration = LocalDateTime.now().plusDays(1).format(formatter);
        final String token = CryptUtils.encrypt(address + " "
                + payload + " " + expiration);

        if (token != null) {
            final Context ctx = new Context();
            ctx.setVariable("name", address);
            ctx.setVariable("expiration", expiration);
            ctx.setVariable("resetPassword", String.valueOf(payload));
            ctx.setVariable("resetLink", newpassword + token + "?rnd.=" + rnd);

            final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            try {
                message.setSubject("Shopin - Reset Password");
                message.setFrom(appemail);
                message.setTo(address);

                final String htmlContent = this.templateEngine.process("mail/html/reset-email", ctx);
                message.setText(htmlContent, true);
            } catch (MessagingException ex) {
                throw new RuntimeException(code + ":Unable to build a message for: "
                        + address + "  |  " + ex.getMessage(), ex);
            }

            try {
                this.javaMailSender.send(mimeMessage);
            } catch (MailSendException e) {
                throw new RuntimeException(code + ":Cannot send e-mail to address: " + address, e);
            }
        }
    }

    @Override
    public void sendAdminErrorMessage(final String payload) {

        final Context ctx = new Context();
        ctx.setVariable("data", payload);

        final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            message.setSubject("Shopin - Admin Alert");
            message.setFrom(appemail);
            message.setTo(adminemail);

            final String htmlContent = this.templateEngine.process("mail/html/malicious-email", ctx);
            message.setText(htmlContent, true);
        } catch (MessagingException ex) {
            throw new RuntimeException(code + ":Unable to build a message for: "
                    + adminemail + "  |  " + ex.getMessage(), ex);
        }

        try {
            this.javaMailSender.send(mimeMessage);
        } catch (MailSendException e) {
            throw new RuntimeException(code + ":Cannot send e-mail to address: " + adminemail, e);
        }
    }

    @Override
    public void sendOrderMessage(String payload) {

        final Context ctx = new Context();

        String[] data = payload.split("\\|");

        ctx.setVariable("nr", "#" + data[1]);
        ctx.setVariable("email", data[2]);
        ctx.setVariable("order", computeOrder(data[3], data[6], data[7]));
        ctx.setVariable("addressl", computeSendAddress(data[4]));
        ctx.setVariable("addressf", computePaymentAddress(data[4], data[5]));

        final MimeMessage mimeMessage_admin = this.javaMailSender.createMimeMessage();
        final MimeMessage mimeMessage_user = this.javaMailSender.createMimeMessage();
        final MimeMessageHelper message_admin = new MimeMessageHelper(mimeMessage_admin, "UTF-8");
        final MimeMessageHelper message_user = new MimeMessageHelper(mimeMessage_user, "UTF-8");
        try {
            message_admin.setSubject("Shopin - New Order");
            message_admin.setFrom(appemail);
            message_admin.setTo(adminemail);

            message_user.setSubject("Shopin - New Order");
            message_user.setFrom(appemail);
            message_user.setTo(data[2]);

            final String htmlContent = this.templateEngine.process("mail/html/order-email", ctx);
            message_admin.setText(htmlContent, true);
            message_user.setText(htmlContent, true);
        } catch (MessagingException ex) {
            throw new RuntimeException(code + ":Unable to build a message for: "
                    + adminemail + "|" + data[2] + "  |  " + ex.getMessage(), ex);
        }

        try {
            this.javaMailSender.send(mimeMessage_admin);
            this.javaMailSender.send(mimeMessage_user);
        } catch (MailSendException e) {
            throw new RuntimeException(code + ":Cannot send e-mail to address: " + adminemail + "|" + data[2], e);
        }
    }

    private String computePaymentAddress(final String addressf, final String need) {
        String address = "<table>";
        if (need.equals("2")) {

            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode addressItems = mapper.readTree(addressf);

                address = address + "<tr><td>Nume: </td><td>" + addressItems.get("ncf").asText() + "</td></tr>";
                address = address + "<tr><td>Adresa: </td><td>" + addressItems.get("addrf").asText() + "</td></tr>";
                address = address + "<tr><td>Telefon: </td><td>" + addressItems.get("telf").asText() + "</td></tr>";
                address = address + "<tr><td>Judet: </td><td>" + addressItems.get("judetf").asText() + "</td></tr>";
                address = address + "<tr><td>Oras: </td><td>" + addressItems.get("orasf").asText() + "</td></tr>";
                address = address + "<tr><td>Cod postal: </td><td>" + addressItems.get("codf").asText() + "</td></tr>";
                address = address + "<tr><td>Precizari: </td><td>" + addressItems.get("infof").asText() + "</td></tr>";

                address = address + "</table>";

                return address;
            } catch (Exception ex) {
                sendAdminErrorMessage(addressf);
            }
            return "Ne pare rau, a aparut o eroare la procesarea adresei de facturare!";

        } else {
            return "La fel ca adresa de livrare.";
        }
    }

    private String computeSendAddress(final String addressl) {
        String address = "<table>";
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode addressItems = mapper.readTree(addressl);

            address = address + "<tr><td>Nume: </td><td>" + addressItems.get("ncl").asText() + "</td></tr>";
            address = address + "<tr><td>Adresa: </td><td>" + addressItems.get("addrl").asText() + "</td></tr>";
            address = address + "<tr><td>Telefon: </td><td>" + addressItems.get("tell").asText() + "</td></tr>";
            address = address + "<tr><td>Judet: </td><td>" + addressItems.get("judetl").asText() + "</td></tr>";
            address = address + "<tr><td>Oras: </td><td>" + addressItems.get("orasl").asText() + "</td></tr>";
            address = address + "<tr><td>Cod postal: </td><td>" + addressItems.get("codl").asText() + "</td></tr>";
            address = address + "<tr><td>Precizari: </td><td>" + addressItems.get("infol").asText() + "</td></tr>";

            address = address + "</table>";

            return address;
        } catch (Exception ex) {
            sendAdminErrorMessage(addressl);
        }
        return "Ne pare rau, a aparut o eroare la procesarea adresei de livrare!";
    }

    private String computeOrder(final String order, final String namesimg, final String total) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode orderItems = mapper.readTree(order);
            JsonNode namesimgItems = mapper.readTree(namesimg);

            Iterator it = orderItems.elements();

            String products = "<table>";

            while (it.hasNext()) {
                JsonNode node = (JsonNode) it.next();

                products = products + "<tr>";
                products = products + "<td colspan='3'>";
                products = products + "<hr/>Cod produs: <strong>" + node.get("msku").asText() + "</strong><hr/>";
                products = products + "</td></tr><tr>";
                products = products + "<td>";
                products = products + "<img src='" + path + (namesimgItems.findValue(node.get("msku").asText())).get("eimage").asText() + node.get("msku").asText() + "/" + "thumb_shopin.jpg' />";
                products = products + "</td><td>";
                products = products + (namesimgItems.findValue(node.get("msku").asText())).get("ename").asText() + "<br/>";
                products = products + "Masura: " + node.get("msize").asText().substring(0, node.get("msize").asText().length() - 2) + "<br/>";
                products = products + "Cantitate: " + node.get("quantity").asText() + "<br/>";
                products = products + "</td><td style='padding-left:10px;'>";
                products = products + "Pret: " + node.get("price").asText() + " RON";
                products = products + "</td>";
                products = products + "</tr>";
            }

            products = products + "<tr><td colspan='3'>";
            products = products + "<hr/>Total: <strong>" + total + "</strong> RON";
            products = products + "</td></tr>";
            products = products + "</table>";

            return products;
        } catch (Exception ex) {
            sendAdminErrorMessage(order + "|" + namesimg);
        }
        return "Ne pare rau, a aparut o eroare la procesarea comenzii dvs.!";
    }
}
