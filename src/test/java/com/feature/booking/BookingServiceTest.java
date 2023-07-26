package com.feature.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class BookingServiceTest {


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
    public void should_return_0_place_because_of_MockRoomService() {
     int actual =  this.bookingService.getTotalAvailablePlaceCount();
     int expect = 0;

     assertEquals(expect, actual);
    }

    @Test
    public void should_return_26_place_because_of_Real_RoomService(){
        int expectPlace = (2 * 8) + (1* 10); // 26 places
        RoomService roomService = new RoomService();
        this.bookingService = new BookingService(paymentService, roomService, bookingDAO, mailSender);
        int actual =  this.bookingService.getTotalAvailablePlaceCount();
        assertEquals(expectPlace, actual);
    }



}
