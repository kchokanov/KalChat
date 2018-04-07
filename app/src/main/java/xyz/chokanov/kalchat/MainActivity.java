package xyz.chokanov.kalchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private TextView mTextId;
    private EditText mTextInput;
    private Button mButtonSend;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot().child("General");
    private String messageIdKey;
    private ArrayList<String> mChatMessages = new ArrayList<>();
    private ArrayList<String> mChatNames = new ArrayList<>();
    private ArrayList<String> mChatTimeStamp = new ArrayList<>();
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");

        final Session session = new Session();
        mTextId = findViewById(R.id.txtId);
        mTextInput = findViewById(R.id.txtInput);
        mButtonSend = findViewById(R.id.btnSend);

        initRecyclerView();
        mTextId.setText("Welcome " + session.getUsername());
        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map = new HashMap<String, Object>();
                messageIdKey = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference msgRoot = root.child(messageIdKey);
                Map<String, Object> childMap = new HashMap<String, Object>();
                childMap.put("User", session.getUsername());
                childMap.put("Message", mTextInput.getText().toString());
                childMap.put("TimeSent", new SimpleDateFormat("HH:mm").format(
                        Calendar.getInstance().getTime()));
                msgRoot.updateChildren(childMap);
                mTextInput.setText("");
            }

        });
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                appendData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                appendData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void appendData(DataSnapshot dataSnapshot) {
        Iterator iterator = dataSnapshot.getChildren().iterator();
        while(iterator.hasNext()){
            mChatMessages.add(((DataSnapshot)iterator.next()).getValue().toString());
            mChatTimeStamp.add(((DataSnapshot)iterator.next()).getValue().toString());
            mChatNames.add(((DataSnapshot)iterator.next()).getValue().toString());
        }
    }

    private void initFillerChat(){
        Log.d(TAG, "initFillerChat: started.");
        mChatMessages.add("hey bro");
        mChatNames.add("test Kal");
        mChatTimeStamp.add(new SimpleDateFormat("HH:mm").format(
                Calendar.getInstance().getTime()));
        mChatMessages.add("Hows life");
        mChatNames.add("test Kal");
        mChatTimeStamp.add(new SimpleDateFormat("HH:mm").format(
                Calendar.getInstance().getTime()));
        mChatMessages.add("mines good");
        mChatNames.add("test Kal");
        mChatTimeStamp.add(new SimpleDateFormat("HH:mm").format(
                Calendar.getInstance().getTime()));
        mChatMessages.add("yo man");
        mChatNames.add("test Kal");
        mChatTimeStamp.add(new SimpleDateFormat("HH:mm").format(
                Calendar.getInstance().getTime()));
        mChatMessages.add("talk to me");
        mChatNames.add("test Kal");
        mChatTimeStamp.add(new SimpleDateFormat("HH:mm").format(
                Calendar.getInstance().getTime()));
        mChatMessages.add("im lonely");
        mChatNames.add("test Kal");
        mChatTimeStamp.add(new SimpleDateFormat("HH:mm").format(
                Calendar.getInstance().getTime()));
        mChatMessages.add("and cold");
        mChatNames.add("test Kal");
        mChatTimeStamp.add(new SimpleDateFormat("HH:mm").format(
                Calendar.getInstance().getTime()));
        mChatMessages.add("so cold");
        mChatNames.add("test Kal");
        mChatTimeStamp.add(new SimpleDateFormat("HH:mm").format(
                Calendar.getInstance().getTime()));
        mChatMessages.add("i just need someone");
        mChatNames.add("test Kal");
        mChatTimeStamp.add(new SimpleDateFormat("HH:mm").format(
                Calendar.getInstance().getTime()));
        mChatMessages.add("why was i born a kal...");
        mChatNames.add("test Kal");
        mChatTimeStamp.add(new SimpleDateFormat("HH:mm").format(
                Calendar.getInstance().getTime()));
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: started.");
        RecyclerView recyclerView = findViewById(R.id.recviewTest);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, mChatMessages, mChatNames, mChatTimeStamp);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
