package org.shopin.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.shopin.model.User;
import org.shopin.pojo.NewPassword;

public class PasswordsValidator implements ConstraintValidator<PasswordsConstraint, Object> {

    @Override
    public void initialize(final PasswordsConstraint constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        if (obj instanceof User) {
            final User user = (User) obj;
            return user.getPassword().equals(user.getConfirm());
        }

        if (obj instanceof NewPassword) {
            final NewPassword newpassword = (NewPassword) obj;
            return newpassword.getPassword().equals(newpassword.getConfirm());
        }

        return false;
    }
}
