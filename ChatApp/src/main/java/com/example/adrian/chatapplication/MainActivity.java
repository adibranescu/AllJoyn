package com.example.adrian.chatapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    /* Load the native alljoyn_java library. */
    static {
        System.loadLibrary("alljoyn_java");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnFind = (Button) findViewById(R.id.btnFind);
        final EditText etName = (EditText) findViewById(R.id.nickName);

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChatApplication chatApplication = (ChatApplication)getApplication();

                /* Verify if an old Nickname was already advertised and stop that channel */
                // hostTopChannel should have completion handler in order to implement stop -> start
//                String previousChannelName = chatApplication.hostGetChannelName();
//                if (previousChannelName != null) {
//                    chatApplication.hostStopChannel();
//                }

                /* Advertise the new Nickname */
                chatApplication.hostSetChannelName(etName.getText().toString());
                chatApplication.hostStartChannel();

                Intent i = new Intent(MainActivity.this, PersonListActivity.class);
                i.putExtra("name", etName.getText().toString());
                startActivity(i);
            }
        });
    }
}
