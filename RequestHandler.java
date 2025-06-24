package echo;

import java.net.Socket;

public class RequestHandler extends Correspondent implements Runnable {
    protected boolean active;

    // 생성자: 소켓 설정
    public RequestHandler(Socket s) {
        super(s);
        active = true;
    }

    public RequestHandler() {
        super();
        active = true;
    }

    // 요청에 대한 응답: 기본은 echo
    protected String response(String msg) throws Exception {
        return "echo: " + msg;
    }

    // 종료 전 처리 (필요 시 override)
    protected void shutDown() {
        if (Server.DEBUG) {
            System.out.println("Request handler shutting down");
        }
        active = false;
    }

    // 클라이언트 요청 처리 루프
    public void run() {
        while (active) {
            try {
                String request = receive();
                if (request == null) continue;

                System.out.println("received: " + request);

                if (request.equals("quit")) {
                    shutDown();
                    break;
                }

                String reply = response(request);
                System.out.println("sending: " + reply);
                send(reply);

                Thread.sleep(10); // 너무 빠른 루프 방지

            } catch (Exception e) {
                send("Error: " + e.getMessage() + " ... ending session");
                break;
            }
        }

        close(); // 소켓 닫기
    }
}
