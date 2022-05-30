package main.java.service;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpTaskManager  extends  FileBackedTaskManager{

    private final KVTaskClient kvTaskClient;

    public HttpTaskManager(String url){

       // super(null);
        kvTaskClient = new KVTaskClient("http://localhost:8078");
    }

    @Override
    public void saveString(String data){
        try {
            kvTaskClient.put("all", data);
        } catch (Exception e){
            e.printStackTrace();
            throw new ManagerSaveException(e.getMessage());
        }
    }
}
