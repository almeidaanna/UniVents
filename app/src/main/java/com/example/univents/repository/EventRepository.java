package com.example.univents.repository;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.univents.dao.EventDAO;
import com.example.univents.database.EventDatabase;
import com.example.univents.model.Event;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class EventRepository {
    private EventDAO eventDao;
    private LiveData<List<Event>> allEvents;

    public EventRepository(Application application){
        EventDatabase db = EventDatabase.getInstance(application);
        eventDao = db.eventDao();
        allEvents = eventDao.getAll();
    }
    // Room executes this query on a separate thread
    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    public void insert(final Event event){
        EventDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override public void run() {
                eventDao.insert(event);
            }
        });
    }
    public void deleteAll(){
        EventDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override public void run()
            {
                eventDao.deleteAll();
            }
        });
    }
    public void delete(final Event event){
        EventDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                eventDao.delete(event);
            }
        });
    }
    public void updateEvent(final Event event){
        EventDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                eventDao.updateEvent(event);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<Event> findByIDFuture(final int eventId) {
        return CompletableFuture.supplyAsync(new Supplier<Event>() {
            @Override
            public Event get() {
                return eventDao.findByID(eventId);
            }
        }, EventDatabase.databaseWriteExecutor);
    }
}

