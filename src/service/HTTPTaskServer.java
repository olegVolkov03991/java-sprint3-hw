package service;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HTTPTaskServer {
    private static final int PORT = 8080;
    HttpServer httpServer;


    public HTTPTaskServer() {
        try {
            httpServer = HttpServer.create();
            httpServer.bind(new InetSocketAddress(PORT), 0);
            httpServer.start();
            System.out.println("HTTP сервер запущен на " + PORT + " порту");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
