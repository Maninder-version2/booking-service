package com.trainticketapp.bookingservice.service;

import com.trainticketapp.bookingservice.model.TicketReceipt;
import com.trainticketapp.bookingservice.dto.TicketPurchaseRequest;
import com.trainticketapp.bookingservice.repository.RouteFareRepository;
import com.trainticketapp.bookingservice.repository.UserTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service
public class TicketBookingService {

    @Autowired
    private SeatAllocationService seatAllocationService;

    @Autowired
    private RouteFareRepository routeFareRepository;

    @Autowired
    private UserTicketRepository userTicketRepository;

    public TicketReceipt purchaseTicket(TicketPurchaseRequest ticket) {
        String seatNo = allocateSeat();

        if (seatNo == null) {
            System.out.println("No Seats are available");
            return null;
        }

        System.out.println("Ticket booked Successfully !!  User : " + ticket.getUser().getEmail() + ", Seat : " + seatNo);
        TicketReceipt ticketReceipt = getTicketReceipt(ticket, seatNo);
        userTicketRepository.setUserVsTickets(ticket.getUser().getEmail(), ticketReceipt);
        System.out.println(ticketReceipt);
        return ticketReceipt;
    }

    private TicketReceipt getTicketReceipt(TicketPurchaseRequest ticket, String seatNo) {
        TicketReceipt ticketReceipt = new TicketReceipt();
        ticketReceipt.setOrigin(ticket.getOrigin());
        ticketReceipt.setDestination(ticket.getDestination());
        ticketReceipt.setUser(ticket.getUser());
        ticketReceipt.setFare(routeFareRepository.getRouteVsCost().get(ticket.getOrigin() + "-" + ticket.getDestination()));
        ticketReceipt.setSeatNo(seatNo);
        return ticketReceipt;
    }

    public TicketReceipt getReceipt(String userId) {
        return userTicketRepository.getUserVsTickets().get(userId);
    }

    public Map<String, String> getUsersBySection(String section) {
        System.out.println(section);
        Map<String, String> usersBySection = new HashMap<>();
        for (Map.Entry<String, TicketReceipt> entry : userTicketRepository.getUserVsTickets().entrySet()) {
            System.out.println(entry.getValue().getSeatNo() + "-" + section);
            if (entry.getValue().getSeatNo().startsWith(section)) {
                usersBySection.put(entry.getKey(), entry.getValue().getSeatNo());
            }
        }
        return usersBySection;
    }

    public boolean removeUser(String userId) {
        if (userTicketRepository.getUserVsTickets().containsKey(userId)) {
            TicketReceipt ticketReceipt = userTicketRepository.getUserVsTickets().remove(userId);
            seatAllocationService.deallocateSeat(ticketReceipt.getSeatNo());
            System.out.println("User: " + userId + " was removed successfully ");
            return true;
        }

        System.out.println("User: " + userId + " cannot be found.");
        return false;
    }

    public boolean modifyUserSeat(String userId, String section, String seat) {
        TicketReceipt ticketReceipt = userTicketRepository.getUserVsTickets().get(userId);
        if (!Objects.isNull(ticketReceipt)) {
            String currentSeat = ticketReceipt.getSeatNo();
            String requestedSeat = section + seat;

            if (!Objects.isNull(currentSeat)) {
                if (currentSeat.equalsIgnoreCase(requestedSeat)) {
                    System.out.println("Requested seat: " + requestedSeat + " was successfully updated");
                    return true;
                }

                String newSeat = seatAllocationService.allocateSeat(section, seat);
                if (!Objects.isNull(newSeat)) {
                    ticketReceipt.setSeatNo(newSeat);
                    System.out.println("Requested seat: " + requestedSeat + " was successfully updated");
                    return true;
                } else {
                    System.err.println("Requested seat: " + requestedSeat + " is not available.");
                }
            }
        } else {
            System.err.println("No valid ticket could be found for user id : " + userId);
        }

        return false;
    }

    private String allocateSeat() {
        return seatAllocationService.allocateSeat();
    }
}