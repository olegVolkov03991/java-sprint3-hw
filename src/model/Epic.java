package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<SubTask> subTasks = new ArrayList<>();

    public Epic() {

    }

    public void addSubTask(Integer id) {
    }


    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
    }

    public List<SubTask> getListSubTask() {
        return subTasks;
    }

    public Epic(String name, String description, int id, String status, List<SubTask> SubTask) {
        super(name, description, id, status);
        this.subTasks = SubTask;
    }



    @Override
    public String toString() {
        String result = "name= " + getName() + '\'';
        if (getDescription() != null) {
            result = result + ", descripton= " + getDescription().length() + '\'';
        } else {
            result = result + ", description= null";
        }
        result = result + ", id= " + getId() + ", SubTaskNumber = " + subTasks
                + ", status= " + getStatus() + '\'';
        return result;
    }

}

