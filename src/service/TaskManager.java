package service;

import model.*;

import java.util.*;

public interface TaskManager {

    List<Task> history();

    Map<Integer, Task> getTasks();

    Map<Integer, Epic> getEpics();

    Map<Integer, SubTask> getSubTasks();

    void createTask(String name, String description);

    void createEpic(String name, String description);

    void createSubTask(String name, String description, int id);

    Task getTaskById(int id);

    Epic getEpicById(int id);

    SubTask getSubTaskById(int id);

    void deleteTask();

    void deleteEpic();

    void deleteSubTask();

    void deleteTaskById(int id);

    void deleteEpicById(int id);

    void deleteSubTaskById(int id);

    void updateTaskById(Task update);

    void updateEpicById(Epic update);

    void updateSubTaskById(SubTask update);

    void printEpicSubTask(int id);
}
