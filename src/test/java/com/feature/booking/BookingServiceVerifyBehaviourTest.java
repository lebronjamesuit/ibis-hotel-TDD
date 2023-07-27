package com.feature.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;


import java.time.LocalDate;


public class BookingServiceVerifyBehaviourTest {


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

        System.out.println(roomServiceMock.getAvailableRooms());
        System.out.println(bookingService.getTotalAvailablePlaceCount());
    }

    @Test
    public void should_invoke_payment_when_prepaid() {
        // Given
        BookingRequest request =  BookingRequest.builder().userId("user01")
                .dateFrom(LocalDate.now())
                .dateTo(LocalDate.now().plusDays(3))
                .guestCount(2)
                .prepaid(true)
                .build();

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
