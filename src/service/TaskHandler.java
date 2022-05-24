package service;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Epic;
import model.SubTask;
import model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class TaskHandler implements HttpHandler {
    private TaskManager manager = Managers.getDefault();
    private HistoryManager history = Managers.getDefaultHistory();
    private static Gson gson = new Gson();

    public TaskHandler() throws URISyntaxException, IOException, InterruptedException {
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();
        String fullUri = httpExchange.getRequestURI().toString();
        String partUri[] = fullUri.split("/");
        long id = -1;

        if(partUri.length > 3){
            id = Long.parseLong(partUri[3].split("id=")[1]);
        }

        switch (method){
            case "GET":
                if(partUri[2].equals("history")){
                    response = gson.toJson(history.getHistory());
                }
                if (partUri[2].equals("task")){
                    response = (id == 1) ? gson.toJson(manager.getTasks()): gson.toJson(manager.getTasks());
                }

                if(partUri[2].equals("epic")){
                    response = (id == 1) ? gson.toJson(manager.getEpics()): gson.toJson(manager.getEpics());
                }

                if (partUri[2].equals("subtask")){
                    response = (id == 1) ? gson.toJson(manager.getSubTasks()): gson.toJson(manager.getSubTasks());
                }
                httpExchange.sendResponseHeaders(response.equals("null") ? 404 : 200,0);
                break;
            case "POST":
                InputStream inputStream = httpExchange.getRequestBody();
                String body = new String(inputStream.readAllBytes());
                if(partUri[2].equals("task")){
                    Task task = gson.fromJson(body, Task.class);
                    if (id == -1){
                        manager.createTask(task);
                    } else {
                        task.getId();
                        manager.updateTaskById(task);
                    }
                }
                if(partUri[2].equals("epic")){
                    Epic epic = gson.fromJson(body, Epic.class);
                    if(id == -1){
                        manager.createEpic(epic);
                    } else {
                        epic.getId();
                        manager.updateEpicById(epic);
                    }
                }
                if(partUri[2].equals("subtask")){
                    SubTask subTask = gson.fromJson(body, SubTask.class);
                    Epic epic = gson.fromJson(body, Epic.class);
                    if(id == -1){
                        manager.createSubTask(subTask, epic);
                    } else {
                        subTask.getId();
                        manager.updateSubTaskById(subTask);
                    }
                }
                httpExchange.sendResponseHeaders(201, 0);
                break;
            case "DELETE":
                if (partUri[2].equals("task")){
                    if((id == -1)) manager.deleteTask();
                }
                if(partUri[2].equals("epic")){
                    if((id == -1)) manager.deleteEpic();
                }
                if (partUri[2].equals("subtask")){
                    if((id == -1))manager.deleteSubTask();
                }
                httpExchange.sendResponseHeaders(204, 0);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + method);
        }
        try(OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
