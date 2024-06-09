package com.trainticketapp.bookingservice.repository;

import static com.trainticketapp.bookingservice.model.CommonConstants.*;

import com.trainticketapp.bookingservice.model.Seat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SectionSeatRepository {
    private Map<String, List<Seat>> sectionVsAvailableSeats;

    public SectionSeatRepository() {
        sectionVsAvailableSeats = new HashMap<>();

        for (String section : SECTIONS) {
            List<Seat> seats = new ArrayList<>();

            for (int i = 1; i <= MAX_SEATS_PER_SECTION; i++) {
                seats.add(new Seat(section + i));
            }
            sectionVsAvailableSeats.put(section, seats);
        }
    }

    public Map<String, List<Seat>> getSectionVsAvailableSeats() {
        return sectionVsAvailableSeats;
    }

    public void setSectionVsAvailableSeats(Map<String, List<Seat>> sectionVsAvailableSeats) {
        this.sectionVsAvailableSeats = sectionVsAvailableSeats;
    }
}
