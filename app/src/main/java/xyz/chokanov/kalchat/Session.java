package xyz.chokanov.kalchat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Session {
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot().
            child("UserList").child(getId());
    private static String username;
    private static String avatarParam;

    public Session(){
        addNewUser();
    }

    public static String getUsername() {
        return username;
    }

    private String getId(){
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
        } catch (Exception ex) {
            //handle exception
        }
        return id;
    }

    private void addNewUser(){
        username = "Kal#" + new Random().nextInt(1337);
        avatarParam = "TODO";
        Map<String, Object> childMap = new HashMap<String, Object>();
        childMap.put("UserName", username);
        childMap.put("Image", avatarParam);
        root.updateChildren(childMap);
    }
}
