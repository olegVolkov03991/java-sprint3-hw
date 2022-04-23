package service;

import model.Task;
import model.Epic;
import model.SubTask;
import model.Status;

import java.util.*;

import static model.Status.*;
import static service.Printer.println;

public class InMemoryTaskManager implements TaskManager {
    public final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final Map<Integer, SubTask> subTasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private IdGenerator idGenerator = new IdGenerator();

    @Override
    public List<Task> history() {
        return historyManager.getHistory();
    }

    @Override
    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    @Override
    public Map<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public Task createTask(String name, String description) {
        Task task = new Task(name, description, idGenerator.generateId(), NEW);
        tasks.put(task.getId(), task);
        println("Задача создана, ее название" + name);
        return task;
    }

    @Override
    public Epic createEpic(String name, String description) {
        Epic epic = new Epic(name, description, idGenerator.generateId(), NEW, (List<SubTask>) subTasks);
        epics.put(epic.getId(), epic);
        epics.put(epic.getId(), epic);
        println("Эпик создан, его название" + name);
        return epic;
    }

    @Override
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

    @Override
    public Task getTaskById(int id) {
        if (tasks.isEmpty()) {
            println("Список задач пуст");
        } else if (!tasks.containsKey(id)) {
            println("задача:");
            historyManager.add(tasks.get(id));
        } else {
            println("Такой задачи нет");
        }
        return tasks.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        if (epics.isEmpty()) {
            println("Список эпиков пуст");
        } else if (epics.containsKey(id)) {
            println("эпик:");
            historyManager.add(epics.get(id));
        } else {
            println("Такого эпика нет");
        }
        return epics.get(id);
    }

    @Override
    public SubTask getSubTaskById(int id) {
        if (subTasks.isEmpty() != false) {
            println("Список под_задач пуст");
        } else if (subTasks.containsKey(id)) {
            println("под_задача:");
            historyManager.add(subTasks.get(id));
        } else {
            println("Такой под_задачи нет");
        }
        return subTasks.get(id);
    }

    @Override
    public void deleteTask() {
        tasks.clear();
        println("Задачи удалены");
    }

    @Override
    public void deleteEpic() {
        tasks.clear();
        println("Эпики уделены");
    }

    @Override
    public void deleteSubTask() {
        tasks.clear();
        println("Под_задачи удалены");
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
    public void updateEpicById(Epic update) {
        if (epics.containsKey(update.getId())) {
            Epic epic = epics.get(update.getId());
            update.setStatus(epic.getStatus());
            epics.put(update.getId(), update);
            println("Обновление выполнено");
        }
    }

    @Override
    public void updateSubTaskById(SubTask update) {
        if (subTasks.isEmpty()) {
            println("Список под_задач пуст");
        } else if (subTasks.containsKey(update.getId())) {
            SubTask subTask = subTasks.get(update.getId());
            update.setEpicNumber(subTask.getEpicNumber());
            subTasks.put(update.getId(), update);
            int numEpic = subTask.getEpicNumber();
            Epic epic = epics.get(numEpic);
            List<SubTask> subTasks = epic.getListSubTask();
            epic.setStatus(calculateStatus(subTasks));
        } else {
            println("Такой под_задачи нет");
        }
    }

    public Status calculateStatus(List<SubTask> SubTasks) {
        int statusNEW = 0;
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

    @Override
    public void printEpicSubTask(int id) {
        if (epics.isEmpty()) {
            println("Список эпиков пуст");
        } else if (!epics.containsKey(id)) {
            println("Такой под_задачи в списке епиков нет");
        } else {
            Epic epic = epics.get(id);
            List<SubTask> subTasks = epic.getListSubTask();
            if (subTasks.size() == 0) {
                println("У этого эпика нет под_задач");
            } else {
                println("Список под_задач" + subTasks + " епика" + epics.get(id));
            }
        }
    }
}
