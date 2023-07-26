package com.feature.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BookingServiceCustomValueTest {

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

    }

    @Test
    public void should_countPlace_when_OneRoomAvailable () {
            // Given , ta co
        Mockito.when(this.roomServiceMock.getAvailableRooms())
                .thenReturn(Collections.singletonList(new Room("Room1", 5)));
            // When, call some method
        int placeCount = bookingService.getTotalAvailablePlaceCount();

           // Then
        int expected = 5;
        assertEquals(expected, placeCount);
    }

    @Test
    public void should_countPlace_when_TwoRoomAvailable () {
        // Given, custom value
        List<Room> rooms = Arrays.asList(new Room("RoomA", 2), new Room("RoomB",4));
        Mockito.when(this.roomServiceMock.getAvailableRooms())
                .thenReturn(rooms);

        // When,
        int placeCount = bookingService.getTotalAvailablePlaceCount();

        // Then
        int expected = 2 + 4;
        assertEquals(expected, placeCount);
    }

}
