package xyz.chokanov.kalchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView mTxtID;
    private EditText mTxtInput;
    private TextView mTxtOutput;
    private Button mBtnSend;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot().child("General");
    private String tempKey,chatUser,chatMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Session session = new Session();

        mTxtID = findViewById(R.id.txtID);
        mTxtOutput = findViewById(R.id.txtOutput);
        mTxtInput = findViewById(R.id.txtInput);
        mBtnSend = findViewById(R.id.btnSend);

        mTxtID.setText("Welcome " + session.getUsername());
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map = new HashMap<String, Object>();
                tempKey = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference msgRoot = root.child(tempKey);
                Map<String, Object> childMap = new HashMap<String, Object>();
                childMap.put("User", session.getUsername());
                childMap.put("Message", mTxtInput.getText().toString());
                msgRoot.updateChildren(childMap);
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
    public void appendData(DataSnapshot dataSnapshot){
        Iterator iterator = dataSnapshot.getChildren().iterator();
        while(iterator.hasNext()){
            chatMsg = (String) ((DataSnapshot)iterator.next()).getValue();
            chatUser = (String) ((DataSnapshot)iterator.next()).getValue();
            mTxtOutput.append(chatUser + ": " + chatMsg + "\n");
            mTxtInput.setText("");
        }
    }
}
