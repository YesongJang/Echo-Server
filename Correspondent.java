package echo;

import java.io.*;
import java.net.*;

public class Correspondent {
    protected Socket sock;
    protected BufferedReader sockIn;
    protected PrintWriter sockOut;

    // 기본 생성자 (서버 측에서 setSocket()으로 소켓 설정 예정)
    public Correspondent() { }

    // 클라이언트 측에서 직접 소켓을 받아올 경우 사용
    public Correspondent(Socket s) {
        this.sock = s;
        initStreams();
    }

    // 서버 측: 수신한 소켓을 설정
    public void setSocket(Socket socket) {
        this.sock = socket;
        initStreams();
    }

    // 입력/출력 스트림 초기화
    protected void initStreams() {
        try {
            sockIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            sockOut = new PrintWriter(sock.getOutputStream(), true); // auto-flush
        } catch (IOException e) {
            System.err.println("Stream initialization error: " + e.getMessage());
        }
    }

    // 연결 종료
    public void close() {
        try {
            sock.close();
        } catch (IOException e) {
            System.err.println("Socket close error: " + e.getMessage());
        }
    }

    // 클라이언트 측에서 서버에 연결
    public void requestConnection(String host, int port) {
        try {
            sock = new Socket(host, port);
            initStreams();
        } catch (UnknownHostException uhe) {
            System.err.println("Unknown host: " + uhe.getMessage());
        } catch (IOException ioe) {
            System.err.println("Failed to connect: " + ioe.getMessage());
        }
    }

    // 요청 전송 (텍스트)
    public void send(String request) {
        sockOut.println(request);
    }

    // 응답 수신 (텍스트)
    public String receive() {
        try {
            return sockIn.readLine();
        } catch (IOException e) {
            System.err.println("Receive error: " + e.getMessage());
            return null;
        }
    }
}
