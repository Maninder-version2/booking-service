package com.trainticketapp.bookingservice.dto;

import com.trainticketapp.bookingservice.model.User;

public class TicketPurchaseRequest {
    String origin;
    String destination;
    User user;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
