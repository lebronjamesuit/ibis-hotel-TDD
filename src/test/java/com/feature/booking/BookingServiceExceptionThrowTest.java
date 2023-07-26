package com.feature.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class BookingServiceExceptionThrowTest {

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
