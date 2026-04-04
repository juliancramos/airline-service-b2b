package com.airline.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientCapacityException extends RuntimeException {

    public InsufficientCapacityException(int flightId, int requested, int available) {
        super(String.format(
                "Flight %d does not have enough capacity. Requested: %d, Available: %d",
                flightId, requested, available
        ));
    }
}
