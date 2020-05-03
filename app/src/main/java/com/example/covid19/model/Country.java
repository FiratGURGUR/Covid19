package com.example.covid19.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Country implements Parcelable {


    private String Country;

    private String Slug;

    private String ISO2;

    public Country(
            @JsonProperty("name")String country,
            @JsonProperty("iso2")String slug,
            @JsonProperty("iso3") String ISO2) {
        this.Country = country;
        this.Slug = slug;
        this.ISO2 = ISO2;
    }

    protected Country(Parcel in) {
        Country = in.readString();
        Slug = in.readString();
        ISO2 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Country);
        dest.writeString(Slug);
        dest.writeString(ISO2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    public String getCountry() {
        return Country;
    }

    public String getSlug() {
        return Slug;
    }

    public String getISO2() {
        return ISO2;
    }
}
