package com.example.univents.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Event implements Parcelable {
    private String eventCategory;
    private String eventName;
    private String eventDate;
    private String eventDay;
    private String eventDetail;
    private String eventTime;
    private double latitude;
    private double longitude;

    public Event(String category, String name, String date, String day, String detail, String time,double latitude, double longitude) {
        eventCategory = category;
        eventName = name;
        eventDate = date;
        eventDay = day;
        eventDetail = detail;
        eventTime = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    protected Event(Parcel in) {
        eventCategory = in.readString();
        eventName = in.readString();
        eventDate = in.readString();
        eventDay = in.readString();
        eventDetail = in.readString();
        eventTime = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String name) {
        eventName = name;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String date) {
        eventDate = date;
    }

    public String getEventDay() {
        return eventDay;
    }

    public void setEventDay(String day) {
        eventDay = day;
    }

    public String getEventDetail() {
        return eventDetail;
    }

    public void setEventDetail(String detail) {
        eventDetail = detail;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventCategory='" + eventCategory + '\'' +
                "eventName='" + eventName + '\'' +
                ", eventDate='" + eventDate + '\'' +
                ", eventDay='" + eventDay + '\'' +
                ", eventTime='" + eventTime + '\'' +
                ", eventDetail='" + eventDetail + '\'' +
                ",latitude='" + latitude + '\'' +
                ",longitude='" + longitude + '\'' +
                '}';
    }

    //remove this before actual deployment
    public static ArrayList<Event> createEventList()
    {
        ArrayList<Event> eventList = new ArrayList<Event>();
        eventList.add(new Event("Cultural","Event 1","12/2/22", "Monday", "This is a dance event ","11:45 AM",-37.918147, 145.168427)); //-37.918147, 145.168427
        eventList.add(new Event("Cultural","Event 2","13/2/22", "Tuesday", "This is a music event ","3:40 PM",-37.55053 ,145.10063)); //37°55'05.3"S 145°10'06.3"E
        eventList.add(new Event("Cultural","Event 3","14/2/22", "Wednesday", "This is a fashion event ","4:30 PM",-37.915624, 145.159303)); //-37.915624, 145.159303
        eventList.add(new Event("Cultural","Event 4","15/2/22", "Thursday", "This is a dance event ","12:55PM",-37.914879, 145.156095)); //-37.914879, 145.156095
        eventList.add(new Event("Cultural","Event 5","16/2/22", "Friday", "This is a music event ","7:30 PM", -37.914879, 145.156095)); // -37.914879, 145.156095
        eventList.add(new Event("Cultural","Event 6","17/2/22", "Saturday", "This is a fashion event ","9:30 PM" ,-37.914879, 145.156095)); //-37.914879, 145.156095
        eventList.add(new Event("Religious","Event 1","18/2/22", "Monday", "This is a mythology quiz ","11:45 AM",-37.918147, 145.168427)); //-37.918147, 145.168427
        eventList.add(new Event("Religious","Event 2","19/2/22", "Tuesday", "This is a carol singing event ","3:40 PM",-37.55053 ,145.10063)); //37°55'05.3"S 145°10'06.3"E
        eventList.add(new Event("Sport","Event 1","20/2/22", "Wednesday", "This is a Badminton ","4:30 PM",-37.915624, 145.159303)); //-37.915624, 145.159303
        eventList.add(new Event("Sport","Event 2","15/3/22", "Thursday", "This is a Cricket ","12:55PM",-37.914879, 145.156095)); //-37.914879, 145.156095
        eventList.add(new Event("Sport","Event 3","20/3/22", "Wednesday", "This is a Soccer","4:30 PM",-37.915624, 145.159303)); //-37.915624, 145.159303
        eventList.add(new Event("Sport","Event 4","15/5/22", "Thursday", "This is a Hockey","12:55PM",-37.914879, 145.156095)); //-37.914879, 145.156095
        eventList.add(new Event("Educational","Event 1","19/2/22", "Thursday", "This is a Science Quiz","12:55PM",-37.914879, 145.156095)); //-37.914879, 145.156095
        eventList.add(new Event("Educational","Event 2","25/3/22", "Friday", "This is a Hackathon","7:30 PM", -37.914879, 145.156095)); // -37.914879, 145.156095
        eventList.add(new Event("Educational","Event 3","17/3/22", "Saturday", "This is a RoboWars","9:30 PM" ,-37.914879, 145.156095)); //-37.914879, 145.156095

        return eventList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(eventCategory);
        parcel.writeString(eventName);
        parcel.writeString(eventDate);
        parcel.writeString(eventDay);
        parcel.writeString(eventDetail);
        parcel.writeString(eventTime);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
    }
}
