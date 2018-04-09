package xyz.chokanov.kalchat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
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
    private static String avatarImage;
    private static String id = generateId();
    private static final String DEFAULT_AVATAR_IMAGE = "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\noQyfAAAAAElFTkSuQmCC\n";
    private DatabaseReference userListDBRef = FirebaseDatabase.getInstance().getReference()
            .getRoot().child("UserList");

    public void setUsername(String name) {
        DatabaseReference userDBRef = userListDBRef.child(id);
        Map<String, Object> childMap = new HashMap<String, Object>();
        childMap.put("UserName", name);
        userDBRef.updateChildren(childMap);
        userName = name;
    }

    public void setAvatarImage(Bitmap avatar) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        avatar.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String avatarString = Base64.encodeToString(byteArray, Base64.DEFAULT);
        DatabaseReference userDBRef = userListDBRef.child(id);
        Map<String, Object> childMap = new HashMap<String, Object>();
        childMap.put("Image",avatarString);
        userDBRef.updateChildren(childMap);
        avatarImage = avatarString;
    }

    public void setAvatarImage(String avatarString){
        avatarImage = avatarString;
    }

    public String getUsername() {
        return userName;
    }
    public Bitmap getAvatarImage() {
        byte [] encodeByte=Base64.decode(avatarImage,Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        return bitmap;
    }
    public String getAvatarAsString(){//TODO - GET RID OF THIS
        return avatarImage;
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
        avatarImage = DEFAULT_AVATAR_IMAGE;
        DatabaseReference userDBRef = userListDBRef.child(id);
        Map<String, Object> childMap = new HashMap<String, Object>();
        childMap.put("UserName", userName);
        childMap.put("Image", avatarImage);
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
