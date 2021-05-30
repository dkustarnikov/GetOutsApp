package com.mitya.getouts;

public enum EventStatus {
    Created("Created"),
    Mapped("Mapped"),
    Complete("Complete");

    String displayName;
    EventStatus(String displayName) {
        this.displayName = displayName;
    }
}
