package org.example.tacket.controller;

import org.example.tacket.config.ApiResponse;
import org.example.tacket.config.TicketStatus;
import org.example.tacket.model.Ticket;
import org.example.tacket.request.TicketRequest;
import org.example.tacket.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService ;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Ticket>> createTicket(@RequestBody TicketRequest request){
        Ticket createdTicket = ticketService.createTicket(request);

        ApiResponse<Ticket> response = new ApiResponse<>(
                true,
                "Ticket created successfully",
                HttpStatus.CREATED.toString(),
                createdTicket,
                Instant.now()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<Ticket>>> createMutipleTikers(@RequestBody List<TicketRequest> requests){

        List<Ticket> createTiket = ticketService.createMutipleTickets(requests);

        ApiResponse<List<Ticket>> response = new ApiResponse<>(
                true,
                "Ticket created successfully",
                HttpStatus.CREATED.toString(),
                createTiket,
                Instant.now()
        );

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Ticket>>> getAllTickets(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){

        List<Ticket> ticketList = ticketService.getAllTickets(page, size);
        ApiResponse<List<Ticket>> response = (ticketList == null || ticketList.isEmpty())
                ? new ApiResponse<>(
                false,
                "No tickets found",
                HttpStatus.NOT_FOUND.toString(),
                ticketList,
                Instant.now()
        )
                : new ApiResponse<>(
                true,
                "Tickets retrieved successfully",
                HttpStatus.OK.toString(),
                ticketList,
                Instant.now()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{ticket-Id}")
    public ResponseEntity<ApiResponse<Ticket>> getTicketById(@PathVariable("ticket-Id") Long ticketId){

        Ticket result = ticketService.getTicketById(ticketId);
        ApiResponse<Ticket> response =  result == null ?  new ApiResponse<>(
                false,
                "Ticket fetched successfully",
                HttpStatus.NOT_FOUND.toString(),
                result,
                Instant.now()
        ) : new ApiResponse<>(
                true,
                "Ticket fetched successfully",
                HttpStatus.OK.toString(),
                result,
                Instant.now()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Ticket>>> searchByPassengerName(@RequestParam String passengerName){

        List<Ticket> result = ticketService.searchByPassengerName(passengerName);
        ApiResponse<List<Ticket>> response = result == null ? new ApiResponse<>(
                false,
                "Tickets fetched successfully",
                HttpStatus.NOT_FOUND.toString(),
                result,
                Instant.now()
        ) : new ApiResponse<>(
                true,
                "Tickets fetched successfully",
                HttpStatus.OK.toString(),
                result,
                Instant.now()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<Ticket>>> filterTickets(@RequestParam TicketStatus status, @RequestParam String date){

        List<Ticket> tickets = ticketService.filterByStatusAndDate(status,date);

        ApiResponse<List<Ticket>> respone = (tickets==null || tickets.isEmpty())  ?new ApiResponse<>(
                false,
                "Filtered tickets retrieved successfully",
                HttpStatus.NOT_FOUND.toString(),
                tickets,
                Instant.now()
        ): new ApiResponse<>(
                true,
                "Filtered tickets retrieved successfully",
                HttpStatus.OK.toString(),
                tickets,
                Instant.now()
        );

        return ResponseEntity.ok(respone);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Ticket>> updateTicket(@PathVariable Long id, @RequestBody TicketRequest request){
        Ticket code = ticketService.getTicketById(id);
        if(code == null) {
            ApiResponse<Ticket> response = new ApiResponse<>(
                    false,
                    "No tickets found with the given ID.",
                    HttpStatus.NOT_FOUND.toString(),
                    code,
                    Instant.now()

            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }
        Ticket updateTicket = ticketService.updateTicket(id,request);
        ApiResponse<Ticket> response = new ApiResponse<>(
                true,
                "Ticket Update successfully",
                HttpStatus.OK.toString(),
                updateTicket,
                Instant.now()

        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteTicket(@PathVariable Long id){
        Ticket ticket = ticketService.getTicketById(id);

        if(ticket == null){
            ApiResponse<Void> response = new ApiResponse<>(
                    false,
                    "No ticket found with the given ID.",
                    HttpStatus.NOT_FOUND.toString(),
                    null,
                    Instant.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ticketService.deleteTicket(id);
        ApiResponse<Void> response = new ApiResponse<>(
                true,
                "Ticket deleted successfully",
                HttpStatus.OK.toString(),
                null,
                Instant.now()
        );
        return ResponseEntity.ok(response);
    }



}
