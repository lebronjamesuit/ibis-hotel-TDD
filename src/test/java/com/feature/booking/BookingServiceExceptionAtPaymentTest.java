package com.feature.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class BookingServiceExceptionAtPaymentTest {

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

    // When payment is too big, throw exception
    @Test
    public void should_throwException_When_PaymentMockFailed() {
        BookingRequest request =  BookingRequest.builder().userId("user01")
                .dateFrom(LocalDate.now())
                .dateTo(LocalDate.now().plusDays(3))
                .guestCount(2)
                .prepaid(true)
                .build();

       when(this.paymentServiceMock.pay( any(), anyDouble())).thenThrow(BusinessException.class);

        Executable executable = () -> bookingService.makeBooking(request);
        assertThrows(BusinessException.class, executable);
    }



}
