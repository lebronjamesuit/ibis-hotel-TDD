package com.feature.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;


public class BookingServiceArgumentCaptureTest {


    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;
    private BookingService bookingService;

    private ArgumentCaptor<Double> doubleArgumentCaptor;


    @BeforeEach
    public void init(){
        this.paymentServiceMock = Mockito.mock(PaymentService.class);
        this.roomServiceMock = Mockito.mock(RoomService.class);
        this.bookingDAOMock = Mockito.mock(BookingDAO.class);
        this.mailSenderMock = Mockito.mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);

        doubleArgumentCaptor = ArgumentCaptor.forClass(Double.class);
    }

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
