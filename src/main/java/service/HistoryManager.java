package main.java.service;

import main.java.model.Node;
import main.java.model.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);
    Node<Task> removeNode(Node<Task> node);
    List<Task> getHistory();
}
