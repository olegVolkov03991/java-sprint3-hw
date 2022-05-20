package tests;

import model.Status;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.HistoryManager;
import service.IdGenerator;
import service.InMemoryHistoryManager;

import java.util.List;

class InMemoryHistoryManagerTest {
    private IdGenerator id = new IdGenerator();
    private static final HistoryManager historyManager = new InMemoryHistoryManager();
    private final Task task1 = new Task("task1", "task1", id.generateId(), Status.NEW);
    private final Task task2 = new Task("task2", "task2", id.generateId(), Status.NEW);

    @Test
    void add() {
        historyManager.add(task1);
        historyManager.add(task2);
        List<Task> list = List.of(task1, task2);
        Assertions.assertEquals(list, historyManager.getHistory());
    }


    @Test
    void getHistory() {
        List<Task> list = List.of(task1, task2);
        Assertions.assertEquals(list, historyManager.getHistory());
    }
}