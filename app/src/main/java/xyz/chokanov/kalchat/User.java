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

/**
 * Class for storing User info.
 */
public class User {
    private static final String TAG = "User";
    private static String userName;
    private static String avatarParam;
    private static String id = generateId();
    private DatabaseReference userListDBRef = FirebaseDatabase.getInstance().getReference()
            .getRoot().child("UserList");

    public void setUsername(String username) {
        User.userName = username;
    }

    public void setAvatarParam(String avatarParam) {
        User.avatarParam = avatarParam;
    }

    public String getUsername() {
        return userName;
    }
    public String getAvatarParam() {
        return avatarParam;
    }
    public static String getId() {
        return id;
    }

    /**
     * Creates new user with a random username and avatar parameters.
     */
    public void createNewUser(){
        Log.d(TAG, "createNewUser: called.");
        userName = "Kal#" + new Random().nextInt(1337);
        avatarParam = "TODO";
        DatabaseReference userDBRef = userListDBRef.child(id);
        Map<String, Object> childMap = new HashMap<String, Object>();
        childMap.put("UserName", userName);
        childMap.put("Image", avatarParam);
        userDBRef.updateChildren(childMap);
    }

    /**
     * Creates unique user ID by hashing the devices MAC address.
     * @return user ID as string
     */
    private static String generateId(){
        Log.d(TAG, "generateId: called");
        String id = "";
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress(); //TODO - Find out how this actually gets the MAC
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
