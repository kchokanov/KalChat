package xyz.chokanov.kalchat;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Session {
    private final String TAG = "Session";
    private static String username;
    private static String avatarParam;
    private static String id = generateId();
    private DatabaseReference userListDBRef = FirebaseDatabase.getInstance().getReference()
            .getRoot().child("UserList");

    public void setUsername(String username) {
        Session.username = username;
    }

    public void setAvatarParam(String avatarParam) {
        Session.avatarParam = avatarParam;
    }

    public String getUsername() {
        return username;
    }
    public String getAvatarParam() {
        return avatarParam;
    }
    public static String getId() {
        return id;
    }

    public void createNewUser(){
        Log.d(TAG, "createNewUser: called.");
        username = "Kal#" + new Random().nextInt(1337);
        avatarParam = "TODO";
        DatabaseReference userDBRef = userListDBRef.child(id);
        Map<String, Object> childMap = new HashMap<String, Object>();
        childMap.put("UserName", username);
        childMap.put("Image", avatarParam);
        userDBRef.updateChildren(childMap);
    }

    private static String generateId(){
        String id = "";
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toString(b & 0xFF));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(res1.toString().getBytes(), 0, res1.toString().length());
                    id = (new BigInteger(1, md.digest())).toString(16);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return id;
    }
}
