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

    public EventViewModel (Application application) {
        super(application);
        cRepository = new EventRepository(application);
        allEvents = cRepository.getAllEvents();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<Event> findByIDFuture(final int eventId){
        return cRepository.findByIDFuture(eventId);
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
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