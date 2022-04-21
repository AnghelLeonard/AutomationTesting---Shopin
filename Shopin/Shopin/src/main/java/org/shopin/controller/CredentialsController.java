package org.shopin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.shopin.exceptions.EmailNotFoundException;
import org.shopin.exceptions.TokenInvalidException;
import org.shopin.pojo.Email;
import org.shopin.pojo.NewPassword;
import org.shopin.service.dao.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialsController {

    @Autowired
    IUserService userService;

    @GetMapping("/activate/{token}")
    public String activateUser(final @PathVariable("token") String token) {

        userService.activateNewUser(token);

        return "redirect:/activate";
    }

    @GetMapping("/reset")
    public String resetShow(final Email email) {
        return "credentials/reset";
    }

    @PostMapping("/reset")
    public String resetPrerequisites(HttpServletRequest request,
            @Valid final Email email, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "credentials/reset";
        }

        try {
            userService.resetPasswordUser(email.getEmail());
        } catch (EmailNotFoundException e) {
            bindingResult.addError(new ObjectError("email",
                    "Sorry, we cannot find your e-mail"));
            return "credentials/reset";
        }

        return "redirect:/pending";
    }

    @GetMapping("/newpassword/{token}")
    public String newPasswordShow(final @PathVariable("token") String token,
            final @ModelAttribute(name = "newpassword", binding = false) NewPassword newpassword) {

        newpassword.setToken(token);
        return "credentials/newpassword";
    }

    @PostMapping("/newpassword")
    public String newPasswordPerform(final @Valid @ModelAttribute("newpassword") NewPassword newpassword,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "credentials/newpassword";
        }

        try {
            userService.newPasswordUser(newpassword);
        } catch (TokenInvalidException e) {
            bindingResult.addError(new FieldError(bindingResult.getObjectName(),
                    "reset", "Reset password doesn't match the password that we sent to you"));
            return "credentials/newpassword";
        }

        return "redirect:/activate";
    }
}
