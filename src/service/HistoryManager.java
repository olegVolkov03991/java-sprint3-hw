package service;

import model.Node;
import model.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);
    void deleteAll();
    Node<Task> removeNode(Node<Task> node);
    List<Task> getHistory();
}
