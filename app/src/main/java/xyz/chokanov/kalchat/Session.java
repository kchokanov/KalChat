package xyz.chokanov.kalchat;

import java.util.Random;

public class Session {
    private static String username;

    public Session(){
        username = "Kal#" + new Random().nextInt(1337);
    }

    public static String getUsername() {
        return username;
    }
}
