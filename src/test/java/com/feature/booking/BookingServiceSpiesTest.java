package com.feature.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class BookingServiceSpiesTest {


    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOSpy;
    private MailSender mailSenderMock;
    private BookingService bookingService;


    @BeforeEach
    public void init() {
        this.paymentServiceMock = Mockito.mock(PaymentService.class);
        this.roomServiceMock = Mockito.mock(RoomService.class);
        this.bookingDAOSpy = Mockito.spy(BookingDAO.class);
        this.mailSenderMock = Mockito.mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOSpy, mailSenderMock);

    }


    // Purpose: Use the Mockito Spy to run the real method of bookingDAO
    @Test
    public void should_invoke_DAO_and_return_real_bookingId() {

        // Prepare data and mock dependency.
        BookingRequest request = BookingRequest.builder().userId("user01")
                .dateFrom(LocalDate.now())
                .dateTo(LocalDate.now().plusDays(3))
                .guestCount(2)
                .prepaid(true)
                .build();
        when(roomServiceMock.findAnyAvailableRoom(request))
                .thenReturn(new Room("1.1", 2));

        // When things happens
        String bookingId = bookingService.makeBooking(request);

        // Then, expect these data
        verify(bookingDAOSpy, times(1)).save(request);
        assertNotNull(bookingId);
        System.out.println(bookingId);

    }


    // Purpose: Test the cancel booking when BookingRequest is valid.
    @Test
    public void should_cancel_booking_when_bookingRequest_is_valid() {

       // Given
        BookingRequest bookingRequest = BookingRequest.builder().userId("user01")
                .dateFrom(LocalDate.now())
                .dateTo(LocalDate.now().plusDays(3))
                .guestCount(2)
                .prepaid(true)
                .build();

        String bookingId = "dummyBookingId";
        when(roomServiceMock.findAnyAvailableRoom(bookingRequest))
                .thenReturn(new Room("1.1", 2));

        // DAO Spy runs real code, I modify the outcome by force return the valid bookingRequest
        doReturn(bookingRequest).when(bookingDAOSpy).get(bookingId);

        //  When things happens
        bookingService.cancelBooking(bookingId);

        // Verify these method have been called
        verify(roomServiceMock, times(1)).releaseRoom(bookingRequest.getRoomId());
        verify(bookingDAOSpy, times(1)).delete(bookingId);

    }
}