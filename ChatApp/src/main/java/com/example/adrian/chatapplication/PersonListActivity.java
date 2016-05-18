package com.example.adrian.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PersonListActivity extends AppCompatActivity implements PersonListWatcher {

    ArrayList<String> peersList;
    ArrayAdapter<String> peersListAdapter;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_person_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_person_list_toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        final String name = i.getExtras().getString("name");
        Toast.makeText(this, "Advertised name: " + name, Toast.LENGTH_SHORT).show();

        initPersonList();

        /* Register as watcher for peers list updates */
        ChatApplication chatApplication = (ChatApplication)getApplication();
        chatApplication.personListWatcher = this;

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(PersonListActivity.this, ChatActivity.class);
                i.putExtra("name", name);
                String peer = list.getItemAtPosition(position).toString();
                i.putExtra("peer", peer);

                ChatApplication chatApplication = (ChatApplication)getApplication();
                chatApplication.useSetChannelName(peer);
                chatApplication.useJoinChannel();

                startActivity(i);
            }
        });
    }

    @Override
    public void personListDidChange() {
        refreshPersonList();
    }

    @Override
    protected void onDestroy() {
        ChatApplication chatApplication = (ChatApplication)getApplication();
        // Unregister observer
        chatApplication.personListWatcher = null;
        super.onDestroy();
    }

    private void refreshPersonList() {

        ChatApplication chatApplication = (ChatApplication)getApplication();
        peersList = (ArrayList<String>) chatApplication.getFoundChannels();

        peersListAdapter.clear();
        peersListAdapter.addAll(listWithTrimmedPrefix(peersList));
        peersListAdapter.notifyDataSetChanged();
    }

    private void initPersonList() {

        ChatApplication chatApplication = (ChatApplication)getApplication();
        peersList = (ArrayList<String>) chatApplication.getFoundChannels();

        peersListAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_person_layout, listWithTrimmedPrefix(peersList));
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(peersListAdapter);
    }

    private ArrayList<String> listWithTrimmedPrefix(ArrayList<String> peersList) {

        String nameWithoutPrefix = null;
        ArrayList<String> newItems = new ArrayList<String>(peersList.size());

        for (String item : peersList) {
            nameWithoutPrefix = item.substring(item.lastIndexOf('.') + 1);
            newItems.add(nameWithoutPrefix);
        }
        return newItems;
    }
}
