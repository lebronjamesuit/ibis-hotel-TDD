package com.feature.booking;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RoomServiceTest {


    @Test
    public void should_return_9_rooms() {
        // Given, 11 - 1 false - 1 duplicate entry set
        RoomService roomService = new RoomService();
        List<Room> rooms = roomService.getAvailableRooms();

        // Expect
        int expect = 9;

        // Then
        assertEquals(9, rooms.size());
    }


}
