package com.feature.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class Test01FirstMock {

    private PaymentService paymentService;
    private RoomService roomService;
    private BookingDAO bookingDAO;
    private MailSender mailSender;
    private BookingService bookingService;

    @Test
    public void testMethod() {
        Assertions.assertEquals(300, 300);
    }

}
