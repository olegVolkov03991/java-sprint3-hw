package main.java.service;

import main.java.model.Epic;
import main.java.model.Status;
import main.java.model.SubTask;
import main.java.model.Task;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public abstract class FileBackedTaskManager extends InMemoryTaskManager {

    public static final String typeSubTask = "subTask";
    public static final String typeTask = "task";
    public static final String typeEpic = "epic";
    public static final String TASK_IN_LINE_DELIMITER = ",";
    private static final String fileToSave = "resources/file.csv";

//    public FileBackedTaskManager(String fileToSave) {
//        this.fileToSave = fileToSave;
//    }

//    @Override
//    public void createTask(Task task){
//        super.createTask(task);
//        save();
//    }

//    @Override
//    public void createEpic(Epic epic){
//        super.createEpic(epic);
//        save();
//    }

//    @Override
//    public void createSubTask(SubTask subTask, Epic epic){
//        super.createSubTask(subTask, epic);
//        save();
//    }

//    @Override
//    public void deleteTask() {
//        super.deleteTask();
//        save();
//    }
//
//    @Override
//    public void deleteEpic() {
//        super.deleteEpic();
//        save();
//
//    }

//    @Override
//    public void deleteSubTask() {
//        super.deleteSubTask();
//        save();
//
//    }

//    @Override
//    public void deleteTaskById(int id) {
//        super.deleteTaskById(id);
//        save();
//
//    }
//
//    @Override
//    public void deleteEpicById(int id) {
//        super.deleteEpicById(id);
//        save();
//
//    }
//
//    @Override
//    public void deleteSubTaskById(int id) {
//        super.deleteSubTaskById(id);
//        save();
//    }

//    @Override
//    public void deleteAll(){
//        super.deleteAll();
//        save();
//    }

//    @Override
//    public Object updateTaskById(Task update) {
//        super.updateTaskById(update);
//        save();
//        return null;
//    }
//
//    @Override
//    public Object updateEpicById(Epic update) {
//        super.updateEpicById(update);
//        save();
//        return null;
//    }
//
//    @Override
//    public Object updateSubTaskById(SubTask update) {
//        super.updateSubTaskById(update);
//        save();
//        return null;
//    }

    @Override
    public void printEpicSubTask(int id) {
        super.printEpicSubTask(id);
        save();
    }

    public static String toString(HistoryManager manager){
        if(!manager.getHistory().isEmpty()){
            StringBuilder stb = new StringBuilder();
            for(Task task : manager.getHistory()){
                String id = String.valueOf(task.getId());
                stb.append(id).append(",");
            }
            return stb.deleteCharAt(stb.length() -2).toString();
        }
        return null;
    }

    public void save() throws ManagerSaveException {
        try(FileWriter out = new FileWriter(fileToSave, StandardCharsets.UTF_8)){
            out.write("id,type,description,status\n");
            for(Epic epic : super.getEpics().values()){
                out.write(epic.toString());
                for(SubTask subTask : super.getSubTasks().values()){
                    out.write(subTask.toString());
                }
            }
            for(Task task : super.getTasks().values()){
                out.write(task.toString());
            }
            if(toString(super.getHistoryManager()) != null){
                out.write("\n");
                out.write(toString(super.getHistoryManager()));
            }
            out.write("\n");

            List<String> historyList = new ArrayList<>();
            List<Task> listHistory = getHistoryList();
            for(Task task : listHistory){
                historyList.add(String.valueOf(task.getId()));
            }
            out.write(String.join(",", historyList));

            System.out.println("сохранено");

        }catch (IOException e){
            throw new ManagerSaveException("ошибка автосохранения");
        }
    }

//    public Task fromString(String str){
//        String[] row = str.split(",");
//        int id = Integer.parseInt(row[0]);
//        String type = row[1];
//        String name = row[2];
//        String status = row[3];
//        String description = row[4];
//        SubTask subTask = null;
//
//        if (Objects.equals(type, String.valueOf(Type.task))) {
//            return new Task(name, description, id, Status.valueOf(status));
//
//        } else if (Objects.equals(type, String.valueOf(Type.epic))) {
//            return new Epic(name, description, id, Status.valueOf(status));
//
//        } else if (Objects.equals(type, String.valueOf(Type.subTask))) {
//            subTask = new SubTask(name, description, id, Status.valueOf(status);
//            if (row.length == 7) {
//                int idEpic = Integer.parseInt(row[5]);
//                subTask.setEpic((Epic) getTaskById(idEpic));
//            }
//            return subTask;
//        } else return null;
//    }

//    public static FileBackedTaskManager loadFromFile(String file){
//        FileBackedTaskManager fbtm = new FileBackedTaskManager();
//        String[] fileArrayHist = new String[0];
//        try {
//            String fileString = Files.readString(Path.of(file));
//            String[] fileArray = fileString.split("\n");
//            fileArrayHist = fileArray;
//            for(int i = 1; i < fileArray.length -2; i++){
//                Task task = fbtm.fromString(fileArray[i]);
//            }
//        } catch (IOException e){
//            e.printStackTrace();;
//        } catch (NumberFormatException num){
//            throw new NumberFormatException("Пустой файл");
//        }
//        try {
//            List<Integer> integerList = InMemoryHistoryManager.fromString(fileArrayHist[fileArrayHist.length - 1]);
//            for(Integer integer : integerList){
//                fbtm.getTaskById(Integer);
//            }
//        } catch (NumberFormatException numHirst){
//            throw new NumberFormatException("пусто");
//        }
//        return fbtm;
//    }

    private static List<String> historyFromString(String string) {
        return Arrays.asList(string.split(TASK_IN_LINE_DELIMITER));
    }
}
