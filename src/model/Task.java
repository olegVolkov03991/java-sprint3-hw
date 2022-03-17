package model;

import java.util.Objects;

public class Task {

    private int id;
    private String name;
    private String description;
    private Status status;

    public Task(String name, String description, int id, Status status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        String result = "name = " + name + '\'';
        if (description != null) {
            result = result + ", description= " + description.length() + '\'';
        } else {
            result = result + ", descroption= null";
        }
        result = result + ", id= " + id + ", status= " + status + '\'';
        return result;
    }
}
