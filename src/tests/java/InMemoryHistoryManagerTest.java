package tests.java;

import main.java.model.Status;
import main.java.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import main.java.service.HistoryManager;
import main.java.service.IdGenerator;
import main.java.service.InMemoryHistoryManager;

import java.util.List;

class InMemoryHistoryManagerTest {
    private IdGenerator id = new IdGenerator();
    private static final HistoryManager historyManager = new InMemoryHistoryManager();
    private final Task task1 = new Task("task1", "task1", id.generateId(), Status.NEW);

    @Test
    void testAddTaskInHisory() {
        historyManager.add(task1);
        List<Task> list = List.of(task1);
        Assertions.assertEquals(list, historyManager.getHistory());
    }
}