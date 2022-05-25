package main.menu.service;

import main.menu.model.Node;
import main.menu.model.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);
    Node<Task> removeNode(Node<Task> node);
    List<Task> getHistory();
}
