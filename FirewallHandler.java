package echo;

import java.util.*;

public class FirewallHandler extends ProxyHandler {
    static private SafeList bannedRequests = new SafeList();

    // static 초기화 블록: 프로그램 시작 시 차단할 명령 설정
    static {
        bannedRequests.ban("new");  // "new" 명령어를 차단
    }

    @Override
    protected String response(String request) throws Exception {
        String cmd = request.trim().split("\\s+")[0].toLowerCase();
        if (bannedRequests.isBlocked(cmd)) {
            return "Sorry, your request is blocked";
        } else {
            return super.response(request);
        }
    }

    // SafeList 내부 클래스
    private static class SafeList {
        private Set<String> bannedRequests = new HashSet<>();

        synchronized public boolean isBlocked(String request) {
            return bannedRequests.contains(request);
        }

        synchronized void ban(String request) {
            bannedRequests.add(request);
        }

        synchronized void unban(String request) {
            bannedRequests.remove(request);
        }
    }
}

