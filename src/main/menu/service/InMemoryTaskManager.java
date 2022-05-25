package main.menu.service;

import main.menu.model.Task;
import main.menu.model.Epic;
import main.menu.model.SubTask;
import main.menu.model.Status;

import java.util.*;

import static main.menu.model.Status.*;
import static main.menu.service.Printer.println;

public class InMemoryTaskManager implements TaskManager {
    private final HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
    public final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final Map<Integer, SubTask> subTasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private final IdGenerator idGenerator = new IdGenerator();

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
    public void createTask(Task task) {
        int numberTask = idGenerator.generateId();
        tasks.put(numberTask, task);
        System.out.println("задача успешно создана");
    }

    @Override
    public void createEpic(Epic epic) {
        int numberEpic = idGenerator.generateId();
        epics.put(numberEpic, epic);
        System.out.println("эпик успешно создан");
    }

    @Override
    public void createSubTask(SubTask subTask, Epic epic) {

        int numberSubTask = idGenerator.generateId();
        subTask.setEpicNumber(epic.getId());
        subTasks.put(numberSubTask, subTask);
        epics.get(epic.getId()).updateStartTimeAndDuration();
        println("под_задача успешно создана");

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
    public Object updateTaskById(Task update) {
        if (tasks.isEmpty()) {
            println("Список задач пуст");
        } else if (tasks.containsKey(update.getId())) {
            tasks.put(update.getId(), update);
            println("Обновление выполнено");
        } else {
            println("Такой задачи нет");
        }
        return null;
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
    public Object updateSubTaskById(SubTask update) {
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
        return null;
    }

    public Status calculateStatus(List<SubTask> subTasks1) {
        int statusNEW = 0;
        int statusDone = 0;
        Status newStatus = NEW;
        ArrayList<Status> statuses = new ArrayList<>();
        for (SubTask i : subTasks1) {
            SubTask subTask = subTasks.get(i);
            Status status = subTask.getStatus();
            if (NEW.equals(status)) {
                statusNEW += 1;
            }
            if (DONE.equals(status)) {
                statusDone += 1;
            }

            if (statuses.size() == statusNEW) {
                return newStatus;
            } else if (statuses.size() == statusDone) {
                newStatus = DONE;
            } else {
                newStatus = IN_PROGRESS;
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

    public HistoryManager getHistoryManager() {
        return inMemoryHistoryManager;
    }
}
