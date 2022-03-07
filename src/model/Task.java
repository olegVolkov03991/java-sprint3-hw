package model;

import java.util.Objects;

public class Task {
    //idGenerator idGen = new idGenerator(id);

    private String name;
    private String description;
    private int id;
    private String status;

///   public Task() {
//    }

    public Task(String name, String description, int id, String status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public Task() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

