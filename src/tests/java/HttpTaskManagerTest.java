package tests.java;

import main.java.model.Status;
import main.java.model.Task;
import main.java.server.KVServer;
import main.java.service.HttpTaskManager;
import main.java.service.IdGenerator;
import main.java.service.InMemoryTaskManager;
import main.java.service.TaskManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {

    public HttpTaskManagerTest() throws URISyntaxException, IOException, InterruptedException {
        super(new HttpTaskManager("http://localhost:8078"));
    }

    @BeforeAll
    static void initialize() throws IOException {
        KVServer base = new KVServer();
        base.start();
    }

    @Test
    void testNewHttp() throws IllegalArgumentException {
        IdGenerator id = new IdGenerator();
        Task task = new Task("task", "task", id.generateId(), Status.NEW);
        manager.createTask(task);
       // TaskManager newManager = new HttpTaskManager("http://localhost:8078");
        int expectedSize = 1;
        assertEquals(expectedSize, manager.getTasks().size(), "загрузка не удалась");
    }
}
