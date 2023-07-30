package com.feature.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceBDDTest {

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

    @Captor
    private ArgumentCaptor<Double> doubleArgumentCaptor;

    @Test
    public void should_countPlace_when_OneRoomAvailable () {
        // TDD style
//        Mockito.when(this.roomServiceMock.getAvailableRooms())
//                .thenReturn(Collections.singletonList(new Room("Room1", 5)));

        // BDD style:  Given , when, then pattern
        given(this.roomServiceMock.getAvailableRooms())
                .willReturn(Collections.singletonList(new Room("Room1", 5)));

        // When
        int placeCount = bookingService.getTotalAvailablePlaceCount();

        // Then
        int expected = 5;
        assertEquals(expected, placeCount);
    }

    @Test
    public void should_invoke_payment_when_prepaid() {

        BookingRequest request =  BookingRequest.builder().userId("user01")
                .dateFrom(LocalDate.now())
                .dateTo(LocalDate.now().plusDays(3))
                .guestCount(2)
                .prepaid(true)
                .build();


         /* when(roomServiceMock.findAnyAvailableRoom(request))
                .thenReturn(new Room("1.1", 2))  ;*/
        given(roomServiceMock.findAnyAvailableRoom(request)).willReturn(new Room("1.1", 2));


        bookingService.makeBooking(request);


       /*
       These code
       Mockito.verify(paymentServiceMock, times(1)).pay(request, 300);
        Mockito.verifyNoMoreInteractions(paymentServiceMock);

        replace with
        */
        then(paymentServiceMock).should(times(1)).pay(request, 300);
        then(paymentServiceMock).shouldHaveNoMoreInteractions();
    }



}
