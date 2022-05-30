package main.java.service;

import main.java.model.Epic;
import main.java.model.SubTask;
import main.java.model.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class FileBackedTaskManager extends InMemoryTaskManager {

    public static final String TASK_IN_LINE_DELIMITER = ",";
    private static final String fileToSave = "resources/file.csv";

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

    private static List<String> historyFromString(String string) {
        return Arrays.asList(string.split(TASK_IN_LINE_DELIMITER));
    }

    public abstract void saveString(String data);
}
