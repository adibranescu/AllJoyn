package com.example.adrian.chatapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements IMWatcher {

    ArrayAdapter<String> messagesListAdapter;
    ArrayList<String> messagesList;
    ListView lv;
    EditText etMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent i = getIntent();
        final String userName = i.getExtras().getString("name");
        final String peerName = i.getExtras().getString("peer");

        getSupportActionBar().setTitle(peerName);

        FloatingActionButton btnSend = (FloatingActionButton) findViewById(R.id.btnSend);
        lv = (ListView) findViewById(R.id.chatLV);
        etMessage = (EditText) findViewById(R.id.etMessage);

        initMessagesList();

        /* Register as watcher for messages list updates */
        ChatApplication chatApplication = (ChatApplication)getApplication();
        chatApplication.messagesListWatcher = this;

        btnSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String msg = etMessage.getText().toString();

                if(msg != null && msg.length() > 0) {

                    ChatApplication chatApplication = (ChatApplication)getApplication();
                    chatApplication.newLocalUserMessage(userName + ": " + msg);

                } else{
                    Toast.makeText(ChatActivity.this, "Can't send empty message!", Toast.LENGTH_SHORT).show();
                }

                etMessage.setText("");
            }
        });
    }

    @Override
    protected void onDestroy() {

        ChatApplication chatApplication = (ChatApplication)getApplication();
        chatApplication.useLeaveChannel();

        super.onDestroy();
    }

    @Override
    public void didReceiveMessage(String msg) {

        messagesListAdapter.add(msg);
        messagesListAdapter.notifyDataSetChanged();
        lv.setSelection(lv.getCount() - 1);
    }

    private void initMessagesList() {

        ChatApplication chatApplication = (ChatApplication)getApplication();
        messagesList = (ArrayList<String>) chatApplication.getHistory();

        messagesListAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_chat_layout, messagesList);
        lv.setAdapter(messagesListAdapter);
        if (messagesList.size() > 0) {
            lv.setSelection(lv.getCount() - 1);
        }
    }
}
