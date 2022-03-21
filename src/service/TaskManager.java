package service;

import model.*;

import java.util.*;

public interface TaskManager {

    Map<Integer, Task> getTasks();

    Map<Integer, Epic> getEpics();

    Map<Integer, SubTask> getSubTasks();

    Task createTask(String name, String description);

    Epic createEpic(String name, String description);

    SubTask createSubTask(String name, String description, int id);

    Task getTaskById(int id);

    Epic getEpicById(int id);

    SubTask getSubTaskById(int id);

    List<Task> getTasks(Map<Integer, Task> taskMap);

    List<Epic> getEpic(Map<Integer, Epic> epicMap);

    List<SubTask> getSubTask(Map<Integer, SubTask> subTaskMap);

    void deleteTask();

    void deleteEpic();

    void deleteSubTask();

    void deleteTaskById(int id);

    void deleteEpicById(int id);

    void deleteSubTaskById(int id);

    Map<Integer, Task> updateTaskById(Task update);

    void updateEpicById(Epic update);

    void updateSubTaskById(SubTask update);

    void printEpicSubTask(int id);
}
