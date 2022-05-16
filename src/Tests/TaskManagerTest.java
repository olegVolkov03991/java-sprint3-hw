package Tests;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.TaskManager;

class TaskManagerTest<T extends TaskManager> {
    private final T object;
    TaskManager taskManager;
    Epic epic;
    SubTask subTask;
    Task task;

    public TaskManagerTest(T object){
        this.object = object;
    }

    @BeforeEach
    void createTaskManager(){
        taskManager = object;
        subTask = new SubTask("купить собаку", "собаку купить", subTask.getId(), Status.NEW);
        epic = new Epic("купить собаку", "собаку купить", epic.getId(), Status.NEW);
        task = new Task("test", "test", task.getId(), Status.NEW);
    }

    @Test
    void history() {
        taskManager.createTask(task);
        taskManager.getTaskById(task.getId());
        Task[]expectedHistory = new Task[]{task};
        Task[]historyTask = taskManager.history().toArray(Task[]::new);
        Assertions.assertArrayEquals(expectedHistory, historyTask);
    }

    @Test
    void getTasks() {
        taskManager.createTask(task);
        taskManager.createTask(task);
        Assertions.assertEquals(task, taskManager.getTasks());
    }

    @Test
    void getEpics() {
        taskManager.createEpic(epic);
        taskManager.createEpic(epic);
        Assertions.assertEquals(epic, taskManager.getEpics());
    }

    @Test
    void getSubTasks() {
        taskManager.createSubTask(subTask, epic);
        taskManager.createSubTask(subTask, epic);
        Assertions.assertEquals(subTask, taskManager.getSubTasks());
    }

    @Test
    void getTaskById() {
        taskManager.createTask(task);
        Assertions.assertEquals(task, taskManager.getTaskById(task.getId()));
    }

    @Test
    void getEpicById() {
        taskManager.createEpic(epic);
        Assertions.assertEquals(epic, taskManager.getEpicById(epic.getId()));
    }

    @Test
    void getSubTaskById() {
        taskManager.createSubTask(subTask, epic);
        Assertions.assertEquals(subTask, taskManager.getSubTaskById(subTask.getId()));
    }

    @Test
    void deleteTask() {
        taskManager.createTask(task);
        taskManager.deleteTask();
        Assertions.assertTrue(taskManager.getTasks().isEmpty());
    }

    @Test
    void deleteEpic() {
        taskManager.createEpic(epic);
        taskManager.deleteEpic();
        Assertions.assertTrue(taskManager.getEpics().isEmpty());
    }

    @Test
    void deleteSubTask() {
        taskManager.createSubTask(subTask, epic);
        taskManager.deleteSubTask();
        Assertions.assertTrue(taskManager.getSubTasks().isEmpty());
    }

    @Test
    void deleteTaskById() {
        taskManager.createTask(task);
        taskManager.deleteTaskById(task.getId());
        Assertions.assertTrue(taskManager.getTasks().isEmpty());
    }

    @Test
    void deleteEpicById() {
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask, epic);
        taskManager.deleteEpicById(epic.getId());
        Assertions.assertTrue(taskManager.getEpics().isEmpty());
    }

    @Test
    void deleteSubTaskById() {
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask, epic);
        taskManager.deleteSubTaskById(subTask.getId());
        Assertions.assertTrue(epic.getListSubTask().isEmpty());
        Assertions.assertNull(taskManager.updateSubTaskById(new SubTask("помыться", "вымыться", subTask.getId(), Status.NEW)));
    }

    @Test
    void updateTaskById() {
        taskManager.createTask(task);

        Task expectedTask = new Task("купить кота", "кота купить", task.getId(), Status.NEW);

        Assertions.assertEquals(expectedTask, taskManager.updateTaskById( new Task("купить кота", "кота купить", task.getId(), Status.NEW)));
    }

    @Test
    void updateEpicById() {
        taskManager.createTask(task);

        Task expectedTask = new Task("купить кота", "кота купить", task.getId(), Status.NEW);
        Assertions.assertEquals(expectedTask, taskManager.updateTaskById(new Task("купить кота", "кота купить", task.getId(), Status.NEW)));
    }

    @Test
    void updateSubTaskById() {

        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask, epic);

        SubTask expectedSubtasks = new SubTask("помыть кота", "кота помыть", subTask.getId(), Status.NEW);

        Assertions.assertEquals(expectedSubtasks, taskManager.updateSubTaskById(expectedSubtasks), "под_задачи разные");
    }

    @Test
    void printEpicSubTask() {

    }

    @Test
    void createEpic() {
        taskManager.createEpic(epic);

        Epic[]expectedAllEpics = new Epic[]{epic};
        Epic[]allEpic = taskManager.getEpics().values().toArray(new Epic[0]);

        Assertions.assertArrayEquals(expectedAllEpics, allEpic, "Массивы не равны");
    }

    @Test
    void createSubTask() {
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask, epic);

        SubTask[] expectedAllSubTasks = new SubTask[]{subTask};
        SubTask[] allSubtasks = taskManager.getSubTasks().values().toArray(new SubTask[0]);

        Assertions.assertArrayEquals(expectedAllSubTasks, allSubtasks, "Массивы не равны");
    }

    @Test
    void createTask() {
        taskManager.createTask(task);

        Task[] expectedAllTasks = new Task[]{task};
        Task[] allTask = taskManager.getTasks().values().toArray(new Task[0]);

        Assertions.assertArrayEquals(expectedAllTasks, allTask, "массивы не равны");
    }
}