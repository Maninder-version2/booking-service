package com.trainticketapp.bookingservice.service;

import static com.trainticketapp.bookingservice.model.CommonConstants.LONDON_FRANCE_FARE;
import static com.trainticketapp.bookingservice.model.CommonConstants.LONDON_FRANCE_ROUTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.trainticketapp.bookingservice.dto.TicketPurchaseRequest;
import com.trainticketapp.bookingservice.model.TicketReceipt;
import com.trainticketapp.bookingservice.model.User;
import com.trainticketapp.bookingservice.repository.RouteFareRepository;
import com.trainticketapp.bookingservice.repository.UserTicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class TicketBookingServiceTest {

    @Mock
    private User user;

    @InjectMocks
    @Spy
    private TicketBookingService ticketBookingService;

    @Mock
    private RouteFareRepository routeFareRepository;

    @Mock
    private SeatAllocationService seatAllocationService;

    @Mock
    private UserTicketRepository userTicketRepository;

    @Test
    public void testPurchaseTicket() {
        TicketPurchaseRequest ticket = new TicketPurchaseRequest();
        ticket.setUser(user);
        ticket.setOrigin("London");
        ticket.setDestination("France");

        when(user.getEmail()).thenReturn("first.second@demo.com");
        when(ticketBookingService.allocateSeat()).thenReturn("A1");
        when(routeFareRepository.getRouteVsCost()).thenReturn(Map.of(LONDON_FRANCE_ROUTE, LONDON_FRANCE_FARE));

        TicketReceipt purchasedTicket = ticketBookingService.purchaseTicket(ticket);
        assertEquals("first.second@demo.com", purchasedTicket.getUser().getEmail());
        assertEquals(20.0, purchasedTicket.getFare());
    }

    @Test
    public void testGetReceipt() {
        TicketPurchaseRequest ticketPurchaseRequest = new TicketPurchaseRequest();
        ticketPurchaseRequest.setOrigin("London");
        ticketPurchaseRequest.setDestination("France");
        ticketPurchaseRequest.setUser(user);

        TicketReceipt ticketReceipt = new TicketReceipt();
        ticketReceipt.setFare(20.0);
        User user1 = new User();
        user1.setFirstName("first");
        user1.setLastName("second");
        user1.setEmail("first.second@demo.com");
        ticketReceipt.setUser(user1);

        when(user.getEmail()).thenReturn("first.second@demo.com");
        when(ticketBookingService.allocateSeat()).thenReturn("A1");
        when(routeFareRepository.getRouteVsCost()).thenReturn(Map.of(LONDON_FRANCE_ROUTE, LONDON_FRANCE_FARE));
        when(userTicketRepository.getUserVsTickets()).thenReturn(Map.of("first.second@demo.com", ticketReceipt));
        ticketBookingService.purchaseTicket(ticketPurchaseRequest);

        TicketReceipt receipt = ticketBookingService.getReceipt("first.second@demo.com");

        assertEquals("first.second@demo.com", receipt.getUser().getEmail());
        assertEquals(20.0, receipt.getFare());
    }

    @Test
    public void testGetReceipt_NotFound() {
        when(userTicketRepository.getUserVsTickets()).thenReturn(Map.of("first.second@demo.com", new TicketReceipt()));
        TicketReceipt receipt = ticketBookingService.getReceipt("second.first@demo.com");
        assertNull(receipt);
    }

    @Test
    public void testGetUsersBySection() {
        TicketReceipt t1 = new TicketReceipt();
        t1.setSeatNo("A1");
        TicketReceipt t2 = new TicketReceipt();
        t2.setSeatNo("A2");

        when(userTicketRepository.getUserVsTickets()).thenReturn(Map.of("first.second@demo.com", t1, "first1.second1@demo.com", t2));

        Map<String, String> usersBySection = ticketBookingService.getUsersBySection("A");
        assertEquals(2, usersBySection.size());
    }

    @Test
    public void testRemoveUser() {
        TicketReceipt t1 = new TicketReceipt();
        t1.setSeatNo("A1");
        TicketReceipt t2 = new TicketReceipt();
        t2.setSeatNo("A2");
        Map<String, TicketReceipt> userVsTickets = new HashMap<>();
        userVsTickets.put("first.second@demo.com", t1);
        userVsTickets.put("first1.second1@demo.com", t2);
        when(userTicketRepository.getUserVsTickets()).thenReturn(userVsTickets);

        ticketBookingService.removeUser("first.second@demo.com");
        verify(seatAllocationService, times(1)).deallocateSeat("A1");
    }

    @Test
    public void testRemoveUser_NotFound() {
        TicketReceipt t1 = new TicketReceipt();
        t1.setSeatNo("A1");
        Map<String, TicketReceipt> userVsTickets = new HashMap<>();
        userVsTickets.put("first.second@demo.com", t1);
        when(userTicketRepository.getUserVsTickets()).thenReturn(userVsTickets);
        assertFalse(ticketBookingService.removeUser("first1.second1@demo.com"));
        verify(seatAllocationService, times(0)).deallocateSeat("A1");
    }

    @Test
    public void testModifyUserSeat() {
        TicketReceipt t1 = new TicketReceipt();
        t1.setSeatNo("A1");
        Map<String, TicketReceipt> userVsTickets = new HashMap<>();
        userVsTickets.put("first.second@demo.com", t1);
        when(userTicketRepository.getUserVsTickets()).thenReturn(userVsTickets);
        when(seatAllocationService.allocateSeat("A", "2")).thenReturn("A2");
        boolean modified = ticketBookingService.modifyUserSeat("first.second@demo.com", "A", "2");
        assertTrue(modified);
    }

    @Test
    public void testModifyUserSeat_NotFound() {
        TicketReceipt t1 = new TicketReceipt();
        t1.setSeatNo("A1");
        Map<String, TicketReceipt> userVsTickets = new HashMap<>();
        userVsTickets.put("first.second@demo.com", t1);
        when(userTicketRepository.getUserVsTickets()).thenReturn(userVsTickets);
        boolean modified = ticketBookingService.modifyUserSeat("first1.second1@demo.com", "A", "2");
        assertFalse(modified);
    }
}
