package main.java.service;

import main.java.model.Epic;
import main.java.model.Status;
import main.java.model.SubTask;
import main.java.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private final HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
    public  Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final Map<Integer, SubTask> subTasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private final IdGenerator idGenerator = new IdGenerator();
    private static final HistoryManager historyManager1 = new InMemoryHistoryManager();
    private IdGenerator id = new IdGenerator();

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
        tasks.put(numberTask ,task);
        System.out.println("задача успешно создана");
        System.out.println(tasks);
    }

    @Override
    public void createEpic(Epic epic) {
        epics.put(id.generateId(), epic);
        int numberEpic = idGenerator.generateId();
        epics.put(numberEpic, epic);
        if(epics.isEmpty()){
            System.out.println("ошибка создания");
        }else {
            System.out.println("эпик успешно создан");
            System.out.println(epics);
        }

    }



    @Override
    public void createSubTask(SubTask subTask, Epic epic) {
        subTask.setEpicNumber(epic.getId());
        subTasks.put(id.generateId(), subTask);
        Printer.println("под_задача успешно создана");
    }

    @Override
    public Task getTaskById(int id) {
        if (tasks.isEmpty()) {
            Printer.println("Список задач пуст");
        } else if (!tasks.containsKey(id)) {
            Printer.println("задача:");
            historyManager.add(tasks.get(id));
        } else {
            Printer.println("Такой задачи нет");
        }
        return tasks.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        if (epics.isEmpty()) {
            Printer.println("Список эпиков пуст");
        } else if (epics.containsKey(id)) {
            Printer.println("эпик:");
            historyManager.add(epics.get(id));
        } else {
            Printer.println("Такого эпика нет");
        }
        return epics.get(id);
    }

    @Override
    public SubTask getSubTaskById(int id) {
        if (subTasks.isEmpty() != false) {
            Printer.println("Список под_задач пуст");
        } else if (subTasks.containsKey(id)) {
            Printer.println("под_задача:");
            historyManager.add(subTasks.get(id));
        } else {
            Printer.println("Такой под_задачи нет");
        }
        return subTasks.get(id);
    }

    @Override
    public void deleteTask() {
        tasks.clear();
        Printer.println("Задачи удалены");
    }

    @Override
    public void deleteEpic() {
        epics.clear();
        Printer.println("Эпики уделены");
    }

    @Override
    public void deleteSubTask() {
        subTasks.clear();
        Printer.println("Под_задачи удалены");
    }

    @Override
    public void deleteTaskById(int id) {
        if (tasks.isEmpty()) {
            Printer.println("Список задач пуст");
        } else if (tasks.containsKey(id)) {
            tasks.remove(id);
            Printer.println("Задча удалена");
        } else {
            Printer.println("Такой задачи нет");
        }
    }

    @Override
    public void deleteEpicById(int id) {
        if (epics.isEmpty()) {
            Printer.println("Список эпиков пуст");
        } else if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            List<SubTask> subTasks = epic.getListSubTask();
            for (SubTask i : subTasks) {
                subTasks.remove(i);
            }
            epics.remove(id);
            Printer.println("Эпик удален");
        } else {
            Printer.println("Такого эпика нет");
        }
    }

    @Override
    public void deleteSubTaskById(int id) {
        if (subTasks.isEmpty()) {
            Printer.println("Список под_задач пуст");
        } else if (subTasks.containsKey(id)) {
            subTasks.remove(id);
            Printer.println("Под_задача удалена");
        } else {
            Printer.println("Такой под_задачи нет");
        }
    }

    @Override
    public void deleteAll(){
        tasks.clear();
        epics.clear();
        subTasks.clear();
    }

    @Override
    public Object updateTaskById(Task update) {
        if (tasks.isEmpty()) {
            Printer.println("Список задач пуст");
        } else if (tasks.containsKey(update.getId())) {
            tasks.put(update.getId(), update);
            Printer.println("Обновление выполнено");
            return update;
        } else {
            Printer.println("Такой задачи нет");
        }
        return null;
    }

    @Override
    public Object updateEpicById(Epic update) {
        if (epics.containsKey(update.getId())) {
            Epic epic = epics.get(update.getId());
            update.setStatus(epic.getStatus());
            epics.put(update.getId(), update);
            Printer.println("Обновление выполнено");
            return update;
        }
        return null;
    }

    @Override
    public Object updateSubTaskById(SubTask update) {
        if (subTasks.isEmpty()) {
            Printer.println("Список под_задач пуст");
        } else if (subTasks.containsKey(update.getId())) {
            SubTask subTask = subTasks.get(update.getId());
            update.setEpicNumber(subTask.getEpicNumber());
            subTasks.put(update.getId(), update);
            int numEpic = subTask.getEpicNumber();
            Epic epic = epics.get(numEpic);
            List<SubTask> subTasks = epic.getListSubTask();
            epic.setStatus(calculateStatus(subTasks));
            return update;
        } else {
            Printer.println("Такой под_задачи нет");
        }
        return null;
    }

    public Status calculateStatus(List<SubTask> subTasks1) {
        int statusNEW = 0;
        int statusDone = 0;
        Status newStatus = Status.NEW;
        ArrayList<Status> statuses = new ArrayList<>();
        for (SubTask i : subTasks1) {
            SubTask subTask = subTasks.get(i);
            Status status = subTask.getStatus();
            if (Status.NEW.equals(status)) {
                statusNEW += 1;
            }
            if (Status.DONE.equals(status)) {
                statusDone += 1;
            }

            if (statuses.size() == statusNEW) {
                return newStatus;
            } else if (statuses.size() == statusDone) {
                newStatus = Status.DONE;
            } else {
                newStatus = Status.IN_PROGRESS;
            }
        }
        return Status.IN_PROGRESS;
    }

    @Override
    public void printEpicSubTask(int id) {
        if (epics.isEmpty()) {
            Printer.println("Список эпиков пуст");
        } else if (!epics.containsKey(id)) {
            Printer.println("Такой под_задачи в списке епиков нет");
        } else {
            Epic epic = epics.get(id);
            List<SubTask> subTasks = epic.getListSubTask();
            if (subTasks.size() == 0) {
                Printer.println("У этого эпика нет под_задач");
            } else {
                Printer.println("Список под_задач" + subTasks + " епика" + epics.get(id));
            }
        }
    }

    public HistoryManager getHistoryManager() {
        return inMemoryHistoryManager;
    }

    @Override
    public List<Task> getHistoryList(){
        return  historyManager.getHistory();
    }
}
