package org.example.tacket.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UpdatePaymentStatusRequest {
    private List<Long> ticketIds;
    private Boolean paymentStatus;

}
