package com.feature.booking;

public class BookingService {

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

	private final static double BASE_PRICE_USD = 50.0;

	public double calculatePrice(BookingRequest bookingRequest) {
		return 0;
	}
	

}
