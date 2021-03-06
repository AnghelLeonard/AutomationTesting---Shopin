package org.shopin.validation;

import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.RepeatCharacterRegexRule;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    @Override
    public void initialize(final PasswordConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {

        final org.passay.PasswordValidator validator = new org.passay.PasswordValidator(Arrays.
                asList(new LengthRule(6, 20),
                        new RepeatCharacterRegexRule(),
                        new WhitespaceRule()
                // add here more rules
                ));
        final RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }

        context.disableDefaultConstraintViolation();

        String errors = validator.getMessages(result).stream()
                .reduce("", (c, e) -> c + e + " ");

        context.buildConstraintViolationWithTemplate(errors).addConstraintViolation();
        return false;
    }

}
