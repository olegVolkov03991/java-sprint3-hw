package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class FileBackedTaskManager extends InMemoryTaskManager {

    public static final String QWE = "id,type,name,status,description,epic";

    private final File fileToSave;

    public FileBackedTaskManager(File fileToSave, HistoryManager defaultHistory) {
        this.fileToSave = fileToSave;
    }

    @Override
    public Task createTask(String name, String description) {
        super.createTask(name, description);
        save();
        return null;
    }

    @Override
    public Epic createEpic(String name, String description) {
        super.createEpic(name, description);
        save();
        return null;
    }

    @Override
    public SubTask createSubTask(String name, String description, int id) {
        super.createSubTask(name, description, id);
        save();
        return null;
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
    public Map<Integer, Task> updateTaskById(Task update) {
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
    public void updateSubTaskById(SubTask update) {
        super.updateSubTaskById(update);
        save();
    }

    @Override
    public void printEpicSubTask(int id) {
        super.printEpicSubTask(id);
        save();
    }

    static String toString(List<Task> history) {
        return history.stream().map(i -> String.valueOf(i.getId())).collect(Collectors.joining(","));

    }

    public static List<Task> fromString(String value) {
        List<Task> tasks = new ArrayList<>();
        String[] strings = value.split(System.lineSeparator());
        for (var i = 1; i < strings.length - 2; i++) {
            tasks.add(Task.fromString(strings[i]));
        }
        return tasks;
    }

    public void save() throws ManagerSaveException {
        try {
            Writer fileWriter = new FileWriter(fileToSave);
            fileWriter.write(QWE);
            for (Task task : getTasks().values()) {
                fileWriter.write(task.toString());
            }
            for (SubTask epic : getSubTasks().values()) {
                fileWriter.write(epic.toString());
            }
            for (Task task : getSubTasks().values()) {
                fileWriter.write(task.toString());
            }
            fileWriter.write("");
            fileWriter.write(FileBackedTaskManager.toString(history()));
            fileWriter.close();
        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
    }

    public static List<Integer> historyFromString(String value) {
        String[] strings = value.split(System.lineSeparator());
        String[] values = strings[strings.length - 1].split(",");
        return Arrays.stream(values).map(Integer::valueOf).collect(Collectors.toList());
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        var manager = new FileBackedTaskManager(file, Managers.getDefaultHistory());
        try {
            List<Task> tasks = fromString(Files.readString(file.toPath()));
            tasks.sort(Comparator.comparing(Task::getName));
            List<Integer> history = historyFromString(Files.readString(file.toPath()));
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
