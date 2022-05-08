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
        final String TASK_IN_LINE_DELIMITER = ",";
        String[] parameters = str.split(TASK_IN_LINE_DELIMITER);
        Task task = null;
        final int ID_COLUMN_INDEX = task.getId();
        final String TYPE_COLUMN_INDEX = parameters[1];
        final String NAME_COLUMN_INDEX = parameters[2];
        final String DESCRIP_COLUMN_INDEX = parameters[4];
        final String task1 = "Task";
        final String subTask = "SubTask";
        final String epic = "Epic";

        switch (TYPE_COLUMN_INDEX) {
            case task1:
                task = new Task(NAME_COLUMN_INDEX, DESCRIP_COLUMN_INDEX, ID_COLUMN_INDEX, Status.NEW);
            case subTask:
                task = new Epic(NAME_COLUMN_INDEX, DESCRIP_COLUMN_INDEX, ID_COLUMN_INDEX, Status.NEW, (List<SubTask>) subTasks);
            case epic:
                task = new SubTask(NAME_COLUMN_INDEX, DESCRIP_COLUMN_INDEX, ID_COLUMN_INDEX, Status.NEW);
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
                    sb.append(task.getId()).append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
                fileWriter.write(sb.toString());
            }
        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
    }

    private static List<String> historyFromString(String string) {
        return Arrays.asList(string.split(","));
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
