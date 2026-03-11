package org.example.tacket.service;

import org.example.tacket.config.TicketStatus;
import org.example.tacket.model.Ticket;
import org.example.tacket.request.TicketRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private AtomicLong counter = new AtomicLong() ;
    private List<Ticket> tickes = new ArrayList<>(
            List.of(
                    new Ticket(counter.incrementAndGet(),"Harry Potter", LocalDate.of(2026, 3, 11),"Boston","Washington DC",180.25,"true",TicketStatus.BOOKED,"A4"),
                    new Ticket(counter.incrementAndGet(),"String", LocalDate.of(2026, 3, 11),"Boston","Washington DC",180.25,"true",TicketStatus.CANCELLED,"A4"),
                    new Ticket(counter.incrementAndGet(),"Ra", LocalDate.of(2026, 3, 11),"pp","pvh",10.0,"true",TicketStatus.COMPLETED,"A01"),
                    new Ticket(counter.incrementAndGet(),"Harry Potter II", LocalDate.of(2026, 3, 11),"Boston","Washington DC",180.25,"true",TicketStatus.COMPLETED,"A4"),
                    new Ticket(counter.incrementAndGet(),"Harry Potter III", LocalDate.of(2026, 3, 11),"Boston","Washington DC",180.25,"true",TicketStatus.CANCELLED,"A4"),
                    new Ticket(counter.incrementAndGet(),"Harry Potter", LocalDate.of(2026, 3, 11),"Boston","Washington DC",180.25,"true",TicketStatus.BOOKED,"A4")
            )
    );


    public Ticket createTicket(TicketRequest request){
        Ticket ticket = new Ticket();

        ticket.setTicketId(counter.incrementAndGet());
        ticket.setPassengerName(request.getPassengerName());
        ticket.setTravelDate(request.getTravelDate());
        ticket.setSourceStation(request.getSourceStation());
        ticket.setDestinationStation(request.getDestinationStation());
        ticket.setPrice(request.getPrice());
        ticket.setPaymentStatus(request.getPaymentStatus());
        ticket.setTicketStatus(request.getTicketStatus());
        ticket.setSeatNumber(request.getSeatNumber());

        tickes.add(ticket);

        return ticket;
    }

    public List<Ticket> createMutipleTickets(List<TicketRequest> requests){

        List<Ticket> result = new ArrayList<>();

        for(TicketRequest request : requests){
            result.add(createTicket(request));
        }

        return result;
    }

    public  List<Ticket> getAllTickets(int page , int size){
        return tickes.stream()
                .skip( page * size)
                .limit(size)
                .toList();
    }

    public Ticket getTicketById(Long id){
        return tickes.stream()
                .filter(t -> t.getTicketId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Ticket> searchByPassengerName(String name){
        return tickes.stream()
                .filter(t -> t.getPassengerName().equals(name))
                .collect(Collectors.toList());
    }

    public List<Ticket> filterByStatusAndDate(TicketStatus status, String date){
        return tickes.stream()
                .filter(t -> t.getTicketStatus().equals(status))
                .collect(Collectors.toList());
    }

    public Ticket updateTicket(Long id, TicketRequest request){

        Ticket ticket = getTicketById(id);

        if(ticket != null){

            ticket.setPassengerName(request.getPassengerName());
            ticket.setTravelDate(request.getTravelDate());
            ticket.setSourceStation(request.getSourceStation());
            ticket.setDestinationStation(request.getDestinationStation());
            ticket.setPrice(request.getPrice());
            ticket.setPaymentStatus(request.getPaymentStatus());
            ticket.setTicketStatus(request.getTicketStatus());
            ticket.setSeatNumber(request.getSeatNumber());

        }

        return ticket;
    }

    public void deleteTicket(Long id){
        tickes.removeIf(t -> t.getTicketId().equals(id));
    }


}
