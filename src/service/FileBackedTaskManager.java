package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FileBackedTaskManager extends InMemoryTaskManager {

    public static final String typeSubTask = "subTask";
    public static final String typeTask = "task";
    public static final String typeEpic = "epic";
    public static final String TASK_IN_LINE_DELIMITER = ",";
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
            for(Epic epic : getEpics().values()){
                out.write(epic.toString());
                for(SubTask subTask : epic.getListSubTask()){
                    out.write(subTask.toString());
                }
            }
            for(Task task : getTasks().values()){
                out.write(task.toString());
            }
            if(toString(super.getHistoryManager()) != null){
                out.write("\n");
                out.write(toString(super.getHistoryManager()));
            }

        }catch (IOException e){
            throw new ManagerSaveException("ошибка автосохранения");
        }
    }

    protected void saveString(String data){
        try(var w = new PrintWriter(new FileOutputStream(fileToSave))){
            w.println(data);
            w.flush();
        } catch (IOException e){
            throw new ManagerSaveException(e.getMessage());
        }
    }

    private static List<String> historyFromString(String string) {
        return Arrays.asList(string.split(TASK_IN_LINE_DELIMITER));
    }
}
