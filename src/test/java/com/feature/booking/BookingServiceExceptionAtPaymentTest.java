package com.feature.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class BookingServiceExceptionAtPaymentTest {

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

    // When payment is too big, throw exception
    // Technique ArgumentMatchers for any instance of Booking request
    @Test
    public void should_throwException_When_PaymentMockFailed() {
        BookingRequest request =  BookingRequest.builder().userId("user01")
                .dateFrom(LocalDate.now())
                .dateTo(LocalDate.now().plusDays(3))
                .guestCount(2)
                .prepaid(true)
                .build();


       when(this.paymentServiceMock
               .pay(ArgumentMatchers.any(), ArgumentMatchers.anyDouble())
       ).thenThrow(BusinessException.class);

        Executable executable = () -> bookingService.makeBooking(request);
        assertThrows(BusinessException.class, executable);
    }

}
