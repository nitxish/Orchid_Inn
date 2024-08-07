package com.example.orchidinn.Model;

import android.widget.TextView;

public class BookingDetailsForAdmin {

    String address, adult, checkIn, checkOut, child, country,email, hotelName, name, phoneNo;
    String roomId, roomType, rooms, state, userId, price, paymentMethod, cancel, bookedDate, totalAmount;

    public BookingDetailsForAdmin() {
    }

    public BookingDetailsForAdmin(String address, String adult, String checkIn, String checkOut, String child, String country, String email, String hotelName, String name, String phoneNo, String roomId, String roomType, String rooms, String state, String userId, String price, String paymentMethod, String cancel, String bookedDate, String totalAmount) {
        this.address = address;
        this.adult = adult;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.child = child;
        this.country = country;
        this.email = email;
        this.hotelName = hotelName;
        this.name = name;
        this.phoneNo = phoneNo;
        this.roomId = roomId;
        this.roomType = roomType;
        this.rooms = rooms;
        this.state = state;
        this.userId = userId;
        this.price = price;
        this.paymentMethod = paymentMethod;
        this.cancel = cancel;
        this.bookedDate = bookedDate;
        this.totalAmount = totalAmount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(String bookedDate) {
        this.bookedDate = bookedDate;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
