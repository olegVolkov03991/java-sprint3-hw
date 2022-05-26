package tests.java;

import main.java.model.Status;
import main.java.model.Task;
import main.java.service.TaskManager;
import main.java.service.HttpTaskManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskManagerTest extends TaskManagerTest{

    public HttpTaskManagerTest(){
    }
    @Test
    void testNewHttp() throws URISyntaxException, IOException, InterruptedException {
        Task task = new Task("task", "task", id.generateId(), Status.NEW);
        manager.createTask(task);
        TaskManager newManager = new HttpTaskManager("http://localhost:8078");
        int expectedSize = 1;
        assertEquals(expectedSize, manager.getTasks().size(), "хагузка не удалась");
    }
}
