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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {

    @MockBean
    private TicketBookingService ticketBookingService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testPurchaseTicket() throws Exception {
        TicketPurchaseRequest ticketPurchaseRequest = new TicketPurchaseRequest();
        ticketPurchaseRequest.setOrigin("London");
        ticketPurchaseRequest.setDestination("France");
        User user = new User();
        user.setFirstName("First");
        user.setLastName("Second");
        user.setEmail("first.second@demo.com");
        ticketPurchaseRequest.setUser(user);

        when(ticketBookingService.purchaseTicket(any())).thenReturn(new TicketReceipt());

        mockMvc.perform(post("/tickets/purchaseTicket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketPurchaseRequest)))
                .andExpect(status().isNotFound());
    }
}