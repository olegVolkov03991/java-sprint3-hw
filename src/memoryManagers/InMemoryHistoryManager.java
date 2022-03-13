package memoryManagers;

import service.HistoryManager;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private static List<Task> history = new ArrayList<Task>();

    @Override
    public void add(Task task) {
        if (history.isEmpty()) {
            history.add(task);
        } else if (history.size() < 10) {
            history.add(task);
        } else {
            history.remove(0);
            history.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> listHistory = new ArrayList<>();
        int count = 1;
        for (Task task : history) {
            listHistory.add(task);
            count++;
        }
        return listHistory;
    }
}
