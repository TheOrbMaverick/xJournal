package com.example.android.xjournal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final int LIST_ITEM = 10;
    private JournalAdapter mAdapter;
    private RecyclerView mNumbersList;
    private DatabaseReference mDatabase;
    ImageButton mImageButton;
    TextView mTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNumbersList = (RecyclerView) findViewById(R.id.rv_entries);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumbersList.setLayoutManager(layoutManager);
        mNumbersList.setHasFixedSize(true);

        mAdapter = new JournalAdapter(LIST_ITEM);
        mNumbersList.setAdapter(mAdapter);


        mTitleText = (TextView) findViewById(R.id.title);


/*
        the following code is to get text back from the journal entry, or is it?

        Intent intentThatGoesBackToThisActivity = getIntent();

        if (intentThatGoesBackToThisActivity.hasExtra(Intent.EXTRA_TEXT)){
            String textEntered = intentThatGoesBackToThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            mTitleText.setText(textEntered);

             the following code is to get text back from the journal entry, or is it? Yess this too.

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Title");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
                mTitleText.setText(name);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        }
        */

        mImageButton = (ImageButton) findViewById(R.id.imageButton);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addJournalEntry = new Intent(MainActivity.this, JournalEntry.class );
                startActivity(addJournalEntry);

            }
        });



    }
}
