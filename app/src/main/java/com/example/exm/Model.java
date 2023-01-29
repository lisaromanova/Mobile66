package com.example.exm;

import android.os.Parcel;
import android.os.Parcelable;

public class Model implements Parcelable {
    public int ID;
    public String Title;
    public int Cost;
    public int StockAvailability;
    public int AvailabilityInTheStore;
    public String Description;
    public String Rewiews;
    public String Image;

    public Model(int ID, String title, int cost, int stockAvailability, int availabilityInTheStore, String description, String rewiews, String image) {
        this.ID = ID;
        Title = title;
        Cost = cost;
        StockAvailability = stockAvailability;
        AvailabilityInTheStore = availabilityInTheStore;
        Description = description;
        Rewiews = rewiews;
        Image = image;
    }

    protected Model(Parcel in) {
        ID = in.readInt();
        Title = in.readString();
        Cost = in.readInt();
        StockAvailability = in.readInt();
        AvailabilityInTheStore = in.readInt();
        Description = in.readString();
        Rewiews = in.readString();
        Image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(Title);
        dest.writeInt(Cost);
        dest.writeInt(StockAvailability);
        dest.writeInt(AvailabilityInTheStore);
        dest.writeString(Description);
        dest.writeString(Rewiews);
        dest.writeString(Image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return Title;
    }

    public int getCost() {
        return Cost;
    }

    public int getStockAvailability() {
        return StockAvailability;
    }

    public int getAvailabilityInTheStore() {
        return AvailabilityInTheStore;
    }

    public String getDescription() {
        return Description;
    }

    public String getRewiews() {
        return Rewiews;
    }

    public String getImage() {
        return Image;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setCost(int cost) {
        Cost = cost;
    }

    public void setStockAvailability(int stockAvailability) {
        StockAvailability = stockAvailability;
    }

    public void setAvailabilityInTheStore(int availabilityInTheStore) {
        AvailabilityInTheStore = availabilityInTheStore;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setRewiews(String rewiews) {
        Rewiews = rewiews;
    }

    public void setImage(String image) {
        Image = image;
    }

}
