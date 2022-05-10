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

    Task task = null;
    public static final String TASK_IN_LINE_DELIMITER = ",";
    public final int ID_COLUMN_INDEX = task.getId();
    public static final int TYPE_COLUMN_INDEX = 1;
    public static final int NAME_COLUMN_INDEX = 2;
    public static final int DESCRIP_COLUMN_INDEX = 4;
    public static final String task1 = "Task";
    public static final String subTask = "SubTask";
    public static final String epic = "Epic";

    private final File fileToSave;

    public FileBackedTaskManager(File fileToSave) {
        this.fileToSave = fileToSave;
    }

    @Override
    public void createTask(String name, String description) {
        super.createTask(name, description);
        save();
    }

    @Override
    public void createEpic(String name, String description) {
        super.createEpic(name, description);
        save();
    }

    @Override
    public void createSubTask(String name, String description, int id) {
        super.createSubTask(name, description, id);
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
    public void updateTaskById(Task update) {
        super.updateTaskById(update);
        save();
    }

    @Override
    public void updateEpicById(Epic update) {
        super.updateEpicById(update);
        save();
    }

    @Override
    public void updateSubTaskById(SubTask update) {
        super.updateSubTaskById(update);
        save();
    }

    @Override
    public void printEpicSubTask(int id) {
        super.printEpicSubTask(id);
        save();
    }

    public Task fromString(String str) {
        String[] parameters = str.split(TASK_IN_LINE_DELIMITER);
        switch (parameters[TYPE_COLUMN_INDEX]) {
            case task1:
                task = new Task(parameters[NAME_COLUMN_INDEX], parameters[DESCRIP_COLUMN_INDEX], ID_COLUMN_INDEX, Status.NEW);
            case subTask:
                task = new Epic(parameters[NAME_COLUMN_INDEX], parameters[DESCRIP_COLUMN_INDEX], ID_COLUMN_INDEX, Status.NEW, (List<SubTask>) subTasks);
            case epic:
                task = new SubTask(parameters[NAME_COLUMN_INDEX], parameters[DESCRIP_COLUMN_INDEX], ID_COLUMN_INDEX, Status.NEW);
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
