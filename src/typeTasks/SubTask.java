package typeTasks;

public class SubTask extends Task {
    int epicNumber;

    public int getEpicNumber() {
        return epicNumber;
    }

    public void setEpicNumber(int epicNumber) {
        this.epicNumber = epicNumber;
    }

    public SubTask() {
    }

    public SubTask(String name, String description, int id, String status) {
        super(name, description, id, status);
        this.epicNumber = epicNumber;
    }

    @Override
    public String toString() {
        String result = "name = " + getName() + '\'';
        if (getDescription() != null) {
            result = result + ", descroption= " + getDescription().length() + '\'';
        } else {
            result = result + ", description= null ";
        }
        result = result + ", id=" + getId() + ", " + "epicNumber" + epicNumber
                + ", status= " + getStatus() + '\'';
        return result;
    }
}
