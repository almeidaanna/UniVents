package com.example.univents.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.univents.model.Event;
import com.example.univents.repository.EventRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EventViewModel extends AndroidViewModel {
    private EventRepository cRepository;
    private LiveData<List<Event>> allEvents;
    private LiveData<List<Event>> culturalEvents;
    private LiveData<List<Event>> religiousEvents;
    private LiveData<List<Event>> sportEvents;
    private LiveData<List<Event>> educationalEvents;

    public EventViewModel (Application application) {
        super(application);
        cRepository = new EventRepository(application);
        allEvents = cRepository.getAllEvents();
        culturalEvents = cRepository.getAllEventsByCategory(1);
        religiousEvents = cRepository.getAllEventsByCategory(2);
        sportEvents = cRepository.getAllEventsByCategory(3);
        educationalEvents = cRepository.getAllEventsByCategory(4);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<Event> findByIDFuture(final int eventId){
        return cRepository.findByIDFuture(eventId);
    }

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

    public void insert(Event event) {
        cRepository.insert(event);
    }
    public void delete(Event event) {
        cRepository.delete(event);
    }

    public void deleteAll() {
        cRepository.deleteAll();
    }

    public void update(Event event) {
        cRepository.updateEvent(event);
    }
}