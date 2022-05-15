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
    private LiveData<List<Event>> culturalEvents;
    private LiveData<List<Event>> religiousEvents;
    private LiveData<List<Event>> sportEvents;
    private LiveData<List<Event>> educationalEvents;

    public EventRepository(Application application) {
        EventDatabase db = EventDatabase.getInstance(application);
        eventDao = db.eventDao();
        allEvents = eventDao.getAll();
        culturalEvents = eventDao.getAllEventsByCategory(1);
        religiousEvents = eventDao.getAllEventsByCategory(2);
        sportEvents = eventDao.getAllEventsByCategory(3);
        educationalEvents = eventDao.getAllEventsByCategory(4);

    }

    // Room executes this query on a separate thread
    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    public LiveData<List<Event>> getAllEventsByCategory(final int cID) {
        switch (cID) {
            case 1:
                return culturalEvents;
            case 2:
                return religiousEvents;
            case 3:
                return sportEvents;
            case 4:
                return educationalEvents;
            default:
                return null;
        }
    }

    public void insert(final Event event) {
        EventDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                eventDao.insert(event);
            }
        });
    }

    public void deleteAll() {
        EventDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                eventDao.deleteAll();
            }
        });
    }

    public void delete(final Event event) {
        EventDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                eventDao.delete(event);
            }
        });
    }

    public void updateEvent(final Event event) {
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

