package com.feature.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

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
