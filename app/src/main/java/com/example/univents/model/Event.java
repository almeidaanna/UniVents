package com.example.univents.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.lifecycle.ViewModelProvider;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.example.univents.viewmodel.StudentViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

@Entity
public class Event implements Parcelable {
    @PrimaryKey (autoGenerate = true)
    private int eventId;
    private String eventCategory;
    private int eventCategoryID;
    private String eventName;
    private String eventDate;

    private String eventDay;
    private String eventDetail;
    private String eventTime;
    private double latitude;
    private double longitude;
    @Ignore
    public static final HashMap<String, Integer> categoryType = new HashMap<String, Integer>() {{
        put("CULTURAL", 1);
        put("RELIGIOUS", 2);
        put("SPORT", 3);
        put("EDUCATIONAL", 4);
    }};
    @Ignore
    public static final HashMap<Integer, String> categoryTypeByName = new HashMap<Integer, String>() {{
        put(1, "CULTURAL");
        put(2, "RELIGIOUS");
        put(3, "SPORT");
        put(4, "EDUCATIONAL");
    }};

    public Event(String eventCategory, int eventCategoryID, String eventName, String eventDate, String eventDay, String eventDetail, String eventTime, double latitude, double longitude) {
        this.eventCategory = eventCategory;
        this.eventCategoryID = eventCategoryID;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventDay = eventDay;
        this.eventDetail = eventDetail;
        this.eventTime = eventTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }


//    public HashMap getCategoryType() { return categoryType; }


    protected Event(Parcel in) {
        eventId = in.readInt();
        eventCategory = in.readString();
        eventCategoryID = in.readInt();
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

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setEventCategoryID(int eventCategoryID) {
        this.eventCategoryID = eventCategoryID;
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

    public int getEventCategoryID() { return eventCategoryID; }

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
        eventList.add(new Event("Cultural",1,"Event 1","12/2/2022", "Monday", "This is a dance event ","11:45 AM",-37.918147, 145.168427)); //-37.918147, 145.168427
        eventList.add(new Event("Cultural",1,"Event 2","13/2/2022", "Tuesday", "This is a music event ","3:40 PM",-37.55053 ,145.10063)); //37°55'05.3"S 145°10'06.3"E
        eventList.add(new Event("Cultural",1,"Event 3","14/2/2022", "Wednesday", "This is a fashion event ","4:30 PM",-37.915624, 145.159303)); //-37.915624, 145.159303
        eventList.add(new Event("Cultural",1,"Event 4","15/2/2022", "Thursday", "This is a dance event ","12:55PM",-37.914879, 145.156095)); //-37.914879, 145.156095
        eventList.add(new Event("Cultural",1,"Event 5","16/2/2022", "Friday", "This is a music event ","7:30 PM", -37.914879, 145.156095)); // -37.914879, 145.156095
        eventList.add(new Event("Cultural",1,"Event 6","17/2/2022", "Saturday", "This is a fashion event ","9:30 PM" ,-37.914879, 145.156095)); //-37.914879, 145.156095
        eventList.add(new Event("Religious",2,"Event 1","18/2/2022", "Monday", "This is a mythology quiz ","11:45 AM",-37.918147, 145.168427)); //-37.918147, 145.168427
        eventList.add(new Event("Religious",2,"Event 2","19/2/2022", "Tuesday", "This is a carol singing event ","3:40 PM",-37.55053 ,145.10063)); //37°55'05.3"S 145°10'06.3"E
        eventList.add(new Event("Sport",3,"Event 1","20/2/2022", "Wednesday", "This is a Badminton ","4:30 PM",-37.915624, 145.159303)); //-37.915624, 145.159303
        eventList.add(new Event("Sport",3,"Event 2","15/3/2022", "Thursday", "This is a Cricket ","12:55PM",-37.914879, 145.156095)); //-37.914879, 145.156095
        eventList.add(new Event("Sport",3,"Event 3","20/3/2022", "Wednesday", "This is a Soccer","4:30 PM",-37.915624, 145.159303)); //-37.915624, 145.159303
        eventList.add(new Event("Sport",3,"Event 4","15/5/2022", "Thursday", "This is a Hockey","12:55PM",-37.914879, 145.156095)); //-37.914879, 145.156095
        eventList.add(new Event("Educational",4,"Event 1","19/2/2022", "Thursday", "This is a Science Quiz","12:55PM",-37.914879, 145.156095)); //-37.914879, 145.156095
        eventList.add(new Event("Educational",4,"Event 2","25/3/2022", "Friday", "This is a Hackathon","7:30 PM", -37.914879, 145.156095)); // -37.914879, 145.156095
        eventList.add(new Event("Educational",4,"Event 3","17/3/2022", "Saturday", "This is a RoboWars","9:30 PM" ,-37.914879, 145.156095)); //-37.914879, 145.156095

        return eventList;
    }

    public static ArrayList<Event> getUserEventHistory(String userName) {
        // write code to fetch data from Room/Firebase db.
        // fetch event history based on userName(email)
        ArrayList<Event> eventList = new ArrayList<Event>();
        // write into a eventList
        return eventList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(eventId);
        parcel.writeString(eventCategory);
        parcel.writeInt(eventCategoryID);
        parcel.writeString(eventName);
        parcel.writeString(eventDate);
        parcel.writeString(eventDay);
        parcel.writeString(eventDetail);
        parcel.writeString(eventTime);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
    }
}