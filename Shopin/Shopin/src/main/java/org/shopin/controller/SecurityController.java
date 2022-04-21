package org.shopin.controller;

import com.github.mkopylec.recaptcha.validation.RecaptchaValidator;
import com.github.mkopylec.recaptcha.validation.ValidationResult;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.shopin.exceptions.UserAlreadyExistException;
import org.shopin.global.data.CategoryModel;
import org.shopin.model.User;
import org.shopin.service.dao.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SecurityController extends CategoryModel {

    @Autowired
    IUserService userService;

    @Autowired
    private RecaptchaValidator recaptchaValidator;

    @GetMapping("/login")
    public String login() {
        return "credentials/login";
    }

    @GetMapping("/register")
    public String registrationShow(final User user) {
        return "credentials/register";
    }

    @PostMapping("/register")
    public String registrationPerform(HttpServletRequest request,
            @Valid final User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "credentials/register";
        }

        ValidationResult result = recaptchaValidator.validate(request);
        if (!result.isSuccess()) {
            bindingResult.addError(new ObjectError(bindingResult.getObjectName(),
                    "Please check the 'I am not a robot' verification"));
            return "credentials/register";
        }

        try {
            userService.registerNewUser(user);
        } catch (UserAlreadyExistException e) {
            bindingResult.addError(new FieldError(bindingResult.getObjectName(),
                    "email", "This e-mail already exist. Please use another e-mail address."));
            return "credentials/register";
        }

        return "redirect:/pending";
    }

}
