package service;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private static List<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        int MAX_SIZE = 10;
        if (history.size() > MAX_SIZE) {
            history.remove(0);
        }
        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        List<Task> listHistory = new ArrayList<>();
        for (Task task : history) {
            listHistory.add(task);
        }
        return history;
    }
}
