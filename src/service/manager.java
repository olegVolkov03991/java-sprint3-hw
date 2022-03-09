package service;

import model.*;

import java.util.*;

import static model.Status.*;
import static service.Printer.*;

public class Manager {
    // я бех понятия какой файл должен быть с большой буквы, ведт они все и так с большой, названия пакетов с маленькой
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, SubTask> subTasks = new HashMap<>();
    private IdGenerator idGenerator = new IdGenerator();

    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    public Map<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    public Task createTask(String name, String description) {
         Task task = new Task(name, description, idGenerator.generateId(), NEW);
        tasks.put(task.getId(), task);
        println("Задача создана, ее название" + name);
        return task;
    }

    public Epic createEpic(String name, String description) {
        Epic epic = new Epic(name, description, idGenerator.generateId(), NEW, (List<SubTask>) subTasks);
        epics.put(epic.getId(), epic);
        println("Эпик создан, его название" + name);
        return epic;
    }

    public SubTask createSubTask(String name, String description, int id) {
        SubTask subTask = new SubTask(name, description, id, NEW);
        subTasks.put(subTask.getId(), subTask);
        Set<Integer> setKeysTask = epics.keySet();
        if (setKeysTask.size() == 0) {
            println("Такого эпика нет!");
        } else {
            println("Под_задача создана, ее название" + name);
        }
        return subTask;
    }

    public Task getTaskById(int id) {
        if (tasks.isEmpty()) {
            println("Список задач пуст");
        } else if (!tasks.containsKey(id)) {
            println("задача:");
        } else {
            println("Такой задачи нет");
        }
        return tasks.get(id);
    }

    public Epic getEpicById(int id) {
        if (epics.isEmpty()) {
            println("Список эпиков пуст");
        } else if (epics.containsKey(id)) {
            println("эпик:");
        } else {
            println("Такого эпика нет");
        }
        return epics.get(id);

    }

    public SubTask getSubTaskById(int id) {
        if (subTasks.isEmpty() != false) {
            println("Список под_задач пуст");
        } else if (subTasks.containsKey(id)) {
            println("под_задача:");
        } else {
            println("Такой под_задачи нет");
        }
        return subTasks.get(id);
    }

    public List<Task> getTasks(Map<Integer, Task> taskMap) {
        List<Task> taskList = new ArrayList<>();
        Set<Integer> setKeys = taskMap.keySet();
        for (int i : setKeys) {
            Task task = taskMap.get(i);
            taskList.add(task);
        }
        return taskList;
    }

    public List<Epic> getEpic(Map<Integer, Epic> epicMap) {
        List<Epic> epicList = new ArrayList<>();
        Set<Integer> setKeys = epicMap.keySet();
        for (int i : setKeys) {
            Epic epic = (Epic) epicMap.get(i);
            epicList.add(epic);
        }
        return epicList;
    }

    public List<SubTask> getSubTask(Map<Integer, SubTask> subTaskMap) {
        List<SubTask> subTaskList = new ArrayList<>();
        Set<Integer> setKeys = subTaskMap.keySet();
        for (int i : setKeys) {
            SubTask subTask = subTaskMap.get(i);
            subTaskList.add(subTask);
        }
        return subTaskList;
    }

    public void deleteTask() {
        tasks.clear();
        println("Задачи удалены");
    }

    public void deleteEpic() {
        tasks.clear();
        println("Эпики уделены");
    }

    public void deleteSubTask() {
        tasks.clear();
        println("Под_задачи удалены");
    }

    public void deleteTaskById(int id) {
        if (tasks.isEmpty()) {
            println("Список задач пуст");
        } else if (tasks.containsKey(id)) {
            tasks.remove(id);
            println("Задча удалена");
        } else {
            println("Такой задачи нет");
        }
    }

    public void deleteEpicById(int id) {
        if (epics.isEmpty()) {
            println("Список эпиков пуст");
        } else if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            List<SubTask> subTasks = epic.getListSubTask();
            for (SubTask i : subTasks) {
                subTasks.remove(i);
            }
            epics.remove(id);
            println("Эпик удален");
        } else {
            println("Такого эпика нет");
        }
    }

    public void deleteSubTaskById(int id) {
        if (subTasks.isEmpty()) {
            println("Список под_задач пуст");
        } else if (subTasks.containsKey(id)) {
            subTasks.remove(id);
            println("Под_задача удалена");
        } else {
            println("Такой под_задачи нет");
        }
    }

    public Map<Integer, Task> updateTaskById(Task update) {
        if (tasks.isEmpty()) {
            println("Список задач пуст");
        } else if (tasks.containsKey(update.getId())) {
            tasks.put(update.getId(), update);
            println("Обновление выполнено");
        } else {
            println("Такой задачи нет");
        }
        return tasks;
    }

    public void updateEpicById(Epic update) {
        if (epics.containsKey(update.getId())) {
            Epic epic = epics.get(update.getId());
            update.setStatus(epic.getStatus());
            epics.put(update.getId(), update);
            println("Обновление выполнено");
        }
    }

    public void updateSubTaskById(SubTask update) {
        if (subTasks.isEmpty()) {
            println("Список под_задач пуст");
        } else if (subTasks.containsKey(update.getId())) {
            SubTask subTask = subTasks.get(update.getId());
            update.setEpicNumber(subTask.getEpicNumber());
            subTasks.put(update.getId(), update);
            int numEpic = subTask.getEpicNumber();
            int statusNEW = 0;
            int statusInProcess = 0;
            int statusDone = 0;
            Epic epic = epics.get(numEpic);
            List<SubTask> subTasks = epic.getListSubTask();
            epic.setStatus(calculateStatus(subTasks));
        } else {
            println("Такой под_задачи нет");
        }
    }

    private Status calculateStatus(List<SubTask> SubTasks) {
        int statusNEW = 0;
        int statusInProcess = 0;
        int statusDone = 0;
        for (SubTask i : SubTasks) {
            SubTask subTask1 = subTasks.get(i);
            Status status = subTask1.getStatus();
            if (statusNEW == subTasks.size()) {
                return NEW;
            }
            if (statusDone == subTasks.size()) {
                return DONE;
            }
        }
        return IN_PROGRESS;
    }

    public void getEpicOdSubTask(int id) {
        if (epics.isEmpty()) {
            println("Список эпиков пуст");
        } else if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            List<SubTask> subTasks = epic.getListSubTask();
            if (subTasks.size() == 0) {
                println("У этого эпика нет под_задач");
            } else {
                println("Список под_задач" + subTasks);
            }
        } else {
            println("Такого эпика нет");
        }
    }
}

