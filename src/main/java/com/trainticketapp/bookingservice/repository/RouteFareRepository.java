package com.trainticketapp.bookingservice.repository;

import static com.trainticketapp.bookingservice.model.CommonConstants.*;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RouteFareRepository {

    Map<String, Double> routeVsCost;

    public RouteFareRepository() {
        routeVsCost = new HashMap<>();
        routeVsCost.put(LONDON_FRANCE_ROUTE, LONDON_FRANCE_FARE);
    }

    public Map<String, Double> getRouteVsCost() {
        return routeVsCost;
    }

    public void setRouteVsCost(Map<String, Double> routeVsCost) {
        this.routeVsCost.putAll(routeVsCost);
    }
}
