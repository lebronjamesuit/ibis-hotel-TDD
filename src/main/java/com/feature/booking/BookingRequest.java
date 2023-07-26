package com.feature.booking;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@EqualsAndHashCode
@Builder
public class BookingRequest {

	private final String userId;
	private final LocalDate dateFrom;
	private final LocalDate dateTo;
	private final int guestCount;
	private final boolean prepaid;
	private String roomId;
	
	public String getUserId() {
		return userId;
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public int getGuestCount() {
		return guestCount;
	}

	public boolean isPrepaid() {
		return prepaid;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

}
