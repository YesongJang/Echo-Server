package echo;

import java.io.*;
import java.net.*;

public class Server {

    protected ServerSocket mySocket;
    protected int myPort;
    public static boolean DEBUG = true;
    protected Class<?> handlerType;

    // 생성자: 포트 설정 + handler 클래스 로딩
    public Server(int port, String handlerTypeName) {
        try {
            myPort = port;
            mySocket = new ServerSocket(myPort);
            handlerType = Class.forName(handlerTypeName);  // 예: "echo.RequestHandler"
        } catch (Exception e) {
            System.err.println("Server setup error: " + e.getMessage());
            System.exit(1);
        }
    }

    // 연결 수락 루프
    public void listen() {
        System.out.println("Server listening at port " + myPort);
        while (true) {
            try {
                Socket clientSocket = mySocket.accept();  // 연결 수락
                if (DEBUG) {
                    System.out.println("Accepted connection from " + clientSocket.getInetAddress());
                }

                RequestHandler handler = makeHandler(clientSocket);  // 핸들러 생성
                if (handler == null) {
                    System.err.println("Handler is null!");
                } else {
                    System.out.println("Handler created. Starting thread...");
                    new Thread(handler).start();
                }

            } catch (IOException e) {
                System.err.println("Connection error: " + e.getMessage());
            }
        }
    }

    // 리플렉션으로 핸들러 생성
    public RequestHandler makeHandler(Socket s) {
        try {
            RequestHandler handler = (RequestHandler) handlerType.getDeclaredConstructor().newInstance();
            handler.setSocket(s);  // Correspondent의 메서드
            return handler;
        } catch (Exception e) {
            System.err.println("Failed to create handler: " + e.getMessage());
            return null;
        }
    }

    // 서버 실행 진입점
    public static void main(String[] args) {
        int port = 5555;
        String service = "echo.MathHandler";

        if (args.length >= 1) {
            service = args[0];
        }
        if (args.length >= 2) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port. Using default 5555.");
            }
        }

        Server server = new Server(port, service);
        server.listen();
    }
}

