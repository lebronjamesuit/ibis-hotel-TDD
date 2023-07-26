package com.feature.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BookingServiceCustomValueTest {


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
    public void should_countPlace_when_OneRoomAvailable () {
            // Given , ta co
        Mockito.when(this.roomService.getAvailableRooms())
                .thenReturn(Collections.singletonList(new Room("Room1", 5)));
            // When, call some method, do something
        int placeCount = bookingService.getTotalAvailablePlaceCount();

           // Then, what happened?
        int expected = 5;
        assertEquals(expected, placeCount);

    }

    @Test
    public void should_countPlace_when_TwoRoomAvailable () {
        // Given , custom value
        List<Room> rooms = Arrays.asList(new Room("RoomA", 2), new Room("RoomB",4));
        Mockito.when(this.roomService.getAvailableRooms())
                .thenReturn(rooms);

        // When,
        int placeCount = bookingService.getTotalAvailablePlaceCount();

        // Then
        int expected = 2 + 4;
        assertEquals(expected, placeCount);

    }

}
