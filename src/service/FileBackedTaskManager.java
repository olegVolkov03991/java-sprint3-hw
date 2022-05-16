package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class FileBackedTaskManager extends InMemoryTaskManager {

    public static final String COLUMNS = "id,type,name,status,description,epic";

    public static final String typeSubTask = "SubTask";
    public static final String typeTask = "Task";
    public static final String typeEpic = "Epic";
    public static final String TASK_IN_LINE_DELIMITER = ",";
    public static final int DESCRIPTION_COLUMN_INDEX = 4;
    public static final int TYPE_COLUMN_INDEX = 1;
    public static final int NAME_COLUMN_INDEX = 2;
    private final File fileToSave;

    public FileBackedTaskManager(File fileToSave) {
        this.fileToSave = fileToSave;
    }

    @Override
    public void createTask(Task task){
        super.createTask(task);
        save();
    }

    @Override
    public void createEpic(Epic epic){
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubTask(SubTask subTask, Epic epic){
        super.createSubTask(subTask, epic);
        save();
    }

    @Override
    public void deleteTask() {
        super.deleteTask();
        save();
    }

    @Override
    public void deleteEpic() {
        super.deleteEpic();
        save();

    }

    @Override
    public void deleteSubTask() {
        super.deleteSubTask();
        save();

    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();

    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();

    }

    @Override
    public void deleteSubTaskById(int id) {
        super.deleteSubTaskById(id);
        save();
    }

    @Override
    public void deleteAll(){
        super.deleteAll();
        save();
    }

    @Override
    public Object updateTaskById(Task update) {
        super.updateTaskById(update);
        save();
        return null;
    }

    @Override
    public void updateEpicById(Epic update) {
        super.updateEpicById(update);
        save();
    }

    @Override
    public Object updateSubTaskById(SubTask update) {
        super.updateSubTaskById(update);
        save();
        return null;
    }

    @Override
    public void printEpicSubTask(int id) {
        super.printEpicSubTask(id);
        save();
    }

    public Task fromString(String str) {
        Task task = null;
        String[] parameters = str.split(TASK_IN_LINE_DELIMITER);
        switch (parameters[TYPE_COLUMN_INDEX]) {
            case typeTask:
                task = new Task(parameters[NAME_COLUMN_INDEX], parameters[DESCRIPTION_COLUMN_INDEX], task.getId(), Status.NEW);
            case typeSubTask:
                task = new Epic(parameters[NAME_COLUMN_INDEX], parameters[DESCRIPTION_COLUMN_INDEX], task.getId(), Status.NEW);
            case typeEpic:
                task = new SubTask(parameters[NAME_COLUMN_INDEX], parameters[DESCRIPTION_COLUMN_INDEX], task.getId(), Status.NEW);
            default:
                System.out.println("Ввели не верные данные");
                break;
        }
        return task;
    }

    public void save() throws ManagerSaveException {
        try {
            Writer fileWriter = new FileWriter(fileToSave);
            fileWriter.write(COLUMNS);
            for (Task task : getTasks().values()) {
                fileWriter.write(task.toString());
            }
            for (SubTask epic : getSubTasks().values()) {
                fileWriter.write(epic.toString());
            }
            for (Task task : getSubTasks().values()) {
                fileWriter.write(task.toString());
            }
            StringBuilder sb = new StringBuilder();
            if (!super.history().isEmpty()) {
                for (Task task : super.history()) {
                    sb.append(task.getId()).append(TASK_IN_LINE_DELIMITER);
                }
                sb.deleteCharAt(sb.length() - 1);
                fileWriter.write(sb.toString());
            }
        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
    }

    private static List<String> historyFromString(String string) {
        return Arrays.asList(string.split(TASK_IN_LINE_DELIMITER));
    }

    public FileBackedTaskManager loadFromFile(File file) {
        var manager = new FileBackedTaskManager(file);
        try {
            List<Task> tasks = (List<Task>) fromString(Files.readString(file.toPath()));
            tasks.sort(Comparator.comparing(Task::getName));
            List<String> history = historyFromString(Files.readString(file.toPath()));
            for (Task task : tasks) {
                if (history.contains(task.getId())) {
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
        return manager;
    }
}
