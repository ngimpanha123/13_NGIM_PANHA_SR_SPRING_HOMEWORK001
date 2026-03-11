package org.example.tacket.request;

import lombok.Data;
import org.example.tacket.config.TicketStatus;

import java.time.LocalDate;

@Data
public class TicketRequest {

    private String passengerName;
    private LocalDate travelDate;
    private String sourceStation;
    private String destinationStation;
    private Double price;
    private Boolean paymentStatus;
    private TicketStatus ticketStatus;
    private String seatNumber;
}
