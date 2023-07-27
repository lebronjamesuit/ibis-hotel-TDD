package com.feature.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class BookingServiceVoidMethodTest {

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

    // Purpose: Test the mail sender in an exception case
    @Test
    public void should_throwException_When_MailSenderHas_an_issue() {
        // Given
        BookingRequest request = BookingRequest.builder().userId("user01")
                .dateFrom(LocalDate.now())
                .dateTo(LocalDate.now().plusDays(3))
                .guestCount(2)
                .prepaid(true)
                .build();

        when(roomServiceMock.findAnyAvailableRoom(request))
                .thenReturn(new Room("1.1", 2))  ;

        when(bookingDAOMock.save(request)).thenReturn("bookingRequestDummy");

        doThrow(
                new UnsupportedOperationException()
        ).when(mailSenderMock).sendBookingConfirmation(anyString());

        // When things happen
        Executable executable = () -> bookingService.makeBooking(request);

        // Then
        assertThrows(UnsupportedOperationException.class, executable);

    }



}
