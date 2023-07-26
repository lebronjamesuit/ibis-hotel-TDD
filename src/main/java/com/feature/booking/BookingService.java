package com.feature.booking;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class BookingService {

    private final static double BASE_PRICE_USD = 50.0;
    private final PaymentService paymentService;
    private final RoomService roomService;
    private final BookingDAO bookingDAO;
    private final MailSender mailSender;

    public BookingService(PaymentService paymentService, RoomService roomService, BookingDAO bookingDAO,
                          MailSender mailSender) {
        super();
        this.paymentService = paymentService;
        this.roomService = roomService;
        this.bookingDAO = bookingDAO;
        this.mailSender = mailSender;
    }

    public double calculatePrice(BookingRequest bookingRequest) {
        // Formulate
        long days = ChronoUnit.DAYS.between(bookingRequest.getDateFrom(), bookingRequest.getDateTo());
        double price = BASE_PRICE_USD * days * bookingRequest.getGuestCount();
        return price;
    }

    public int getTotalAvailablePlaceCount() {
        List<Room> rooms = roomService.getAvailableRooms();
        int placesCount = rooms.stream()
                .map(room -> room.getCapacity())
                .reduce(0, (roomN, roomN1) -> roomN + roomN1);
        return placesCount;
    }

    // Main buz logic
    public String makeBooking(BookingRequest bookingRequest) {
       Room room = roomService.findAnyAvailableRoom(bookingRequest);
       double price = calculatePrice(bookingRequest);

        if (bookingRequest.isPrepaid()) {
            paymentService.pay(bookingRequest, price);
        }

       return "";
    }



}
