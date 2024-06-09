package com.trainticketapp.bookingservice.model;

public class TicketReceipt {
    String origin;
    String destination;
    User user;
    double fare;
    String seatNo;

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

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    @Override
    public String toString() {
        return "TicketReceipt{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", user=" + user +
                ", fare=" + fare +
                ", seat='" + seatNo + '\'' +
                '}';
    }
}
