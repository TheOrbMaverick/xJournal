package com.example.android.xjournal;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class EnteredData extends AppCompatActivity {

    private TextView mTitleText;
    private TextView mBody;
    private ImageButton mImageButton;

    private RecyclerView mJournalList;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entered_data);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Journal");

        mJournalList = (RecyclerView) findViewById(R.id.rv_entries);
        mJournalList.setHasFixedSize(false);
        mJournalList.setLayoutManager(new LinearLayoutManager(this));

        mTitleText = (TextView) findViewById(R.id.entered_title);
        mBody = (TextView) findViewById(R.id.entered_body);

        mImageButton = (ImageButton) findViewById(R.id.image_button_entered);
        mImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent addJournalEntry = new Intent(EnteredData.this, JournalEntry.class);
                startActivity(addJournalEntry);

            }
        });
    }

    private void showUpdateDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialogue, null);

        dialogBuilder.setView(dialogView);

        final EditText editTitle = (EditText) dialogView.findViewById(R.id.edit_title);
        final EditText editBody = (EditText) dialogView.findViewById(R.id.edit_body);
        final Button editButton = (Button) dialogView.findViewById(R.id.edit_button);

        dialogBuilder.setTitle("Updating entry");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTitle.getText().toString().trim();
                String body = editBody.getText().toString().trim();

                //the data that is created in the darabase
                HashMap<String, String> dataMap = new HashMap<String, String>();
                dataMap.put("title", title);
                dataMap.put("body", body);

                //what creates new data in the database
                mDatabase.push().setValue(dataMap);

                alertDialog.dismiss();

            }
        });
    }

   private boolean updateEntry( String title, String body){

        DatabaseReference mData = FirebaseDatabase.getInstance().getReference().child("Journal");

        Journal newEntry = new Journal(title, body);

        mData.setValue(newEntry);
        Toast.makeText(this, "successfully updated", Toast.LENGTH_SHORT).show();

        return  true;

    }

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseRecyclerAdapter<Journal, JournalViewHolder> firebaseRecyclerAdaper = new FirebaseRecyclerAdapter<Journal, JournalViewHolder>(
                Journal.class,
                R.layout.list_item,
                JournalViewHolder.class,
                mDatabase
        ){
            @Override
            protected void populateViewHolder(JournalViewHolder viewHolder, Journal model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setBody(model.getBody());

                final String postKey = getRef(position).getKey();

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(EnteredData.this, "edit your entry", Toast.LENGTH_LONG).show();

                        showUpdateDialog();

                    }
                });
                viewHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        mDatabase.child(postKey).removeValue();
                        Toast.makeText(EnteredData.this, "successfully deleted", Toast.LENGTH_LONG).show();
                        return false;
                    }
                });

            }
        };

        mJournalList.setAdapter(firebaseRecyclerAdaper);

    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    public static class JournalViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public JournalViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setTitle(String title){
            TextView postTitle = (TextView) mView.findViewById(R.id.entered_title);
            postTitle.setText(title);
        }

        public void setBody(String body){
            TextView postBody = (TextView) mView.findViewById(R.id.entered_body);
            postBody.setText(body);
        }
    }
}
