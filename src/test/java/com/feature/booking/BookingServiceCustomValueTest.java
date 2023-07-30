package com.feature.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BookingServiceCustomValueTest {

    @Mock
    private PaymentService paymentServiceMock;

    @Mock
    private RoomService roomServiceMock;

    @Mock
    private BookingDAO bookingDAOMock;

    @Mock
    private MailSender mailSenderMock;

    @InjectMocks
    private BookingService bookingService;

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
