package com.feature.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;


public class BookingServiceCalculatePriceTest {

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

        BookingRequest request =  BookingRequest.builder().userId("user01")
                .dateFrom(LocalDate.now())
                .dateTo( LocalDate.now().plusDays(3))
                .guestCount(2)
                .prepaid(false)
                .build();

        double price  =  this.bookingService.calculatePrice(request);
        Assertions.assertEquals(300, price);
    }

    @Test
    public void should_fail_when_IncorrectInput() {

        BookingRequest request =  BookingRequest.builder().userId("user01")
                .dateFrom(LocalDate.now())
                .dateTo( LocalDate.now().plusDays(3))
                .guestCount(1)
                .prepaid(false)
                .build();
        double price  =  this.bookingService.calculatePrice(request);

        // 1 Guest * 3 days * 50 euro =  150 actual
        Assertions.assertEquals(150, price);
    }



}
