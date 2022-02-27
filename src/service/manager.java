package service;

import typeTasks.Epic;
import typeTasks.Status;
import typeTasks.SubTask;
import typeTasks.Task;

import java.util.*;

import static typeTasks.Status.*;

public class manager {

    private Integer ID = 1;

    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, SubTask> subTasks = new HashMap<>();

    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    public Map<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    public static void println(String message) {
        System.out.println(message);
    }

    public Task makeTask(String name, String description) {

        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setId(ID++);
        task.setStatus("NEW");
        tasks.put(task.getId(), task);
        println("Задача создана, ее id - " + ID);
        return task;
    }

    public Epic makeEpic(String name, String description) {

        Epic epic = new Epic();
        epic.setName(name);
        epic.setDescription(description);
        epic.setId(ID++);
        epic.setStatus("NEW");
        epics.put(epic.getId(), epic);
        println("Эпик создан, его id - " + ID);
        return epic;
    }

    public SubTask makeSubTask(String name, String description, int id) {

        SubTask subTask = new SubTask();
        subTask.setName(name);
        subTask.setDescription(description);
        subTask.setEpicNumber(id);
        subTask.setStatus("NEW");
        subTasks.put(subTask.getId(), subTask);
        Set<Integer> setKeysTask = epics.keySet();
        if (setKeysTask.size() == 0) {
            println("попробуй ка еще раз");

        } else {
            for (int i : setKeysTask) {
                Epic epic = epics.get(i);
                if (id != epic.getId()) {
                    println("такой задачи нет");
                } else {
                    subTask.setId((ID++));
                    epic.addSubTask(subTask.getId());
                    subTasks.put(subTask.getId(), subTask);
                    println("Задача создана, ее id - " + ID);
                }

            }
        }
        return subTask;
    }

    public void getTaskById(int id) {

        if (tasks.isEmpty()) {
            println("Список задач пуст");
        } else if (tasks.containsKey(id)) {
            System.out.print(tasks.get(id));
        } else {
            println("Такой задачи нет");
        }
    }

    public void getEpicById(int id) {

        if (epics.isEmpty()) {
            println("Список эпиков пуст");
        } else if (epics.containsKey(id)) {
            System.out.print(epics.get(id));
        } else {
            println("Такого эпика нет");
        }
    }

    public void getSubTaskById(int id) {

        if (subTasks.isEmpty() != false) {
            println("Список под_задач пуст");
        } else if (subTasks.containsKey(id)) {
            System.out.print(subTasks.get(id));
        } else {
            println("Такой под_задачи нет");
        }
    }

    public List<Task> getTasc(Map<Integer, Task> taskMap) {
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
            List<Integer> listSubTask = epic.getListSubTask();
            for (int i : listSubTask) {
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
            update.setListSubTask(epic.getListSubTask());
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
            List<Integer> listSubTask = epic.getListSubTask();
            epic.setStatus(String.valueOf(calculateStatus(listSubTask)));

        } else {
            println("Такой под_задачи нет");
        }
    }

    private Status calculateStatus(List listSubTask) {
        int statusNEW = 0;
        int statusInProcess = 0;
        int statusDone = 0;
        for (Object i : listSubTask) {
            SubTask subTask1 = subTasks.get(i);
            String status = subTask1.getStatus();
            if (statusNEW == listSubTask.size()) {
                return NEW;
            }
            if (statusDone == listSubTask.size()) {
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
            List<Integer> listSubTask = epic.getListSubTask();
            if (listSubTask.size() == 0) {
                println("У этого эпика нет под_задач");
            } else {
                println("Список под_задач" + listSubTask);
            }
        } else {
            println("Такого эпика нет");
        }
    }
}

