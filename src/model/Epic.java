package model;

import java.util.List;

public class Epic extends Task {
    private List<SubTask> subTasks;

    public Epic(String name, String description, int id, Status status, List<SubTask> SubTask) {
        super(name, description, id, status);
        this.subTasks = SubTask;
    }

    public List<SubTask> getListSubTask() {
        return subTasks;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
