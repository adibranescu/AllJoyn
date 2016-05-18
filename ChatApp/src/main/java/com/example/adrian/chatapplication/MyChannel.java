package com.example.adrian.chatapplication;

import java.util.ArrayList;
import java.util.List;

public class MyChannel extends ro.pub.acs.hyccups.opportunistic.Channel {
    @Override
    public String getName() {
        return "MyAwesomeChannel";
    }

    @Override
    public List<String> getInterests() {
        ArrayList<String> interests = new ArrayList<String>();
        interests.add("Advertise_Nickname");
        return interests;
    }

    @Override
    public void onPeerConnected(String deviceId, String userId) {
    }

    @Override
    public void onPeerDisconnected(String deviceId, String userId) {

    }

    @Override
    public void onMessageReceived(String sourceUserId, String destinationUserId, String content, long creationTime) {

    }

    @Override
    public void onDisseminationReceived(String sourceUserId, String content, long creationTime, String[] tags) {

    }
}
