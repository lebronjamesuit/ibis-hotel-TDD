package com.feature.booking;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoomService {

    public final String messageException = "There is no room that matched the request";

    private final Map<Room, Boolean> roomAvailability;
    {
        roomAvailability = new HashMap<>();
        roomAvailability.put(new Room("1.1", 2), true);
        roomAvailability.put(new Room("1.2", 2), true);
        roomAvailability.put(new Room("1.3", 2), true);
        roomAvailability.put(new Room("2.1", 2), true);
        roomAvailability.put(new Room("2.2", 2), true);
        roomAvailability.put(new Room("3.1", 2), true);
        roomAvailability.put(new Room("3.2", 2), true);
        roomAvailability.put(new Room("4.1", 2), true);
        roomAvailability.put(new Room("4.2", 10), true);
        roomAvailability.put(new Room("4.2", 10), true);
        roomAvailability.put(new Room("5.2", 1), false);
    }


    public  List<Room> getAvailableRooms() {
         return  roomAvailability.entrySet().stream()
                  .filter(entry -> entry.getValue().equals(true))
                  .map(entry -> entry.getKey())
                  .collect(Collectors.toList());
    }

    public Room findAnyAvailableRoom(BookingRequest bookingRequest) {
        // Entry short-form is e
       Room room  =  roomAvailability.entrySet().stream()
                .filter(e -> e.getValue().equals(true))
                .map(e -> e.getKey())
                .filter(e -> e.getCapacity() == bookingRequest.getGuestCount())
                .findFirst().orElseThrow( () -> new BusinessException(messageException));

       return room;
    }

    public void bookRoom(String roomId) {
        Room room = roomAvailability.entrySet().stream()
                .filter(entry -> entry.getKey().getId().equals(roomId) && entry.getValue())
                .findFirst()
                .map(entry -> entry.getKey())
                .orElseThrow(BusinessException::new);

        roomAvailability.put(room, true);
    }


    public void releaseRoom(String roomId) {

    }
}
