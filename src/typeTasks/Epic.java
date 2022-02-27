package typeTasks;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> listSubTask = new ArrayList<>();

    public void addSubTask(int subTask) {
        listSubTask.add(subTask);
    }

    public List<Integer> getListSubTask() {
        return listSubTask;
    }

    public void setListSubTask(List<Integer> listSubTask) {
        this.listSubTask = listSubTask;
    }

    public Epic() {
    }

    public Epic(String name, String description, int id) {
    }

    public Epic(String name, String description, int id, String status, ArrayList<Integer> listSubTask) {
        super(name, description, id, status);
        this.listSubTask = listSubTask;
    }

    public Epic(ArrayList<Integer> listSubTask) {
        this.listSubTask = listSubTask;
    }

    @Override
    public String toString() {
        String result = "name= " + getName() + '\'';
        if (getDescription() != null) {
            result = result + ", descripton= " + getDescription().length() + '\'';
        } else {
            result = result + ", description= null";
        }
        result = result + ", id= " + getId() + ", ListSubTaskNumber = " + listSubTask
                + ", status= " + getStatus() + '\'';
        return result;
    }
}

