package main.java.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;
    HttpServer httpServer;


    public HttpTaskServer() {
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
