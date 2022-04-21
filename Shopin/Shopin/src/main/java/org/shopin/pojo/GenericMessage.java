package org.shopin.pojo;

import org.shopin.util.MessageType;

public class GenericMessage {

    private MessageType type;
    private String sender;
    private String receiver;
    private String payload;

    public GenericMessage() {
    }

    public GenericMessage(String sender, String receiver, String payload, MessageType type) {
        this.sender = sender;
        this.receiver = receiver;
        this.payload = payload;
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "GenericMessage{" + "sender=" + sender + ", "
                + "receiver=" + receiver + ", payload=" + payload + '}';
    }
}
