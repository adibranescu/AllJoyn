package com.example.adrian.chatapplication;

public interface Observable {
    public void addObserver(Observer obs);
    public void deleteObserver(Observer obs);
}
