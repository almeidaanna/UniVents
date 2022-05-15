package com.example.univents.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.univents.model.Event;

import java.util.List;

@Dao
public interface EventDAO {

    @Query("SELECT * FROM event ORDER BY eventName")
    LiveData<List<Event>> getAll();

    @Query("SELECT * FROM event WHERE eventCategoryID = :cID")
    LiveData<List<Event>> getAllEventsByCategory(int cID);

    @Query("SELECT * FROM event WHERE eventId = :eId LIMIT 1")
    Event findByID(int eId);

    @Insert
    void insert(Event event);

    @Delete
    void delete(Event event);

    @Update
    void updateEvent(Event event);

    @Query("DELETE FROM event")
    void deleteAll();
}
