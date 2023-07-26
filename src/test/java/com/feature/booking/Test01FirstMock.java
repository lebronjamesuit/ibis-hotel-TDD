package com.feature.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;


public class Test01FirstMock {

    private PaymentService paymentService;
    private RoomService roomService;
    private BookingDAO bookingDAO;
    private MailSender mailSender;
    private BookingService bookingService;


    @BeforeEach
    public void init(){
        this.paymentService = Mockito.mock(PaymentService.class);
        this.roomService = Mockito.mock(RoomService.class);
        this.bookingDAO = Mockito.mock(BookingDAO.class);
        this.mailSender = Mockito.mock(MailSender.class);

        this.bookingService = new BookingService(paymentService, roomService, bookingDAO, mailSender);

    }
    
    @Test
    public void should_true_when_correctInput() {
        String userId = "user01";
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo =  LocalDate.now().plusDays(3);
        int guestCount = 3;
        boolean prepaid = false;

        BookingRequest request =  new BookingRequest(userId, dateFrom, dateTo, guestCount, prepaid);
        double price  =  this.bookingService.calculatePrice(request);

        Assertions.assertEquals(300, price);
    }

}
