package com.example.shopmobile.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class Product implements Parcelable {

    // Using annotations to map JSON keys (e.g. from fakestoreapi.com) to Java fields
    private String id;

    @SerializedName("title")
    private String name;

    private double price;
    private String description;
    private String category;

    @SerializedName("image")
    private String imageUrl;

    // API returns rating as an object: { "rate": 3.9, "count": 120 }
    @SerializedName("rating")
    private Rating ratingObj;


    // Helper for internal usage
    public double getRating() {
        return ratingObj != null ? ratingObj.rate : 0.0;
    }

    public int getRatingCount() {
        return ratingObj != null ? ratingObj.count : 0;
    }

    // No-args constructor for Gson
    public Product() {
    }

    public Product(String id, String name, double price, String description, String category, String imageUrl, Rating ratingObj) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
        this.ratingObj = ratingObj;
    }

    protected Product(Parcel in) {
        id = in.readString();
        name = in.readString();
        price = in.readDouble();
        description = in.readString();
        category = in.readString();
        imageUrl = in.readString();
        ratingObj = in.readParcelable(Rating.class.getClassLoader());
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeString(imageUrl);
        dest.writeParcelable(ratingObj, flags);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getImageUrl() { return imageUrl; }

    // Nested Class for Rating
    public static class Rating implements Parcelable {
        public double rate;
        public int count;

        public Rating() {}

        public Rating(double rate, int count) {
            this.rate = rate;
            this.count = count;
        }

        protected Rating(Parcel in) {
            rate = in.readDouble();
            count = in.readInt();
        }

        public static final Creator<Rating> CREATOR = new Creator<Rating>() {
            @Override
            public Rating createFromParcel(Parcel in) {
                return new Rating(in);
            }

            @Override
            public Rating[] newArray(int size) {
                return new Rating[size];
            }
        };

        @Override
        public int describeContents() { return 0; }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(rate);
            dest.writeInt(count);
        }
    }
}