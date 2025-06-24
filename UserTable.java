package echo;

import java.util.concurrent.ConcurrentHashMap;

public class UserTable {
    private static ConcurrentHashMap<String, String> users = new ConcurrentHashMap<>();

    public static synchronized boolean register(String user, String pass) {
        if (users.containsKey(user)) return false;
        users.put(user, pass);
        return true;
    }

    public static synchronized boolean authenticate(String user, String pass) {
        return users.containsKey(user) && users.get(user).equals(pass);
    }
}

