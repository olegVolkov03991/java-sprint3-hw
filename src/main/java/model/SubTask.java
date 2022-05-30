package main.java.model;

public class SubTask extends Task {

    private Epic epic;

    private int epicId;

    public int getEpicNumber() {
        return epicId;
    }

    public void setEpicNumber(int epicNumber) {
        this.epicId = epicNumber;
    }

    public Epic getEpic(){
        return epic;
    }

    public void setEpic(Epic epic){
        this.epic = epic;
    }

    public SubTask(String name, String description, int id, Status status) {
        super(name, description, id, status);
    }

    @Override
    public String toString() {
        return super.toString() + "," + getEpicNumber();
    }
}
