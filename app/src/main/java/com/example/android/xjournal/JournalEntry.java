package com.example.android.xjournal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class JournalEntry extends AppCompatActivity {


    private DatabaseReference mDatabase;

    private EditText mTitleText;
    private EditText mBody;

    private Button mSaveButton;

    private StorageReference mStorage;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_entry);

        mStorage = FirebaseStorage.getInstance().getReference();

        mTitleText = (EditText) findViewById(R.id.title);
        mBody = (EditText) findViewById(R.id.entry);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Journal");

        mSaveButton = (Button) findViewById(R.id.save_button);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //reference to get edit text string
                String title = mTitleText.getText().toString().trim();
                String body = mBody.getText().toString().trim();

                //the data that is created in the darabase
                HashMap<String, String> dataMap = new HashMap<String, String>();
                dataMap.put("title", title);
                dataMap.put("body", body);

                //what creates new data in the database
                mDatabase.push().setValue(dataMap);

                //where the button is going and how it is carrying the text and the data is started
                Context context = JournalEntry.this;
                Class destinationActivity = EnteredData.class;
                Intent startEnteredData = new Intent(context, destinationActivity);

                startActivity(startEnteredData);

            }
        });

    }


}
