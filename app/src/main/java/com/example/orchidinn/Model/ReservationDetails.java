package com.example.orchidinn.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ReservationDetails implements Parcelable {

    String checkIn, checkOut;
    int roomCount, adultCount, childCount;

    public ReservationDetails() {
    }

    public ReservationDetails(String checkIn, String checkOut, int roomCount, int adultCount, int childCount) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomCount = roomCount;
        this.adultCount = adultCount;
        this.childCount = childCount;
    }

    protected ReservationDetails(Parcel in) {
        checkIn = in.readString();
        checkOut = in.readString();
        roomCount = in.readInt();
        adultCount = in.readInt();
        childCount = in.readInt();
    }

    public static final Creator<ReservationDetails> CREATOR = new Creator<ReservationDetails>() {
        @Override
        public ReservationDetails createFromParcel(Parcel in) {
            return new ReservationDetails(in);
        }

        @Override
        public ReservationDetails[] newArray(int size) {
            return new ReservationDetails[size];
        }
    };

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

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public int getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(int adultCount) {
        this.adultCount = adultCount;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(checkIn);
        dest.writeString(checkOut);
        dest.writeInt(roomCount);
        dest.writeInt(adultCount);
        dest.writeInt(childCount);
    }
}
