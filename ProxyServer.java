package echo;

import java.net.Socket;

public class ProxyServer extends Server {

    String peerHost;
    int peerPort;

    public ProxyServer(int myPort, String service, int peerPort, String peerHost) {
        super(myPort, service);
        this.peerHost = peerHost;
        this.peerPort = peerPort;
    }

    // 핸들러 생성 및 initPeer 호출
    @Override
    public RequestHandler makeHandler(Socket s) {
        RequestHandler handler = super.makeHandler(s);
        if (handler instanceof ProxyHandler) {
            ((ProxyHandler) handler).initPeer(peerHost, peerPort);
        }
        return handler;
    }

    public static void main(String[] args) {
        int port = 5555;
        int peerPort = 6666;
        String peerHost = "localhost";
        String service = "echo.ProxyHandler";

        if (1 <= args.length) service = args[0];
        if (2 <= args.length) peerPort = Integer.parseInt(args[1]);
        if (3 <= args.length) port = Integer.parseInt(args[2]);
        if (4 <= args.length) peerHost = args[3];

        Server server = new ProxyServer(port, service, peerPort, peerHost);
        server.listen();
    }
}


