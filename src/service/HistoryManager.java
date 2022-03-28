package service;

import model.Node;
import model.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);
    Node<Task> removeNode(Node node);
    List<Task> getHistory();
}
