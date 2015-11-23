package com.goeuro.devtest.json;


public class JsonLocationInfo {

    public long _id;
    public String key;
    public String name;
    public String fullName;
    public String iata_airport_code;
    public String type;
    public String country;
    public geo_position geo_position;
    public long locationId;
    public boolean inEurope;
    public String countryCode;
    public String coreCountry;
    public String distance;


    @Override
    public String toString() {
        return "JsonLocationInfo{" +
                "_id=" + _id +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", iata_airport_code='" + iata_airport_code + '\'' +
                ", type='" + type + '\'' +
                ", country='" + country + '\'' +
                ", geo_position=" + geo_position +
                ", locationId=" + locationId +
                ", inEurope=" + inEurope +
                ", countryCode='" + countryCode + '\'' +
                ", coreCountry='" + coreCountry + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }
}
