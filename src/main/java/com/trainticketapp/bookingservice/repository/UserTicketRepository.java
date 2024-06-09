package com.trainticketapp.bookingservice.repository;

import com.trainticketapp.bookingservice.model.TicketReceipt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserTicketRepository {

    private Map<String, TicketReceipt> userVsTickets;

    public UserTicketRepository() {
        userVsTickets = new HashMap<>();
    }

    public Map<String, TicketReceipt> getUserVsTickets() {
        return userVsTickets;
    }

    public void setUserVsTickets(String user, TicketReceipt ticketReceipt) {
        this.userVsTickets.put(user, ticketReceipt);
    }
}
