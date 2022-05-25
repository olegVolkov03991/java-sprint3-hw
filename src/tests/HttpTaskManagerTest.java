package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import main.menu.service.HttpTaskManager;
import main.menu.server.KVServer;
import main.menu.service.Managers;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpTaskManagerTest extends TaskManagerTest {
    private KVServer server;

    public HttpTaskManagerTest(HttpTaskManager manager) {
        super(manager);
    }

    @BeforeEach
    public void createManager() throws IOException, InterruptedException, URISyntaxException {
        super.manager = (HttpTaskManager) Managers.getDefault();
        server.start();
    }

    @AfterEach
    public void stopServer() {
        server.stop();
    }
}
