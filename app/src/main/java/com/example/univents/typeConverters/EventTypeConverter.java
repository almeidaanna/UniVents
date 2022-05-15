package com.example.univents.typeConverters;

import androidx.room.TypeConverter;

import com.example.univents.model.Event;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EventTypeConverter {
    @TypeConverter
    public static String listToJson(ArrayList<Event> events) {
        return new Gson().toJson(events);
    }

    @TypeConverter
    public static ArrayList<Event> jsonToList(String eventList) {
        Type listType = new TypeToken<ArrayList<Event>>() {}.getType();
        return new Gson().fromJson(eventList, listType);
    }
}
