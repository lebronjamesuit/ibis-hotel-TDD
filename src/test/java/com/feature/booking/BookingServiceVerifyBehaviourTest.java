package com.feature.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;


import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class BookingServiceVerifyBehaviourTest {

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
    public void should_invoke_payment_when_prepaid() {
        // Given
        BookingRequest request =  BookingRequest.builder().userId("user01")
                .dateFrom(LocalDate.now())
                .dateTo(LocalDate.now().plusDays(3))
                .guestCount(2)
                .prepaid(true)
                .build();

        when(roomServiceMock.findAnyAvailableRoom(request))
                .thenReturn(new Room("1.1", 2))  ;

        // Actual real service
        bookingService.makeBooking(request);

        // Verify after run
        // Price:  guest count * 50 * days  = 300
        Mockito.verify(paymentServiceMock, times(1)).pay(request, 300);
        Mockito.verifyNoMoreInteractions(paymentServiceMock);
    }


    @Test
    public void should_NOT_invoke_payment_when_NOT_prepaid() {
        // Given
        BookingRequest request =  BookingRequest.builder().userId("user01")
                .dateFrom(LocalDate.now())
                .dateTo( LocalDate.now().plusDays(3))
                .guestCount(2)
                .prepaid(false)
                .build();

        when(roomServiceMock.findAnyAvailableRoom(request))
                .thenReturn(new Room("1.1", 2))  ;

        // Actual real service
        bookingService.makeBooking(request);

        // Verify after run
        Mockito.verify(paymentServiceMock, never()).pay(any(), anyDouble());

    }


    // Purpose: Mock data for Room, then verify bookingDAOMock has been called
    @Test
    public void should_invoke_DAO_when_mock_data_room() {
        BookingRequest request =  BookingRequest.builder().userId("user01")
                .dateFrom(LocalDate.now())
                .dateTo( LocalDate.now().plusDays(3))
                .guestCount(2)
                .prepaid(true)
                .build();

        when(roomServiceMock.findAnyAvailableRoom(request))
                .thenReturn(new Room("1.1", 2))  ;

       bookingService.makeBooking(request);

       verify(bookingDAOMock, times(1)).save(request);

    }



}
