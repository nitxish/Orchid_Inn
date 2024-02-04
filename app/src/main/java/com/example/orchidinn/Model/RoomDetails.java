package com.example.orchidinn.Model;

public class RoomDetails {

    String roomName, rate;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public RoomDetails(String roomName, String rate) {
        this.roomName = roomName;
        this.rate = rate;
    }
}
