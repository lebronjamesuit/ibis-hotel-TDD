package com.feature.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;


public class BookingServiceTest {


    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;
    private BookingService bookingService;


    @BeforeEach
    public void init(){
        this.paymentServiceMock = Mockito.mock(PaymentService.class);
        this.roomServiceMock = Mockito.mock(RoomService.class);
        this.bookingDAOMock = Mockito.mock(BookingDAO.class);
        this.mailSenderMock = Mockito.mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);

        System.out.println(roomServiceMock.getAvailableRooms());
        System.out.println(bookingService.getTotalAvailablePlaceCount());
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
        RoomService roomServiceReal = new RoomService();
        this.bookingService = new BookingService(paymentServiceMock, roomServiceReal, bookingDAOMock, mailSenderMock);
        int actual =  this.bookingService.getTotalAvailablePlaceCount();
        assertEquals(expectPlace, actual);
    }



}
