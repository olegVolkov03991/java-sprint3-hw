package tests;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import model.Status;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.IdGenerator;
import service.KVServer;
import service.TaskHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Handler;

public class HandlerTest {
    private HttpClient client;
    private KVServer kvServer;
    private HttpServer httpServer;

    @BeforeEach
    void init() throws IndexOutOfBoundsException, InterruptedException, IOException {
        kvServer = new KVServer();
        kvServer.start();
        httpServer = HttpServer.create();
        TaskHandler handler = new TaskHandler();
        httpServer.bind(new InetSocketAddress(8080), 0);
        httpServer.createContext("/tasks", handler);
        httpServer.start();
        client = HttpClient.newBuilder().build();
    }

    @AfterEach
    void stop(){
        httpServer.stop(1);
        kvServer.stop();
    }

    private Task getTask(){
        IdGenerator id = new IdGenerator();
        Task task = new Task("task", "task", id.generateId(), Status.NEW);
        return task;
    }

    @Test
    public void testPostTask() throws IndexOutOfBoundsException, InterruptedException{
        URI url = URI.create("http://localhost:8080/tasks/task/");
        Gson gson = new Gson();
        String json = gson.toJson(getTask());
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assertions.assertEquals(200,response.statusCode());
    }

    @Test
    public void testDeleteTask() throws IndexOutOfBoundsException, InterruptedException{
        URI url = URI.create("http://localhost:8080/tasks/task/");
        Gson gson = new Gson();
        Task task = getTask();
        String json = gson.toJson(getClass());
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        URI uri = URI.create("http://localhost:8080/tasks/task/?id=" + task.getId());
        HttpRequest request1 = HttpRequest.newBuilder().uri(uri).DELETE().build();
        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assertions.assertEquals(200,response.statusCode());
    }
}
