package com.feature.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceArgumentCaptureTest {

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
    public void should_payCorrect_Price_when_inputCorrect() {
        // Given
        BookingRequest request =  BookingRequest.builder().userId("user01")
                .dateFrom(LocalDate.now())
                .dateTo(LocalDate.now().plusDays(3))
                .guestCount(2)
                .prepaid(true)
                .build();

        Mockito.when(this.roomServiceMock.findAnyAvailableRoom(request))
                .thenReturn(new Room("Room1", 5));


        bookingService.makeBooking(request);


        Mockito.verify(paymentServiceMock, times(1))
                .pay(eq(request), doubleArgumentCaptor.capture());

        double value = doubleArgumentCaptor.getValue();
        Assertions.assertEquals(300, value);

    }

    @Test
    public void should_payCorrect_Price_when_ManyCall() {
        // Given
        BookingRequest request =  BookingRequest.builder().userId("user01")
                .dateFrom(LocalDate.now())
                .dateTo(LocalDate.now().plusDays(3))
                .guestCount(2)
                .prepaid(true)
                .build();

        BookingRequest request2 =  BookingRequest.builder().userId("user01")
                .dateFrom(LocalDate.now())
                .dateTo(LocalDate.now().plusDays(1))
                .guestCount(2)
                .prepaid(true)
                .build();

        Mockito.when(this.roomServiceMock.findAnyAvailableRoom(any()))
                .thenReturn(new Room("Room1", 5));

        List<Double> expected = Arrays.asList( 300.0, 100.0);

        bookingService.makeBooking(request);
        bookingService.makeBooking(request2);


        Mockito.verify(paymentServiceMock, times(2))
                .pay(any(), doubleArgumentCaptor.capture());

        List<Double> values  = doubleArgumentCaptor.getAllValues();
        Assertions.assertEquals(expected, values);

    }

}
