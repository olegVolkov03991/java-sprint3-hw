package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.io.*;
import java.net.Proxy;
import java.nio.file.Files;
import java.util.*;

public class FileBackedTaskManager extends InMemoryTaskManager {

    public static final String TABLE_TITLE_STRING = "id,type,name,status,description,epic";

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

//    public static Task fromString(String value) {
//        String[] strings = value.split(",");
//        var id = Integer.parseInt(strings[0]);
//        String clazz = strings[1];
//        var name = strings [2];
//        String statusStr = strings[3];
//        String description = strings[4];
//        Status status;
//        if("NEW".equals(statusStr)){
//            status = (Status.NEW);
//        } else if("IN_PROGRESS".equals(statusStr)){
//            status = (Status.IN_PROGRESS);
//        } else {
//            status =  Status.DONE;
//        }
//
//        if(strings[1].equals("Task")){
//            Task task = new Task(name,description,id,status);
//            task.getId();
//            task.setStatus(status);
//            return task;
//        } else if(strings[1].equals("Epic")){
//            Task task = new SubTask(name,description,id,status);
//            task.getId();
//            task.setStatus(status);
//            return task;
//        } else{
//            Task task = new SubTask(name, description, id, status);
//            task.getId();
//            task.setStatus(status);
//            return task;
//        }
        public Task fromString(String str) {
            String[] parameters = str.split(",");
            String typeTask = parameters[1];
            Task task = null;
            switch (typeTask) {
                case "Task":
                    task = new Task(parameters[2], parameters[4], task.getId(), Status.NEW);
                case "SubTask":
                    task = new Epic(parameters[2], parameters[4], task.getId(), Status.NEW, (List<SubTask>) subTasks);
                case "Epic":
                    task = new SubTask(parameters[2], parameters[4], task.getId(), Status.NEW);
            }
            return task;
        }


    public void save() throws ManagerSaveException {
        try {
            Writer fileWriter = new FileWriter(fileToSave);
            fileWriter.write(TABLE_TITLE_STRING);
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
            if(!super.history().isEmpty()){
                for(Task task : super.history()){
                    sb.append(task.getId()).append(",");
                }
                sb.deleteCharAt(sb.length()-1);
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
