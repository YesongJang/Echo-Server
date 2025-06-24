package echo;

import java.io.*;

public class SimpleClient extends Correspondent {
    protected BufferedReader stdin;
    protected PrintWriter stdout;
    protected PrintWriter stderr;
    public boolean DEBUG = true;

    // 생성자: 서버 연결 + 입력/출력 스트림 준비
    public SimpleClient(String host, int port) {
        requestConnection(host, port);

        stdout = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(System.out)), true);
        stderr = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(System.err)), true);
        stdin = new BufferedReader(
                new InputStreamReader(System.in));
    }

    // REPL: 사용자 입력 → 서버 전송 → 응답 수신 → 출력
    public void repl() {
        while (true) {
            try {
                stdout.print("-> ");
                stdout.flush();
                String msg = stdin.readLine();
                if (msg == null) continue;

                if (msg.equals("quit")) break;

                if (DEBUG) stdout.println("sending: " + msg);
                send(msg);

                msg = receive();
                stdout.println("received: " + msg);
            } catch (IOException e) {
                stderr.println("Communication error: " + e.getMessage());
                break;
            }
        }

        // 종료 요청 전송 후 종료
        send("quit");
        stdout.println("bye");
    }

    public static void main(String[] args) {
        int port = 5555;
        String host = "localhost";

        // 명령줄 인자 처리 (port, host)
        if (args.length >= 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port. Using default 5555.");
            }
        }

        if (args.length >= 2) {
            host = args[1];
        }

        SimpleClient client = new SimpleClient(host, port);
        client.repl();
    }
}
