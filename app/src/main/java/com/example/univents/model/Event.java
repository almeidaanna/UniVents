package com.example.univents.model;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private String eventName;
    private String eventDate;
    private String eventDay;
    private String eventDetail;

    public Event(String name, String date, String day, String detail) {
        eventName = name;
        eventDate = date;
        eventDay = day;
        eventDetail = detail;
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
                "eventName='" + eventName + '\'' +
                ", eventDate='" + eventDate + '\'' +
                ", eventDay='" + eventDay + '\'' +
                ", eventDetail='" + eventDetail + '\'' +
                '}';
    }

    //remove this before actual deployment
    public static ArrayList<Event> createEventList()
    {
        ArrayList<Event> eventList = new ArrayList<Event>();
        eventList.add(new Event("event1","12/2/22", "Monday", "This is a dance event and starts at 4:00 pm be there"));
        eventList.add(new Event("event2","13/2/22", "Tuesday", "This is a music event and starts at 4:00 pm be there"));
        eventList.add(new Event("event3","14/2/22", "Wednesday", "This is a fashion event and starts at 4:00 pm be there"));
        eventList.add(new Event("event4","15/2/22", "Thursday", "This is a dance event and starts at 4:00 pm be there"));
        eventList.add(new Event("event5","16/2/22", "Friday", "This is a music event and starts at 4:00 pm be there"));
        eventList.add(new Event("event6","17/2/22", "Saturday", "This is a fashion event and starts at 4:00 pm be there"));
        return eventList;
    }
}
