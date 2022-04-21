package org.shopin.pojo;

import javax.validation.constraints.NotNull;
import org.shopin.validation.EmailConstraint;

public class Email {

    @EmailConstraint
    @NotNull
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
