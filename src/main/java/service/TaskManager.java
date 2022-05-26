package main.java.service;

import main.java.model.Epic;
import main.java.model.SubTask;
import main.java.model.Task;

import java.util.*;

public interface TaskManager {

    Map<Integer, Task> getTasks();

    Map<Integer, Epic> getEpics();

    Map<Integer, SubTask> getSubTasks();

    Task getTaskById(int id);

    Epic getEpicById(int id);

    SubTask getSubTaskById(int id);

    void deleteTask();

    void deleteEpic();

    void deleteSubTask();

    void deleteTaskById(int id);

    void deleteEpicById(int id);

    void deleteSubTaskById(int id);

    default void deleteAll() {
    }

    Object updateTaskById(Task update);

    Object updateEpicById(Epic update);

    Object updateSubTaskById(SubTask update);

    void printEpicSubTask(int id);

    void createEpic(Epic epic);

    void createSubTask(SubTask subTask, Epic epic);

    void createTask(Task task);
}
