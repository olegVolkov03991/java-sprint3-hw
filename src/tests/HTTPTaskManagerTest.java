package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import service.HTTPTaskManager;
import service.KVServer;
import service.Managers;

import java.io.IOException;
import java.net.URISyntaxException;

public class HTTPTaskManagerTest extends TaskManagerTest {
    private KVServer server;

    public HTTPTaskManagerTest(HTTPTaskManager manager) {
        super(manager);
    }

    @BeforeEach
    public void createManager() throws IOException, InterruptedException, URISyntaxException {
        super.manager = (HTTPTaskManager) Managers.getDefault();
        server.start();
    }

    @AfterEach
    public void stopServer() {
        server.stop();
    }
}
