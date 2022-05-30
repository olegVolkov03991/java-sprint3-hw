package tests.java;

import main.java.model.Epic;
import main.java.model.Status;
import main.java.model.SubTask;
import main.java.model.Task;
import main.java.service.IdGenerator;
import main.java.service.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class TaskManagerTest<T extends TaskManager> {

   private final IdGenerator id = new IdGenerator();
    T manager;

    public TaskManagerTest(T manager) {
        this.manager = manager;
    }

    @AfterEach
    void afterEach(){
        manager.deleteAll();
    }

    @Test
    void getTasks() {
        Task task = new Task("task", "task", id.generateId(), Status.NEW);
        manager.createTask(task);
        Map<Integer, Task> test = new HashMap<>();
        test.put(1 ,task);
        assertEquals(test, manager.getTasks());
    }

    @Test
    void getEpics() {
        Epic epic = new Epic("epic", "epic", id.generateId(), Status.NEW);
        manager.createEpic(epic);
        Map<Integer, Epic> test = new HashMap<>();
        test.put(1 ,epic);
        assertEquals(test, manager.getEpics());
    }

    @Test
    void getSubTasks() {
        Epic epic = new Epic("epic", "epic", id.generateId(), Status.NEW);
        SubTask subTask = new SubTask("subTask", "subTask", id.generateId(), Status.NEW);
        manager.createSubTask(subTask, epic);
        Map<Integer, SubTask> test = new HashMap<>();
        test.put(1 ,subTask);
        assertEquals(test, manager.getSubTasks());
    }

    @Test
    void getTaskById() {
        Task task = new Task("task", "task", id.generateId(), Status.NEW);
        manager.createTask(task);
        assertEquals(task, manager.getTaskById(task.getId()));
    }

    @Test
    void getEpicById() {
        Epic epic = new Epic("epic", "epic", id.generateId(), Status.NEW);
        manager.createEpic(epic);
        assertEquals(epic, manager.getEpicById(epic.getId()));
    }

    @Test
    void deleteTask() {
        Task task = new Task("task", "task", id.generateId(), Status.NEW);
        manager.createTask(task);
        manager.deleteTask();
        assertTrue(manager.getTasks().isEmpty());
    }

    @Test
    void deleteEpic() {
        Epic epic = new Epic("epic", "epic", id.generateId(), Status.NEW);
        manager.createEpic(epic);
        manager.deleteEpic();
        assertTrue(manager.getEpics().isEmpty());
    }

    @Test
    void deleteSubTask() {
        Epic epic = new Epic("epic", "epic", id.generateId(), Status.NEW);
        SubTask subTask = new SubTask("subTask", "subTask", id.generateId(), Status.NEW);
        manager.createSubTask(subTask, epic);
        manager.deleteSubTask();
        assertEquals(manager.getSubTasks().isEmpty() ,manager.getSubTasks().isEmpty());
    }

    @Test
    void deleteTaskById() {
        Task task = new Task("task", "task", id.generateId(), Status.NEW);
        manager.createTask(task);
        manager.deleteTaskById(task.getId());
        assertEquals(manager.getTasks().isEmpty(), manager.getSubTasks().isEmpty());
    }

    @Test
    void deleteEpicById() {
        Epic epic = new Epic("epic", "epic", id.generateId(), Status.NEW);
        SubTask subTask = new SubTask("subTask", "subTask", id.generateId(), Status.NEW);
        manager.createEpic(epic);
        manager.createSubTask(subTask, epic);
        manager.deleteEpicById(epic.getId());
        assertTrue(manager.getEpics().isEmpty());
    }

    @Test
    void deleteSubTaskById() {
        Epic epic = new Epic("epic", "epic", id.generateId(), Status.NEW);
        SubTask subTask = new SubTask("subTask", "subTask", id.generateId(), Status.NEW);
        manager.createEpic(epic);
        manager.createSubTask(subTask, epic);
        manager.deleteSubTaskById(subTask.getId());
        assertTrue(epic.getListSubTask().isEmpty());
        Assertions.assertNull(manager.updateSubTaskById(new SubTask("помыться", "вымыться", subTask.getId(), Status.NEW)));
    }

    @Test
    void updateTaskById() {
        Task task = new Task("task", "task", id.generateId(), Status.NEW);
        manager.createTask(task);

        Task expectedTask = new Task("купить кота", "кота купить", task.getId(), Status.NEW);

        assertEquals(expectedTask, manager.updateTaskById(expectedTask));
    }

    @Test
    void updateEpicById() {
        Epic epic = new Epic("task", "task", id.generateId(), Status.NEW);
        manager.createTask(epic);

        Task expectedTask = new Task("купить кота", "кота купить", epic.getId(), Status.NEW);
        assertEquals(expectedTask, manager.updateTaskById(expectedTask));
    }

    @Test
    void updateSubTaskById() {
        Epic epic = new Epic("epic", "epic", id.generateId(), Status.NEW);
        SubTask subTask = new SubTask("subTask", "subTask", id.generateId(), Status.NEW);
        manager.createEpic(epic);
        manager.createSubTask(subTask, epic);

        SubTask expectedSubtasks = new SubTask("помыть кота", "кота помыть", subTask.getId(), Status.NEW);

        assertEquals(expectedSubtasks, manager.updateSubTaskById(expectedSubtasks));
    }

    @Test
    void createEpic() {
        Epic epic = new Epic("epic", "epic", id.generateId(), Status.NEW);
        manager.createEpic(epic);
        Map<Integer, Epic> test = new HashMap<>();
        test.put(1, epic);
        assertEquals(test, manager.getEpics());
    }

    @Test
    void createSubTask() {
        Epic epic = new Epic("epic", "epic", id.generateId(), Status.NEW);
        SubTask subTask = new SubTask("subTask", "subTask", id.generateId(), Status.NEW);
        manager.createSubTask(subTask, epic);
        Map<Integer, SubTask> test = new HashMap<>();
        test.put(1 ,subTask);
        assertEquals(test, manager.getSubTasks());
    }

    @Test
    void createTask(){
        Task task = new Task("task", "task", 1, Status.NEW);
        manager.createTask(task);
        Map<Integer, Task> test = new HashMap<>();
        test.put(1 ,task);
        assertEquals(test, manager.getTasks());
    }
}