package com.feature.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class BookingServiceExceptionThrowTest {

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

    // Throw exception case because there is no room has capacity more than 10
    @Test
    public void should_throwException_When_GuestIsMoreThan_10() {
        BookingRequest request =  BookingRequest.builder()
                .guestCount(15)
                .build();

        Mockito.when(roomServiceMock.findAnyAvailableRoom(request))
                .thenThrow(BusinessException.class);

        Executable executable = () -> bookingService.makeBooking(request);
        assertThrows(BusinessException.class, executable);
    }



}
