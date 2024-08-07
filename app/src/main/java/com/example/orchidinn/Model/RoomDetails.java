package com.example.orchidinn.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class RoomDetails implements Parcelable {

    String name, price, id, description, availability, maintenance, image1, image2;

    public RoomDetails() {
    }

    public RoomDetails(String name, String price, String id, String description, String availability, String maintenance, String image1, String image2) {
        this.name = name;
        this.price = price;
        this.id = id;
        this.description = description;
        this.availability = availability;
        this.maintenance = maintenance;
        this.image1 = image1;
        this.image2 = image2;
    }

    protected RoomDetails(Parcel in) {
        name = in.readString();
        price = in.readString();
        id = in.readString();
        description = in.readString();
        availability = in.readString();
        maintenance = in.readString();
        image1 = in.readString();
        image2 = in.readString();
    }

    public static final Creator<RoomDetails> CREATOR = new Creator<RoomDetails>() {
        @Override
        public RoomDetails createFromParcel(Parcel in) {
            return new RoomDetails(in);
        }

        @Override
        public RoomDetails[] newArray(int size) {
            return new RoomDetails[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(String maintenance) {
        this.maintenance = maintenance;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(id);
        dest.writeString(description);
        dest.writeString(availability);
        dest.writeString(maintenance);
        dest.writeString(image1);
        dest.writeString(image2);
    }
}
