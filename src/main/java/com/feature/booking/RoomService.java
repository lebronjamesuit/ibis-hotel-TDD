package com.feature.booking;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoomService {

    private final Map<Room, Boolean> roomAvailability;
    {
        roomAvailability = new HashMap<>();
        roomAvailability.put(new Room("1.1", 2), true);
        roomAvailability.put(new Room("1.2", 2), true);
        roomAvailability.put(new Room("1.3", 5), true);
        roomAvailability.put(new Room("2.1", 3), true);
        roomAvailability.put(new Room("2.2", 4), true);
        roomAvailability.put(new Room("3.1", 6), true);
        roomAvailability.put(new Room("3.2", 8), true);
        roomAvailability.put(new Room("4.1", 10), true);
        roomAvailability.put(new Room("4.2", 12), true);
        roomAvailability.put(new Room("4.2", 12), true);
        roomAvailability.put(new Room("5.2", 1), false);
    }


    public  List<Room> getAvailableRooms() {
         return  roomAvailability.entrySet().stream()
                  .filter(entry -> entry.getValue().equals(true))
                  .map(entry -> entry.getKey())
                  .collect(Collectors.toList());
    }




}
