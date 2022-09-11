package me.rickylafleur.joinmessages.impl;

import java.util.UUID;

public class User {

    private final UUID uniqueId;
    private String message = "";

    public User(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
