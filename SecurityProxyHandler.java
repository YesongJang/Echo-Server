package echo;

public class SecurityProxyHandler extends ProxyHandler {
    private boolean loggedIn = false;

    @Override
    protected String response(String msg) throws Exception {
        if (!loggedIn) {
            String[] parts = msg.trim().split("\\s+");
            if (parts.length != 3) {
                return "Must login or register first (new/login user password)";
            }

            String command = parts[0];
            String user = parts[1];
            String pass = parts[2];

            switch (command) {
                case "new":
                    if (UserTable.register(user, pass)) {
                        return "Registration successful. Please login.";
                    } else {
                        return "Username already exists.";
                    }
                case "login":
                    if (UserTable.authenticate(user, pass)) {
                        loggedIn = true;
                        return "Login successful.";
                    } else {
                        return "Login failed.";
                    }
                default:
                    return "Unknown command. Use new/login.";
            }
        }

        // 로그인 되어 있으면 일반 요청 전달
        return super.response(msg);
    }
}

