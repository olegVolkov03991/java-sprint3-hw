package tests;

import main.menu.model.Epic;
import main.menu.model.Status;
import main.menu.model.SubTask;
import main.menu.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.menu.service.IdGenerator;
import main.menu.service.TaskManager;

class TaskManagerTest<T extends TaskManager> {
    IdGenerator id = new IdGenerator();
    TaskManager manager;

    public TaskManagerTest(T manager) {
        this.manager = manager;
    }

    @BeforeEach
    void createTaskManager() {
        manager = manager;
    }

    @Test
    void history() {
        Task task = new Task("task", "task", id.generateId(), Status.NEW);
        manager.createTask(task);
        manager.getTaskById(task.getId());
        Task[] expectedHistory = new Task[]{task};
        Task[] historyTask = manager.history().toArray(Task[]::new);
        Assertions.assertArrayEquals(expectedHistory, historyTask);
    }

    @Test
    void getTasks() {
        Task task = new Task("task", "task", id.generateId(), Status.NEW);
        manager.createTask(task);
        manager.createTask(task);
        Assertions.assertEquals(task, manager.getTasks());
    }

    @Test
    void getEpics() {
        Epic epic = new Epic("epic", "epic", id.generateId(), Status.NEW);
        manager.createEpic(epic);
        manager.createEpic(epic);
        Assertions.assertEquals(epic, manager.getEpics());
    }

    @Test
    void getSubTasks() {
        Epic epic = new Epic("epic", "epic", id.generateId(), Status.NEW);
        SubTask subTask = new SubTask("subTask", "subTask", id.generateId(), Status.NEW);
        manager.createSubTask(subTask, epic);
        manager.createSubTask(subTask, epic);
        Assertions.assertEquals(subTask, manager.getSubTasks());
    }

    @Test
    void getTaskById() {
        Task task = new Task("task", "task", id.generateId(), Status.NEW);
        manager.createTask(task);
        Assertions.assertEquals(task, manager.getTaskById(task.getId()));
    }

    @Test
    void getEpicById() {
        Epic epic = new Epic("epic", "epic", id.generateId(), Status.NEW);
        manager.createEpic(epic);
        Assertions.assertEquals(epic, manager.getEpicById(epic.getId()));
    }

    @Test
    void getSubTaskById() {
        Epic epic = new Epic("epic", "epic", id.generateId(), Status.NEW);
        SubTask subTask = new SubTask("subTask", "subTask", id.generateId(), Status.NEW);
        manager.createSubTask(subTask, epic);
        Assertions.assertEquals(subTask, manager.getSubTaskById(subTask.getId()));
    }

    @Test
    void deleteTask() {
        Task task = new Task("task", "task", id.generateId(), Status.NEW);
        manager.createTask(task);
        manager.deleteTask();
        Assertions.assertTrue(manager.getTasks().isEmpty());
    }

    @Test
    void deleteEpic() {
        Epic epic = new Epic("epic", "epic", id.generateId(), Status.NEW);
        manager.createEpic(epic);
        manager.deleteEpic();
        Assertions.assertTrue(manager.getEpics().isEmpty());
    }

    @Test
    void deleteSubTask() {
        Epic epic = new Epic("epic", "epic", id.generateId(), Status.NEW);
        SubTask subTask = new SubTask("subTask", "subTask", id.generateId(), Status.NEW);
        manager.createSubTask(subTask, epic);
        manager.deleteSubTask();
        Assertions.assertTrue(manager.getSubTasks().isEmpty());
    }

    @Test
    void deleteTaskById() {
        Task task = new Task("task", "task", id.generateId(), Status.NEW);
        manager.createTask(task);
        manager.deleteTaskById(task.getId());
        Assertions.assertTrue(manager.getTasks().isEmpty());
    }

    @Test
    void deleteEpicById() {
        Epic epic = new Epic("epic", "epic", id.generateId(), Status.NEW);
        SubTask subTask = new SubTask("subTask", "subTask", id.generateId(), Status.NEW);
        manager.createEpic(epic);
        manager.createSubTask(subTask, epic);
        manager.deleteEpicById(epic.getId());
        Assertions.assertTrue(manager.getEpics().isEmpty());
    }

    @Test
    void deleteSubTaskById() {
        Epic epic = new Epic("epic", "epic", id.generateId(), Status.NEW);
        SubTask subTask = new SubTask("subTask", "subTask", id.generateId(), Status.NEW);
        manager.createEpic(epic);
        manager.createSubTask(subTask, epic);
        manager.deleteSubTaskById(subTask.getId());
        Assertions.assertTrue(epic.getListSubTask().isEmpty());
        Assertions.assertNull(manager.updateSubTaskById(new SubTask("помыться", "вымыться", subTask.getId(), Status.NEW)));
    }

    @Test
    void updateTaskById() {
        Task task = new Task("task", "task", id.generateId(), Status.NEW);
        manager.createTask(task);

        Task expectedTask = new Task("купить кота", "кота купить", task.getId(), Status.NEW);

        Assertions.assertEquals(expectedTask, manager.updateTaskById(new Task("купить кота", "кота купить", task.getId(), Status.NEW)));
    }

    @Test
    void updateEpicById() {
        Task task = new Task("task", "task", id.generateId(), Status.NEW);
        manager.createTask(task);

        Task expectedTask = new Task("купить кота", "кота купить", task.getId(), Status.NEW);
        Assertions.assertEquals(expectedTask, manager.updateTaskById(new Task("купить кота", "кота купить", task.getId(), Status.NEW)));
    }

    @Test
    void updateSubTaskById() {
        Epic epic = new Epic("epic", "epic", id.generateId(), Status.NEW);
        SubTask subTask = new SubTask("subTask", "subTask", id.generateId(), Status.NEW);
        manager.createEpic(epic);
        manager.createSubTask(subTask, epic);

        SubTask expectedSubtasks = new SubTask("помыть кота", "кота помыть", subTask.getId(), Status.NEW);

        Assertions.assertEquals(expectedSubtasks, manager.updateSubTaskById(expectedSubtasks), "под_задачи разные");
    }

    @Test
    void createEpic() {
        Epic epic = new Epic("epic", "epic", id.generateId(), Status.NEW);
        manager.createEpic(epic);

        Epic[] expectedAllEpics = new Epic[]{epic};
        Epic[] allEpic = manager.getEpics().values().toArray(new Epic[0]);

        Assertions.assertArrayEquals(expectedAllEpics, allEpic, "Массивы не равны");
    }

    @Test
    void createSubTask() {
        Epic epic = new Epic("epic", "epic", id.generateId(), Status.NEW);
        SubTask subTask = new SubTask("subTask", "subTask", id.generateId(), Status.NEW);
        manager.createEpic(epic);
        manager.createSubTask(subTask, epic);

        SubTask[] expectedAllSubTasks = new SubTask[]{subTask};
        SubTask[] allSubtasks = manager.getSubTasks().values().toArray(new SubTask[0]);

        Assertions.assertArrayEquals(expectedAllSubTasks, allSubtasks, "Массивы не равны");
    }

    @Test
    void createTask() {
        Task task = new Task("task", "task", id.generateId(), Status.NEW);
        manager.createTask(task);

        Task[] expectedAllTasks = new Task[]{task};
        Task[] allTask = manager.getTasks().values().toArray(new Task[0]);

        Assertions.assertArrayEquals(expectedAllTasks, allTask, "массивы не равны");
    }
}