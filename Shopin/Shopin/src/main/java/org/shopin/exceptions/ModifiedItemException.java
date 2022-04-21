package org.shopin.exceptions;

public final class ModifiedItemException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ModifiedItemException() {
        super();
    }

    public ModifiedItemException(final Throwable cause) {
        super(cause);
    }
}
