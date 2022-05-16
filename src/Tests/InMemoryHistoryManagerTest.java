package Tests;

import model.Status;
import model.Task;
import org.junit.jupiter.api.Test;
import service.InMemoryHistoryManager;


class InMemoryHistoryManagerTest {

    @Test
    void deleteAllTasks(){
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        Task task1 = null;
        task1 = new Task("task1", "task1", task1.getId(), Status.NEW);
        inMemoryHistoryManager.add(task1);
    }

    @Test
    void linkLast() {
    }

    @Test
    void getTasks() {
    }

    @Test
    void add() {
    }

    @Test
    void deleteAll() {
    }

    @Test
    void removeNode() {
    }

    @Test
    void getHistory() {
    }
}