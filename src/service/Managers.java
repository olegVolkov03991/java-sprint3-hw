package service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Managers {
    private static HistoryManager historyManager;
    private static TaskManager taskManager;

    public static TaskManager getDefault() throws URISyntaxException, IOException, InterruptedException {
        if (taskManager == null) {
            taskManager = new HTTPTaskManager("http://localhost:8080");
        }
        return taskManager;
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTaskManager getFileBackedTaskManager(File fname) {
        return new FileBackedTaskManager(fname);
    }
}
