package com.trainticketapp.bookingservice.service;

import java.util.List;
import java.util.Map;

import com.trainticketapp.bookingservice.model.CommonConstants;
import com.trainticketapp.bookingservice.model.Seat;
import com.trainticketapp.bookingservice.repository.SectionSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SeatAllocationService {

    @Autowired
    SectionSeatRepository sectionSeatRepository;

    public String allocateSeat() {
        Map<String, List<Seat>> sectionVsAvailableSeats = sectionSeatRepository.getSectionVsAvailableSeats();
        for (String section : sectionVsAvailableSeats.keySet()) {
            List<Seat> availableSeats = sectionVsAvailableSeats.get(section);
            for (Seat seat : availableSeats) {
                if (seat.isAvailable()) {
                    seat.setAvailable(false);
                    return seat.getSeatNumber();
                }
            }
        }
        return null;
    }

    public void deallocateSeat(String seat) {
        String section = seat.substring(0, 1);
        int seatNumber = getIntSeatNumber(seat.substring(1));

        if (seatNumber != -1) {
            List<Seat> availableSeats = sectionSeatRepository.getSectionVsAvailableSeats().get(section);
            availableSeats.get(seatNumber - 1).setAvailable(true);
        }
    }

    public String allocateSeat(String section, String seatNumber) {
        List<Seat> availableSeats = sectionSeatRepository.getSectionVsAvailableSeats().get(section);
        int seatNum = getIntSeatNumber(seatNumber);

        if (seatNum != -1) {
            Seat seat = availableSeats.get(seatNum - 1);
            if (seat.isAvailable()) {
                seat.setAvailable(false);
                return seat.getSeatNumber();
            }
        }

        return null;
    }

    private int getIntSeatNumber(String seatNumber) {
        int intSeatNumber = Integer.valueOf(seatNumber);
        if (intSeatNumber >= 1 && intSeatNumber <= CommonConstants.MAX_SEATS_PER_SECTION) {
            return intSeatNumber;
        } else {
            return -1;
        }
    }

}