package com.feature.booking;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class RoomServiceTest {


    @Test
    public void should_return_9_rooms() {
        RoomService roomService = new RoomService();
        List<Room> rooms = roomService.getAvailableRooms();
        // Then
        assertEquals(9, rooms.size());
    }

    @Test
    public void should_ThrowBuzException_when_ThereIsNoRoomFor15Guest(){

        // find a room that fits for 15 people
        RoomService roomService = new RoomService();
        BookingRequest request =  BookingRequest.builder()
                .guestCount(15)
                .build();

        // The max capacity of a room is 10, so it throws ex
        BusinessException throwEx = assertThrows(BusinessException.class, () -> {
            roomService.findAnyAvailableRoom(request);
        });

        assertEquals(roomService.messageException, throwEx.getMessage());

    }

}
