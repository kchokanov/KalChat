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
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * Starting activity for the application. Creates chat and populates chat screen.
 * If the user is registered their data is pulled from the DB, otherwise a new user is registered
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView mTextShowUser;
    private EditText mTextInput;
    private Button mButtonSend;
    private ArrayList<String> mChatMessages = new ArrayList<>(); //TODO - not have an array list with every message ever in the room
    private ArrayList<String> mChatNames = new ArrayList<>();
    private ArrayList<String> mChatTimeStamp = new ArrayList<>();
    private String roomName = "General";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");
        final User user = new User();
        DatabaseReference userBDRef = FirebaseDatabase.getInstance().getReference().getRoot()
                .child("UserList").child(User.getId());
        final DatabaseReference chatDBRef = FirebaseDatabase.getInstance().getReference().getRoot()
                .child(roomName);
        mTextShowUser = findViewById(R.id.txtId);
        mTextInput = findViewById(R.id.txtInput);
        mButtonSend = findViewById(R.id.btnSend);

        initRecyclerView();
        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(chatDBRef, user.getUsername(), mTextInput.getText().toString());
                mTextInput.setText("");
            }

        });
        userBDRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Attempt to get user data. if none exists a new user will be made
                try {
                    user.setAvatarParam(dataSnapshot.child("Image").getValue().toString());
                    user.setUsername(dataSnapshot.child("UserName").getValue().toString());
                    mTextShowUser.setText("Welcome " + user.getUsername());
                }catch (NullPointerException e){
                    user.createNewUser();
                    mTextShowUser.setText("Welcome " + user.getUsername());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO
            }
        });

        chatDBRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                appendChatData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                appendChatData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //TODO
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //TODO
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO
            }
        });
    }

    /**
     * Creates and binds the chat recyclerview to the UI and to the chat array list
     */
    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: started.");
        RecyclerView recyclerView = findViewById(R.id.recviewTest);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, mChatMessages, mChatNames, mChatTimeStamp);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Appends message to chat array list.
     * @param dataSnapshot Database snapshot containing message inforamtion
     */
    private void appendChatData(DataSnapshot dataSnapshot) {
        try {
            mChatMessages.add(dataSnapshot.child("Message").getValue().toString());
            mChatTimeStamp.add(dataSnapshot.child("TimeSent").getValue().toString());
            mChatNames.add(dataSnapshot.child("User").getValue().toString());
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Adds message to database
     * @param root Database reference for the chat room
     * @param username Username of sender
     * @param message Message to be sent... duh
     */
    private void sendMessage(DatabaseReference root, String username, String message){
        Map<String,Object> chatMap = new HashMap<String, Object>();
        String messageIdKey = root.push().getKey();
        root.updateChildren(chatMap);

        DatabaseReference msgDBRef = root.child(messageIdKey);
        Map<String, Object> msgMap = new HashMap<String, Object>();
        msgMap.put("User", username);
        msgMap.put("Message", message);
        msgMap.put("TimeSent", new SimpleDateFormat("HH:mm").format(
                Calendar.getInstance().getTime()));
        msgDBRef.updateChildren(msgMap);
    }

    /**
     * Creates filler chat for testing the UI.
     * @deprecated Hopefully
     */
    private void initFillerChat(){
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
}
