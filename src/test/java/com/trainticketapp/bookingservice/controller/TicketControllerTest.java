package com.trainticketapp.bookingservice.controller;

import com.trainticketapp.bookingservice.dto.TicketPurchaseRequest;
import com.trainticketapp.bookingservice.model.TicketReceipt;
import com.trainticketapp.bookingservice.model.User;
import com.trainticketapp.bookingservice.repository.RouteFareRepository;
import com.trainticketapp.bookingservice.repository.UserTicketRepository;
import com.trainticketapp.bookingservice.service.SeatAllocationService;
import com.trainticketapp.bookingservice.service.TicketBookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.servlet.config.MvcNamespaceHandler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {

    public static final String LONDON = "London";
    public static final String FRANCE = "France";
    @MockBean
    private TicketBookingService ticketBookingService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testPurchaseTicket_when_ticket_purchase_successful() throws Exception {
        TicketPurchaseRequest request = getTicketPurchaseRequest();
        TicketReceipt ticketReceipt = getTicketReceipt(request);
        when(ticketBookingService.purchaseTicket(any())).thenReturn(ticketReceipt);

        mockMvc.perform(post("/api/ticket/purchaseTicket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    private static TicketPurchaseRequest getTicketPurchaseRequest() {
        TicketPurchaseRequest request = new TicketPurchaseRequest();
        request.setOrigin(LONDON);
        request.setDestination(FRANCE);
        User user = new User();
        user.setFirstName("First");
        user.setLastName("Second");
        user.setEmail("first.second@demo.com");
        request.setUser(user);
        return request;
    }

    private static TicketReceipt getTicketReceipt(TicketPurchaseRequest request) {
        TicketReceipt ticketReceipt = new TicketReceipt();
        ticketReceipt.setOrigin(request.getOrigin());
        ticketReceipt.setDestination(request.getDestination());
        ticketReceipt.setUser(request.getUser());
        ticketReceipt.setFare(20.0);
        ticketReceipt.setSeatNo("A1");
        return ticketReceipt;
    }

    @Test
    public void testPurchaseTicket_when_ticket_purchase_unsuccessful() throws Exception {
        TicketPurchaseRequest request = getTicketPurchaseRequest();
        when(ticketBookingService.purchaseTicket(any())).thenReturn(null);

        mockMvc.perform(post("/api/ticket/purchaseTicket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetTicketPurchaseReceipt_when_ticket_receipt_found() throws Exception {
        when(ticketBookingService.getReceipt(any())).thenReturn(getTicketReceipt(getTicketPurchaseRequest()));
        mockMvc.perform(get("/api/ticket/users/first.second@demo.com/ticketReceipt")).andExpect(status().isOk());
    }

    @Test
    public void testGetTicketPurchaseReceipt_when_ticket_receipt_not_found() throws Exception {
        when(ticketBookingService.getReceipt(any())).thenReturn(null);
        mockMvc.perform(get("/api/ticket/users/first.second@demo.com/ticketReceipt")).andExpect(status().isNotFound());
    }
}