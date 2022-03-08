package model;

public class SubTask extends Task {

    private int epicId;

    public int getEpicNumber() {
        return epicId;
    }

    public void setEpicNumber(int epicNumber) {
        this.epicId = epicNumber;
    }

    public SubTask(String name, String description, int id, String status) {
        super(name, description, id, status);
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        String result = "name = " + getName() + '\'';
        if (getDescription() != null) {
            result = result + ", descroption= " + getDescription().length() + '\'';
        } else {
            result = result + ", description= null ";
        }
        result = result + ", id=" + getId() + ", " + "epicId" + epicId
                + ", status= " + getStatus() + '\'';
        return result;
    }
}
